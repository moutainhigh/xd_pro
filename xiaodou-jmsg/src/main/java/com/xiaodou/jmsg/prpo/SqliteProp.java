package com.xiaodou.jmsg.prpo;

import com.xiaodou.common.util.FileUtil;

public class SqliteProp {

	/**
	 * 配置文件
	 */
	private static FileUtil errFile = FileUtil
			.getInstance("/conf/custom/env/jmsg_client_sqlite_jdbc.properties");

	/**
	 * @return 获取配置文件信息
	 */
	private static FileUtil getProperty() {
		if (errFile == null)
			synchronized (SqliteProp.class) {
				if (errFile == null)
					errFile = FileUtil
							.getInstance("/conf/custom/env/jmsg_client_sqlite_jdbc.properties");
			}
		return errFile;
	}

	/**
	 * 获取参数值
	 * 
	 * @param code
	 *            参数key
	 * @return 参数值
	 */
	public static String getParams(String code) {
		return getProperty().getProperties(code);
	}

}
