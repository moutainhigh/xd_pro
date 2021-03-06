package com.xiaodou.resources.request.forum;

import com.xiaodou.resources.request.BaseRequest;
import com.xiaodou.summer.validator.annotion.NotEmpty;

/**
 * @name DeleteColumnistRequest 
 * CopyRright (c) 2016 by zhaodan
 *
 * @author zhaodan
 * @date 2016年8月31日
 * @description 删除专栏请求
 * @version 1.0
 */
public class DeleteColumnistRequest extends BaseRequest {

  /** columnistId 专栏ID */
  @NotEmpty
  private String columnistId;

  public String getColumnistId() {
    return columnistId;
  }

  public void setColumnistId(String columnistId) {
    this.columnistId = columnistId;
  }

}
