package com.xiaodou.summer.web;

import com.xiaodou.summer.vo.in.BaseValidatorPojo;
import com.xiaodou.summer.vo.out.ResultInfo;


/**
 * <p>
 * 业务方法拓展点
 * </p>
 * 
 * @author <a href="mailto:dan.zhao@elong.corp.com">zhaodan</a>
 * @version 1.0
 * @date 2014年7月2日
 */
public abstract class ServiceHandler<M extends BaseValidatorPojo> {
  private BaseController controller;

  public void setController(BaseController controller) {
    this.controller = controller;
  }

  public BaseController getController() {
    return controller;
  }

  public ServiceHandler(BaseController controller) {
    this.controller = controller;
  }
  
  public ServiceHandler() {
  }

  /**
   * 调用业务层方法
   * 
   * @param pojo 參數pojo
   * @return resultInfoVo
   * @throws Exception
   */
  public abstract <T extends ResultInfo> T doService(M pojo) throws Exception;
}
