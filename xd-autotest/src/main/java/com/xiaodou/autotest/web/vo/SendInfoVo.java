package com.xiaodou.autotest.web.vo;

import java.util.Date;

import com.xiaodou.summer.validator.annotion.NotEmpty;

/**
 * @name @see com.xiaodou.dashboard.vo.SendInfoVo.java
 * @CopyRright (c) 2015 by XiaoDou NetWork Technology
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2015年11月30日
 * @description 发送信息vo
 * @version 1.0
 */
public class SendInfoVo {

	public SendInfoVo() {
	}

	/** 邮件名称 */
	@NotEmpty
	private String name;
	/** 收件人 */
	@NotEmpty
	private String mail;
	/** 时间 */
	@NotEmpty
	private Date createDate;
	/** 邮件内容 */
	@NotEmpty
	private String mailInfo;
	/** 失败数量 */
	@NotEmpty
	private Integer failCount=0;
	/** 总请求数 */
	@NotEmpty
	private Integer count=0;
	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getMailInfo() {
		return mailInfo;
	}

	public void setMailInfo(String mailInfo) {
		this.mailInfo = mailInfo;
	}

	public Integer getFailCount() {
		return failCount;
	}

	public void setFailCount(Integer failCount) {
		this.failCount = failCount;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

}
