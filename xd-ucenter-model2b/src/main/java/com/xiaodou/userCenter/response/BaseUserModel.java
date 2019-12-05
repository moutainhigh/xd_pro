package com.xiaodou.userCenter.response;

import com.alibaba.fastjson.JSON;
import com.xiaodou.common.annotation.ShowField;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.userCenter.model.UserModel;

public abstract class BaseUserModel implements UserModelResponse {

  /**
   * 主键id
   */
  @ShowField
  private String id = StringUtils.EMPTY;

  /** userId 用戶ID */
  @ShowField
  private String userId = StringUtils.EMPTY;

  /**
   * 所属模块
   */

  @ShowField
  private String module = StringUtils.EMPTY;


  /**
   * 盐值
   */
  private String salt = StringUtils.EMPTY;

  /**
   * token初始时间
   */
  private String tokenTime = StringUtils.EMPTY;

  /**
   * token值
   */
  private String token = StringUtils.EMPTY;

  /**
   * 创建时间
   */
  private String createTime = StringUtils.EMPTY;

  /**
   * 用过的设备号列表,设备号之间以逗号分隔
   */
  private String usedDeviceId = StringUtils.EMPTY;

  /**
   * 用户年龄
   */

  @ShowField
  private String age = StringUtils.EMPTY;


  /**
   * 用户地址
   */

  @ShowField
  private String address = StringUtils.EMPTY;


  /**
   * 最新device ip
   */
  private String latestDeviceIp = StringUtils.EMPTY;

  /**
   * 用户名(手机号)
   */

  @ShowField
  private String userName = StringUtils.EMPTY;


  /**
   * 昵称
   */

  @ShowField
  private String nickName = StringUtils.EMPTY;


  /**
   * 性别
   */

  @ShowField
  private String gender = StringUtils.EMPTY;

  /**
   * 头像URL地址
   */

  @ShowField
  private String portrait = StringUtils.EMPTY;
  // local/qq/weibo/weixin;所属平台
  private String platform = StringUtils.EMPTY;

  // 第三方唯一标识
  private String uniqueId = StringUtils.EMPTY;

  @ShowField
  private String credit = StringUtils.EMPTY;; // 积分
  // private String coin = StringUtils.EMPTY;; // 金币
  @ShowField
  private String bePraiseNum = StringUtils.EMPTY;;// 被赞数
  @ShowField
  private String majorName = StringUtils.EMPTY;// 专业名称
  @ShowField
  private String userType = StringUtils.EMPTY;// 用户类型 1 普通用户 2 导入用户

  public String getMajorName() {
    return majorName;
  }

  public void setMajorName(String majorName) {
    this.majorName = majorName;
  }

  public String getPlatform() {
    return platform;
  }

  public void setPlatform(String platform) {
    this.platform = platform;
  }

  public String getUniqueId() {
    return uniqueId;
  }

  public void setUniqueId(String uniqueId) {
    this.uniqueId = uniqueId;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public String getTokenTime() {
    return tokenTime;
  }

  public void setTokenTime(String tokenTime) {
    this.tokenTime = tokenTime;
  }

  public String getCreateTime() {
    return createTime;
  }

  public void setCreateTime(String createTime) {
    this.createTime = createTime;
  }

  public String getAge() {
    return age;
  }

  public void setAge(String age) {
    this.age = age;
  }

  public String getGender() {
    return gender;
  }

  public void setGender(String gender) {
    this.gender = gender;
  }

  public String getLatestDeviceIp() {
    return latestDeviceIp;
  }

  public void setLatestDeviceIp(String latestDeviceIp) {
    this.latestDeviceIp = latestDeviceIp;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public String getNickName() {
    return nickName;
  }

  public void setNickName(String nickName) {
    this.nickName = nickName;
  }

  public String getSalt() {
    return salt;
  }

  public void setSalt(String salt) {
    this.salt = salt;
  }

  public String getPortrait() {
    return portrait;
  }

  public void setPortrait(String portrait) {
    this.portrait = portrait;
  }

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }

  public String getUsedDeviceId() {
    return usedDeviceId;
  }

  public void setUsedDeviceId(String usedDeviceId) {
    this.usedDeviceId = usedDeviceId;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public String getModule() {
    return module;
  }

  public void setModule(String module) {
    this.module = module;
  }

  public String getCredit() {
    return credit;
  }

  public void setCredit(String credit) {
    this.credit = credit;
  }

  public String getBePraiseNum() {
    return bePraiseNum;
  }

  public void setBePraiseNum(String bePraiseNum) {
    this.bePraiseNum = bePraiseNum;
  }

  public String getUserType() {
    return userType;
  }

  public void setUserType(String userType) {
    this.userType = userType;
  }

  @Override
  public String toString() {
    return JSON.toJSONString(this);
  }

  @Override
  public UserModelResponse initFromUserModel(UserModel model) {
    if (null != model) {
      if (null != model.getCreateTime()) setCreateTime(model.getCreateTime().toString());
      if (null != model.getId()) {
        setId(model.getId().toString());
        setUserId(model.getId().toString());
      }
      setModule(model.getModule());
      setLatestDeviceIp(model.getLatestDeviceIp());
      setSalt(model.getSalt());
      setToken(model.getToken());
      if (null != model.getTokenTime()) setTokenTime(model.getTokenTime().toString());
      setUsedDeviceId(model.getUsedDeviceId());
      setUserName(model.getUserName());
      setAddress(model.getAddress());
      if (null != model.getAge()) setAge(model.getAge().toString());
      // setGender(Gender.getByCode(model.getGender().toString()).getName());
      setGender(model.getGender().toString());
      setNickName(model.getNickName());
      setPortrait(model.getPortrait());
      setUniqueId(model.getUniqueId());
      setPlatform(model.getPlatform());
      if (null != model.getCredit()) setCredit(model.getCredit().toString());
      // if (null != model.getCoin()) setCoin(model.getCoin().toString());
      if (null != model.getBePraiseNum()) setBePraiseNum(model.getBePraiseNum().toString());
      if (null != model.getUserType())setUserType(Integer.toString(model.getUserType()));// 默认为1（普通用户）
      initModuleInfo(model.getModuleInfo());
      initOfficialInfo(model.getOfficialInfo());
    }
    return this;
  }

  protected abstract void initModuleInfo(String modelInfo);

  public abstract boolean checkInfo();

  /* 获取官方导入用户数据 */
  protected abstract void initOfficialInfo(String officialInfo);

}