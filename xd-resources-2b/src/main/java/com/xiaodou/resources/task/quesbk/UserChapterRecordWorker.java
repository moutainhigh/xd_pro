package com.xiaodou.resources.task.quesbk;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.google.common.collect.Maps;
import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.jmsg.client.RabbitMQSender;
import com.xiaodou.queue.annotation.HandlerMessage;
import com.xiaodou.queue.callback.IMQCallBacker;
import com.xiaodou.queue.message.DefaultMessage;
import com.xiaodou.queue.worker.AbstractDefaultWorker;
import com.xiaodou.resources.constant.quesbk.QuesBaseConstant;
import com.xiaodou.resources.model.quesbk.UserChapterRecord;
import com.xiaodou.resources.service.quesbk.facade.QuesOperationFacade;
import com.xiaodou.summer.util.SpringWebContextHolder;

@HandlerMessage("UserChapterRecord")
public class UserChapterRecordWorker extends AbstractDefaultWorker {

  /** serialVersionUID */
  private static final long serialVersionUID = 1744525511433456484L;

  @Override
  public void domain(DefaultMessage message, IMQCallBacker<DefaultMessage> callback) {
    UserChapterRecord userExamRecord =
        FastJsonUtil.fromJson(message.getMessageBodyJson(), UserChapterRecord.class);
    QuesOperationFacade quesOperationFacade = SpringWebContextHolder.getBean("quesOperationFacade");
    userExamRecord = queryUserItemRecord(userExamRecord, quesOperationFacade);
    insertOrUpdateItemRecord(userExamRecord, quesOperationFacade);
    // 更新成绩
//    quesOperationFacade.updateUserScore(userExamRecord.getModule(), userExamRecord.getCourseId(),
//        userExamRecord.getUserId());
    RabbitMQSender.getInstance().send(userExamRecord, UUID.randomUUID().toString(),
        "2_refreshChapterRecordStatistics");
  }

  private UserChapterRecord queryUserItemRecord(UserChapterRecord userExamRecord,
      QuesOperationFacade quesOperationFacade) {
    Map<String, Object> cond = Maps.newHashMap();
    cond.put("userId", userExamRecord.getUserId());
    cond.put("courseId", userExamRecord.getCourseId());
    cond.put("chapterId", userExamRecord.getChapterId());
    cond.put("itemId", userExamRecord.getItemId());
    List<UserChapterRecord> recordList = quesOperationFacade.queryUserChapterRecord(cond);
    if (null == recordList || recordList.size() == 0) return userExamRecord;
    UserChapterRecord chapterRecord = recordList.get(0);
    chapterRecord.setScore(userExamRecord.getScore());
    chapterRecord.setStarLevel(userExamRecord.getStarLevel());
    chapterRecord.setModule(userExamRecord.getModule());
    if (null != userExamRecord.getStatus()) chapterRecord.setStatus(userExamRecord.getStatus());
    chapterRecord.setRobotIdList(userExamRecord.getRobotIdList());
    chapterRecord.markModify();
    return chapterRecord;
  }

  private void insertOrUpdateItemRecord(UserChapterRecord userExamRecord,
      QuesOperationFacade quesOperationFacade) {
    if (QuesBaseConstant.QUES_DOMAIN_OPERATION_CREATE == userExamRecord.getOperation()) {
      quesOperationFacade.insertUserChapterRecord(userExamRecord);
    }
    if (QuesBaseConstant.QUES_DOMAIN_OPERATION_MODIFY == userExamRecord.getOperation()) {
      quesOperationFacade.updateUserChapterRecord(userExamRecord);
    }
  }

  @Override
  public void domain(List<DefaultMessage> message, IMQCallBacker<List<DefaultMessage>> callback) {}

  @Override
  public void onException(Throwable t, List<DefaultMessage> message,
      IMQCallBacker<List<DefaultMessage>> callback) {
    LoggerUtil.error("插入/更新闯关状态异常.", t);
  }

  @Override
  public void onException(Throwable t, DefaultMessage message,
      IMQCallBacker<DefaultMessage> callback) {
    LoggerUtil.error("插入/更新闯关状态异常.", t);
  }

}
