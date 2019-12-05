package com.xiaodou.oms.statemachine.instance.bus;

/**
 * 汽车票状态机状态定义 (二期支付落地)
 * @author bing.cheng
 *
 */
public enum BusState {
	
	Init(0, "Init", "新单"), 
	HoldSeatSuccess(6, "HoldSeatSuccess", "占座成功"), 	
	Closed(5, "Closed", "取消"), 
	PaySuccess(1, "PaySuccess", "支付成功"),
	PayFailure(-1, "PayFailure", "支付失败"),
	HoldTicketIng(10, "HoldTicketIng", "出票中"),
	HoldTicketSuccess(2, "HoldTicketSuccess", "出票成功");

	private Integer name;
	private String code;
	private String desc;

	private BusState(Integer name, String code, String desc) {
		this.name = name;
		this.code = code;
		this.desc = desc;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Integer getName() {
		return name;
	}

	public void setName(Integer name) {
		this.name = name;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	@Override
	public String toString() {
		return this.getName().toString();
	}
}
