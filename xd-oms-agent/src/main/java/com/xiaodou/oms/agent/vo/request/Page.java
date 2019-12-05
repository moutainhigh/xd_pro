package com.xiaodou.oms.agent.vo.request;

/**
 * <p>分页查询-分页信息类</p>
 *
 * @author <a href="mailto:dan.zhao@elong.corp.com">zhaodan</a>
 * @version 1.0
 * @date 2014年7月1日
 */
public class Page {
  
  /**
   * 单页记录数
   */
  private Integer pageSize;
  
  /**
   * 页码
   */
  private Integer pageNo;

  public Integer getPageSize() {
    return pageSize;
  }

  public void setPageSize(Integer pageSize) {
    this.pageSize = pageSize;
  }

  public Integer getPageNo() {
    return pageNo;
  }

  public void setPageNo(Integer pageNo) {
    this.pageNo = pageNo;
  }

}
