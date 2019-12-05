package com.xiaodou.mooccrawler.task;

import java.util.List;
import java.util.UUID;

import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.mooccrawler.dao.TaskModelDao;
import com.xiaodou.mooccrawler.domain.TaskModel;
import com.xiaodou.mooccrawler.holder.TaskHolder.Task;
import com.xiaodou.queue.annotation.HandlerMessage;
import com.xiaodou.queue.callback.IMQCallBacker;
import com.xiaodou.queue.message.DefaultMessage;
import com.xiaodou.queue.worker.AbstractDefaultWorker;
import com.xiaodou.summer.util.SpringWebContextHolder;

@HandlerMessage("AddTask")
public class AddTaskWorker extends AbstractDefaultWorker {

  /** serialVersionUID */
  private static final long serialVersionUID = -4668352060797615970L;

  @Override
  public void domain(DefaultMessage message, IMQCallBacker<DefaultMessage> callback)
      throws Exception {
    Task task = FastJsonUtil.fromJson(message.getMessageBodyJson(), Task.class);
    if (null == task || null == task.getCourseId())
      throw new IllegalArgumentException("task 参数异常");
    TaskModel taskModel = new TaskModel();
    taskModel.setCourseId(task.getCourseId());
    taskModel.setBaseUrl(task.getBaseUrl());
    taskModel.setTid(task.getTid());
    taskModel.setId(UUID.randomUUID().toString());
    TaskModelDao taskModelDao = SpringWebContextHolder.getBean("taskModelDao");
    if (null != taskModelDao) {
      taskModelDao.addEntity(taskModel);
    }
  }

  @Override
  public void domain(List<DefaultMessage> message, IMQCallBacker<List<DefaultMessage>> callback)
      throws Exception {
    // TODO Auto-generated method stub

  }

  @Override
  public void onException(Throwable t, List<DefaultMessage> message,
      IMQCallBacker<List<DefaultMessage>> callback) {
    LoggerUtil.error("处理Task失败", t);
  }

  @Override
  public void onException(Throwable t, DefaultMessage message,
      IMQCallBacker<DefaultMessage> callback) {

  }

}
