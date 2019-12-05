package com.xiaodou.jmsg.server.service.facade;

import java.util.List;
import java.util.Map;

import com.xiaodou.jmsg.server.model.MessageBody;
import com.xiaodou.jmsg.server.model.MessageLog;
import com.xiaodou.jmsg.server.model.MessageQueueSetting;
import com.xiaodou.jmsg.server.model.Relation;
import com.xiaodou.jmsg.server.model.ServerQueueSetting;

public interface JmsgServerServiceFacade {
	List<ServerQueueSetting> getServerQueueSettingListByCond(Map<String, Object> inputArgument);
   ServerQueueSetting getServerQueueSettingListById(String id);
	void addServerQueueSetting(ServerQueueSetting s);
	void editServerQueueSetting(ServerQueueSetting s);
	void delServerQueueSetting(ServerQueueSetting s);
	
   List<MessageQueueSetting> getQueueListByCond(Map<String, Object> inputArgument);
   MessageQueueSetting getQueueListById(String id);
	void addQueue(MessageQueueSetting s);
	void editQueue(MessageQueueSetting s);
	void delQueue(MessageQueueSetting s);
	


   List<MessageBody> getMessageBodyListByCond(Map<String, Object> inputArgument);
   MessageBody getMessageBodyById(String id);
   MessageBody addMessageBody(MessageBody messageBody);
	
   List<MessageLog> getMessageLogListByCond(Map<String, Object> inputArgument,
    	      Map<String, Object> outputField);
   MessageLog getMessageLogById(String id);
   boolean updateMessageBody(Map<String, Object> input,MessageBody messageBody);
   MessageLog addMessageLog(MessageLog messageLog);
   
   List<Relation> getRelationByCond(Map<String, Object> inputArgument);
}
