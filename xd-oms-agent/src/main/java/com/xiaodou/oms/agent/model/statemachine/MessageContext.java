package com.xiaodou.oms.agent.model.statemachine;

import java.util.Map;

/**
 * Created by zyp on 14-7-3.
 */
public class MessageContext {

    /**核心参数*/
    private StateMachineFireCoreParams coreParams;

    /**其他参数*/
    private Map<String,Object> otherParams;

    public StateMachineFireCoreParams getCoreParams() {
        return coreParams;
    }

    public void setCoreParams(StateMachineFireCoreParams coreParams) {
        this.coreParams = coreParams;
    }

    public Map<String, Object> getOtherParams() {
        return otherParams;
    }

    public void setOtherParams(Map<String, Object> otherParams) {
        this.otherParams = otherParams;
    }
}
