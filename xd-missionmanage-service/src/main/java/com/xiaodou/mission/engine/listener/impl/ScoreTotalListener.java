package com.xiaodou.mission.engine.listener.impl;

import com.xiaodou.mission.constants.MissionConstant;
import com.xiaodou.mission.engine.listener.AbstractEventListener;
import com.xiaodou.mission.engine.model.Context;

/**
 * @name @see com.xiaodou.mission.engine.listener.impl.ScoreTotalListener.java
 * @CopyRright (c) 2017 by Corp.XiaodouTech
 *
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年12月26日
 * @description 总得分监听器
 * @version 1.0
 */
public class ScoreTotalListener extends AbstractEventListener {

  @Override
  public String getCurrentNum(Context context) {
    if (null != context && null != context.getCoreParam()
        && null != context.getCoreParam().getScoreTotal()) {
      return context.getCoreParam().getScoreTotal().toString();
    }
    return MissionConstant.DEFAULT_THROHOLD_UNRECHED;
  }

}