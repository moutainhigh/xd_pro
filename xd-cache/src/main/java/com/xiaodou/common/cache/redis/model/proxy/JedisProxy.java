package com.xiaodou.common.cache.redis.model.proxy;

import java.util.List;
import java.util.Map;
import java.util.Set;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;

import com.xiaodou.common.cache.redis.model.proxy.pipeline.IPipeLineProxy;
import com.xiaodou.common.cache.redis.model.proxy.pipeline.PipeLineProxy;

/**
 * jedis实例代理
 * 
 * @author <a href="mailto:dan.zhao@elong.corp.com">zhaodan</a>
 * @version 1.0
 * @date 2014年6月4日
 */
public class JedisProxy implements IJedisProxy<Jedis> {

  public JedisProxy(Jedis jedis) {
    this(jedis, false);
  }

  public JedisProxy(Jedis jedis, boolean broken) {
    this.jedis = jedis;
    this.broken = broken;
  }

  private Jedis jedis;

  private boolean broken;

  @Override
  public boolean isBroken() {
    return this.broken;
  }

  @Override
  public void setBroken(boolean broken) {
    this.broken = broken;
  }

  @Override
  public Jedis getJedis() {
    return this.jedis;
  }

  @Override
  public void setJedis(Jedis jedis) {
    this.jedis = jedis;
  }

  public boolean exists(String key) {
    return this.jedis.exists(key);
  }

  public Long del(String key) {
    return this.jedis.del(key);
  }

  public void rpush(String key, String aList) {
    this.jedis.rpush(key, aList);
  }

  public void hset(String key, String field, String value) {
    this.jedis.hset(key, field, value);
  }

  public void hincrBy(String key, String incrementField, long incrementValue) {
    this.jedis.hincrBy(key, incrementField, incrementValue);

  }

  public String get(String key) {
    return this.jedis.get(key);
  }

  public List<String> lrange(String key, int i, int j) {
    return this.jedis.lrange(key, i, j);
  }

  public Map<String, String> hgetAll(String key) {
    return this.jedis.hgetAll(key);
  }

  public String hget(String key, String field) {
    return this.jedis.hget(key, field);
  }

  public void set(byte[] bytes, byte[] byteArray) {
    this.jedis.set(bytes, byteArray);
  }

  public void expire(byte[] bytes, int cacheSeconds) {
    this.jedis.expire(bytes, cacheSeconds);
  }

  public byte[] get(byte[] bytes) {
    return this.jedis.get(bytes);
  }

  public void hmset(String key, Map<String, String> map) {
    this.jedis.hmset(key, map);
  }

  public List<String> hmget(String key, String... field) {
    return this.jedis.hmget(key, field);
  }

  @Override
  public String getSet(String key, String value) {
    return this.jedis.getSet(key, value);
  }

  @Override
  public void expire(String key, int cacheSeconds) {
    this.jedis.expire(key, cacheSeconds);
  }

  @Override
  public IPipeLineProxy<Pipeline> pipelined() {
    return new PipeLineProxy(this.jedis.pipelined());
  }

  @Override
  public Long hdel(String key, String... fields) {
    return this.jedis.hdel(key, fields);
  }

  @Override
  public Long zadd(String key, double score, String member) {
    return this.jedis.zadd(key, score, member);
  }

  @Override
  public Long zadd(String key, Map<String, Double> scoreMembers) {
    return this.jedis.zadd(key, scoreMembers);
  }

  @Override
  public Set<String> zrange(String key, long start, long end) {
    return this.jedis.zrange(key, start, end);
  }

  @Override
  public Long zcard(String key) {
    return this.jedis.zcard(key);
  }

  @Override
  public Long zrem(String key, String... member) {
    // TODO Auto-generated method stub
    return this.jedis.zrem(key, member);
  }

  /**
   * +1 (方法说明描述)
   * 
   * @param key
   * @return
   * 
   * @see com.elong.common.cache.redis.model.proxy.IJedisProxy#incr(java.lang.String)
   */
  @Override
  public Long incr(String key) {
    return this.jedis.incr(key);
  }

  @Override
  public boolean setnx(String key) {
    return this.jedis.setnx(key, LOCK) == 1;
  }

  @Override
  public IPipeLineProxy<Pipeline> getPipeLine() {
    return new PipeLineProxy(this.jedis.pipelined());
  }

}
