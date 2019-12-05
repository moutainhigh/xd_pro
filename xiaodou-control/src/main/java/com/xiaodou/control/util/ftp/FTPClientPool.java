package com.xiaodou.control.util.ftp;

import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.pool.ObjectPool;
import org.apache.commons.pool.PoolableObjectFactory;

/**
 * 实现了一个FTPClient连接池
 * 
 * @author heaven
 */
public class FTPClientPool implements ObjectPool<FTPClient> {
	private static final int DEFAULT_POOL_SIZE = 1;
	private final BlockingQueue<FTPClient> pool;
	private final FTPClientFactory factory;

	/**
	 * 初始化连接池，需要注入一个工厂来提供FTPClient实例
	 * 
	 * @param factory
	 * @throws Exception
	 */
	public FTPClientPool(FTPClientFactory factory) throws Exception {
		this(DEFAULT_POOL_SIZE, factory);
	}

	/**
	 * 
	 * @param poolSize
	 * @param factory
	 * @throws Exception
	 */
	public FTPClientPool(int poolSize, FTPClientFactory factory)
			throws Exception {
		this.factory = factory;
		pool = new ArrayBlockingQueue<FTPClient>(poolSize * 2);
		initPool(poolSize);
	}

	/**
	 * 初始化连接池，需要注入一个工厂来提供FTPClient实例
	 * 
	 * @param maxPoolSize
	 * @throws Exception
	 */
	private void initPool(int maxPoolSize) throws Exception {
		for (int i = 0; i < maxPoolSize; i++) {
			// 往池中添加对象
			addObject();
		}

	}

	public FTPClient borrowObject() throws Exception, NoSuchElementException,
			IllegalStateException {
		FTPClient client = pool.take();
		if (client == null) {
			client = factory.makeObject();
			addObject();
		} else if (!factory.validateObject(client)) {// 验证不通过
			// 使对象在池中失效
			invalidateObject(client);
			// 制造并添加新对象到池中
			client = factory.makeObject();
			addObject();
		}
		return client;

	}

	public void returnObject(FTPClient client) throws Exception {
		if ((client != null) && !pool.offer(client, 1, TimeUnit.SECONDS)) {
			try {
				factory.destroyObject(client);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void invalidateObject(FTPClient client) throws Exception {
		// 移除无效的客户端
		pool.remove(client);
	}

	public void addObject() throws Exception, IllegalStateException,
			UnsupportedOperationException {
		// 插入对象到队列
		pool.offer(factory.makeObject(), 1, TimeUnit.SECONDS);
	}

	public int getNumIdle() throws UnsupportedOperationException {
		return 0;
	}

	public int getNumActive() throws UnsupportedOperationException {
		return 0;
	}

	public void clear() throws Exception, UnsupportedOperationException {

	}

	public void close() throws Exception {
		while (pool.iterator().hasNext()) {
			FTPClient client = pool.take();
			factory.destroyObject(client);
		}
	}

	public void setFactory(PoolableObjectFactory<FTPClient> factory)
			throws IllegalStateException, UnsupportedOperationException {

	}
}