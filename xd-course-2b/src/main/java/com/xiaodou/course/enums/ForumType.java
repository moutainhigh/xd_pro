package com.xiaodou.course.enums;


public enum ForumType {
	COURSEANDSHARE("0", "公开课和知识分享"),OPENCOURSE("1", "公开课"),SHARE("2", "知识分享"),MAJOR("3","特设专业"), CAMPUSVOICE ("4", "校园之声");

  private ForumType(String code, String value) {
    this.code = code;
    this.value = value;
  }

  public String code;
  public String value;

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }
}
