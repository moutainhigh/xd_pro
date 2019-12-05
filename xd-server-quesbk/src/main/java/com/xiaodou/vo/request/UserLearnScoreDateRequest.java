package com.xiaodou.vo.request;

import com.xiaodou.summer.validator.annotion.NotEmpty;

public class UserLearnScoreDateRequest extends QuesBasePojo {

  @NotEmpty
  private String courseId;
  /* page 页数 */
  @NotEmpty
  private String page;
  @NotEmpty
  private String firstLoginTime;

  public String getCourseId() {
    return courseId;
  }

  public void setCourseId(String courseId) {
    this.courseId = courseId;
  }

  public String getPage() {
    return page;
  }

  public void setPage(String page) {
    this.page = page;
  }

  public String getFirstLoginTime() {
    return firstLoginTime;
  }

  public void setFirstLoginTime(String firstLoginTime) {
    this.firstLoginTime = firstLoginTime;
  }

}