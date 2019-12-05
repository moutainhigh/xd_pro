package com.xiaodou.mission.engine.event;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.xiaodou.summer.validator.annotion.AddValidField;
import com.xiaodou.summer.validator.annotion.NotEmpty;
import com.xiaodou.summer.validator.enums.AnnotationType;

/**
 * @name @see com.xiaodou.mission.engine.event.EliminateWrongQuesEvent.java
 * @CopyRright (c) 2016 by XiaoDou NetWork Technology
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2016年2月17日
 * @description 消灭错题事件
 * @version 1.0
 */
@Data
@EqualsAndHashCode(callSuper=true)
@AddValidField(annotiation = AnnotationType.NotEmpty, field = {"courseId", "majorId"})
public class EliminateWrongQuesEvent extends BaseEvent {
  protected EliminateWrongQuesEvent() {}

  /** count 消灭错题数量 */
  @NotEmpty
  private Integer count;

}
