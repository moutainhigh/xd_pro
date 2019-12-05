package com.xiaodou.util;

import com.xiaodou.common.util.FileUtil;


public class ConfigProp {

  /**
   * 配置文件
   */
  private static FileUtil errFile = FileUtil.getInstance("/conf/custom/env/config.properties");

  /**
   * @return 获取配置文件信息
   */
  private static FileUtil getProperty() {
    if (errFile == null)
      synchronized (ConfigProp.class) {
        if (errFile == null)
          errFile = FileUtil.getInstance("/conf/custom/env/config.properties");
      }
    return errFile;
  }

  /**
   * 获取参数值
   * 
   * @param code 参数key
   * @return 参数值
   */
  public static String getParams(String code) {
    return getProperty().getProperties(code);
  }

  /**
   * 获取Int型配置项
   * 
   * @param code
   * @return
   */
  public static int getInt(String code) {
    return getProperty().getPropertiesInt(code);
  }

}
