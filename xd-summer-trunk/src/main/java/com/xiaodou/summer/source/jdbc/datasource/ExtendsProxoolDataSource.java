package com.xiaodou.summer.source.jdbc.datasource;

import org.logicalcobwebs.proxool.ProxoolDataSource;

/**
 * 
 * 
 * @Title:ExtendsProxoolDataSource.java
 * 
 * @Description:数据源扩展
 * 
 * @author zhaoyang
 * @date Jan 17, 2014 10:51:52 AM
 * @version V1.0
 */
public class ExtendsProxoolDataSource extends ProxoolDataSource {

  /**
   * @param houseKeepingSleepTime the houseKeepingSleepTime to set
   */
  public void setHouseKeepingSleepTime(long houseKeepingSleepTime) {

    Long tp = new Long(houseKeepingSleepTime);
    super.setHouseKeepingSleepTime(tp.intValue());
  }

  /**
   * @param maximumConnectionLifetime the maximumConnectionLifetime to set
   */
  public void setMaximumConnectionLifetime(long maximumConnectionLifetime) {

    Long tp = new Long(maximumConnectionLifetime);
    super.setHouseKeepingSleepTime(tp.intValue());
  }

  /**
   * @param overloadWithoutRefusalLifetime the overloadWithoutRefusalLifetime to set
   */
  public void setOverloadWithoutRefusalLifetime(long overloadWithoutRefusalLifetime) {

    Long tp = new Long(overloadWithoutRefusalLifetime);
    super.setHouseKeepingSleepTime(tp.intValue());
  }
}
