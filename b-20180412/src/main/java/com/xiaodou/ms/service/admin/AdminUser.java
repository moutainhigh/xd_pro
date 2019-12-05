package com.xiaodou.ms.service.admin;

import java.sql.Timestamp;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import com.xiaodou.ms.model.admin.Admin;

/**
 * Created by zyp on 14-9-3.
 */
public class AdminUser extends User {

  private String salt;

  private String realName;

  private String email;

  private String telphone;

  private Integer userId;

  private String lastLoginIp;

  private Timestamp lastLoginTime;

  private String merchant;

  public String getMerchant() {
    return merchant;
  }

  public void setMerchant(String merchant) {
    this.merchant = merchant;
  }

  public String getRealName() {
    return realName;
  }

  public void setRealName(String realName) {
    this.realName = realName;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getTelphone() {
    return telphone;
  }

  public void setTelphone(String telphone) {
    this.telphone = telphone;
  }

  public Integer getUserId() {
    return userId;
  }

  public void setUserId(Integer userId) {
    this.userId = userId;
  }

  public String getLastLoginIp() {
    return lastLoginIp;
  }

  public void setLastLoginIp(String lastLoginIp) {
    this.lastLoginIp = lastLoginIp;
  }

  public Timestamp getLastLoginTime() {
    return lastLoginTime;
  }

  public void setLastLoginTime(Timestamp lastLoginTime) {
    this.lastLoginTime = lastLoginTime;
  }

  public String getSalt() {
    return salt;
  }

  public void setSalt(String salt) {
    this.salt = salt;
  }

  public AdminUser(Admin admin, Collection<? extends GrantedAuthority> authorities) {
    super(admin.getUserName(), admin.getPassword(), authorities);
    this.salt = admin.getSalt();
    this.realName = admin.getRealName();
    this.telphone = admin.getTelphone();
    this.userId = admin.getId();
    this.email = admin.getEmail();
    this.lastLoginIp = admin.getLastLoginIp();
    this.lastLoginTime = admin.getLastLoginTime();
    this.merchant = admin.getMerchant();
  }

  public AdminUser(Admin admin, boolean enabled, boolean accountNonExpired,
      boolean credentialsNonExpired, boolean accountNonLocked,
      Collection<? extends GrantedAuthority> authorities) {
    super(admin.getUserName(), admin.getPassword(), enabled, accountNonExpired,
        credentialsNonExpired, accountNonLocked, authorities);
    this.salt = admin.getSalt();
    this.realName = admin.getRealName();
    this.telphone = admin.getTelphone();
    this.userId = admin.getId();
    this.email = admin.getEmail();
    this.lastLoginIp = admin.getLastLoginIp();
    this.lastLoginTime = admin.getLastLoginTime();
    this.merchant = admin.getMerchant();
  }
}
