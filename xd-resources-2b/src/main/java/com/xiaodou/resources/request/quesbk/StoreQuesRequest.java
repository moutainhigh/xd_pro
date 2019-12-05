package com.xiaodou.resources.request.quesbk;

import com.xiaodou.resources.request.BaseRequest;
import com.xiaodou.summer.validator.annotion.LegalValue.LegalValueList;
import com.xiaodou.summer.validator.annotion.NotEmpty;

public class StoreQuesRequest extends BaseRequest {
  /**
   * 课程ID
   */
  @NotEmpty
  private String courseId;

  /**
   * 问题ID
   * 
   */
  @NotEmpty
  private String quesId;
  /**
   * 收藏类型 1 好题(默认)    2 我不会的    3 需要记忆  4取消收藏
   */
  @NotEmpty
  @LegalValueList({"1", "2", "3", "4"})
  private String type;

  public String getQuesId() {
    return quesId;
  }

  public void setQuesId(String quesId) {
    this.quesId = quesId;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getCourseId() {
    return courseId;
  }

  public void setCourseId(String courseId) {
    this.courseId = courseId;
  }

}
