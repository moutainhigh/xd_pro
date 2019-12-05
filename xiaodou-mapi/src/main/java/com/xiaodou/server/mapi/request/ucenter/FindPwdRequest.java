package com.xiaodou.server.mapi.request.ucenter;

import com.xiaodou.server.mapi.request.MapiBaseRequest;
import com.xiaodou.summer.validator.annotion.NotEmpty;

public class FindPwdRequest extends MapiBaseRequest {

  @NotEmpty
  private String phoneNum;

  @NotEmpty
  private String checkCode;

  @NotEmpty
  private String pwd;

  public String getPhoneNum() {
    return phoneNum;
  }

  public void setPhoneNum(String phoneNum) {
    this.phoneNum = phoneNum;
  }

  public String getCheckCode() {
    return checkCode;
  }

  public void setCheckCode(String checkCode) {
    this.checkCode = checkCode;
  }

  public String getPwd() {
    return pwd;
  }

  public void setPwd(String pwd) {
    this.pwd = pwd;
  }

}
