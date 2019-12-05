package com.xiaodou.st.dataclean.task.mission.sessionpercent;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.xiaodou.common.util.DateUtil;
import com.xiaodou.common.util.RandomUtil;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.queue.annotation.HandlerMessage;
import com.xiaodou.queue.callback.IMQCallBacker;
import com.xiaodou.queue.enums.MessageType;
import com.xiaodou.queue.message.DefaultMessage;
import com.xiaodou.queue.worker.AbstractDefaultWorker;
import com.xiaodou.st.dataclean.enums.RoleTypeEnum;
import com.xiaodou.st.dataclean.model.domain.dashboard.sessionpercent.CategoryUnitSessionPercentModel;
import com.xiaodou.st.dataclean.model.domain.raw.RawDataFinishMissionModel;
import com.xiaodou.st.dataclean.model.domain.raw.RawDataProductModel;
import com.xiaodou.st.dataclean.service.facade.DashBoardServiceFacade;
import com.xiaodou.st.dataclean.util.LockFactory;
import com.xiaodou.st.dataclean.util.RealSqlSession;
import com.xiaodou.summer.util.SpringWebContextHolder;

@HandlerMessage(value = "FinishMissionAfter", type = MessageType.Multiple)
public class CategoryUnitSessionPercent extends AbstractDefaultWorker {
  /**
   * serialVersionUID
   */
  private static final long serialVersionUID = -4334009237028174065L;

  /**
   * 试点单位-专业维度下的平均任务完成度
   */
  @Override
  public void domain(DefaultMessage message, IMQCallBacker<DefaultMessage> callback) {
    try {
      LockFactory.getCategoryUnitSessionLock();
      DashBoardServiceFacade dashBoardServiceFacade =
          SpringWebContextHolder.getBean("dashBoardServiceFacade");
      if (StringUtils.isBlank(message.getMessageBodyJson())) return;
      RawDataFinishMissionModel rawDataFinishMissionModel =
          FastJsonUtil.fromJson(message.getMessageBodyJson(), RawDataFinishMissionModel.class);
      RealSqlSession.setRealSqlSession(rawDataFinishMissionModel.getModuleId());
      // 获取产品时间间隔
      RawDataProductModel product =
          dashBoardServiceFacade.getRawDataProductById(Long.parseLong(rawDataFinishMissionModel
              .getProductId()));
      String begin = DateUtil.SDF_YMD.format(product.getBeginApplyTime());
      String end = DateUtil.SDF_YMD.format((new Date()));
      int day =
          (int) ((new Date().getTime() - product.getBeginApplyTime().getTime()) / (24 * 60 * 60 * 1000));
      if (day == 0) {
        return;
      }
      // 根据条件查询任务完成度
      Map<String, Object> input4 = new HashMap<>();
      input4.put("dateTimeUpper", begin);
      input4.put("dateTimeLower", end);
      input4.put("catId", rawDataFinishMissionModel.getProductCategoryId());
      input4.put("roleType", RoleTypeEnum.RoleTypeEnum_Pilot_Unit.getCode());
      input4.put("unitId", rawDataFinishMissionModel.getPilotUnitId());
      Double missionAllPercent = dashBoardServiceFacade.getAvgSessionLearnPercentModelByCond(input4);
      CategoryUnitSessionPercentModel s = new CategoryUnitSessionPercentModel();
//      double missionPercent = missionAllPercent / day;
      s.setMissionPercent(missionAllPercent);
      // 查询记录是否存在
      Map<String, Object> input2 = new HashMap<>();
      input2.put("pilotUnitId", rawDataFinishMissionModel.getPilotUnitId());
      input2.put("catId", rawDataFinishMissionModel.getProductCategoryId());
      List<CategoryUnitSessionPercentModel> dList =
          dashBoardServiceFacade.getCategoryUnitSessionPercentModelByCond(input2);
      if (dList != null && dList.size() > 0) {
        Map<String, Object> cond = new HashMap<>();
        cond.put("pilotUnitId", rawDataFinishMissionModel.getPilotUnitId());
        cond.put("catId", rawDataFinishMissionModel.getProductCategoryId());
        dashBoardServiceFacade.editCategoryUnitSessionPercentModel(cond, s);
      } else {
        s.setPilotUnitId(rawDataFinishMissionModel.getPilotUnitId());
        s.setCreateTime(new Timestamp(System.currentTimeMillis()));
        s.setCatId(Integer.parseInt(rawDataFinishMissionModel.getProductCategoryId()));

        s.setUnitId(rawDataFinishMissionModel.getTaughtUnitId());
        s.setId(Integer.valueOf(RandomUtil.randomNumber(9)));
        s.setRoleType(RoleTypeEnum.RoleTypeEnum_Taught_Unit.getCode());
        dashBoardServiceFacade.addCategoryUnitSessionPercentModel(s);

        s.setRoleType(RoleTypeEnum.RoleTypeEnum_Chief_Unit.getCode());
        s.setUnitId(rawDataFinishMissionModel.getChiefUnitId());
        s.setId(Integer.valueOf(RandomUtil.randomNumber(9)));
        dashBoardServiceFacade.addCategoryUnitSessionPercentModel(s);

        s.setRoleType(RoleTypeEnum.RoleTypeEnum_Pilot_Unit.getCode());
        s.setUnitId(rawDataFinishMissionModel.getPilotUnitId());
        s.setId(Integer.valueOf(RandomUtil.randomNumber(9)));
        dashBoardServiceFacade.addCategoryUnitSessionPercentModel(s);
      }
    } finally {
      LockFactory.returnCategoryUnitSessionLock();
    }
  }

  @Override
  public void domain(List<DefaultMessage> message, IMQCallBacker<List<DefaultMessage>> callback) {}

  @Override
  public void onException(Throwable t, List<DefaultMessage> message,
      IMQCallBacker<List<DefaultMessage>> callback) {}

  @Override
  public void onException(Throwable t, DefaultMessage message,
      IMQCallBacker<DefaultMessage> callback) {
    LoggerUtil.error("记录试点单位专业维度下的平均任务完成度失败", t);
  }
}