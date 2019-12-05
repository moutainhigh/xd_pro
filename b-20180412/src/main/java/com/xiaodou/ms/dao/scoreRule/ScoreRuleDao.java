package com.xiaodou.ms.dao.scoreRule;


import java.util.Map;

import org.springframework.stereotype.Repository;

import com.xiaodou.ms.dao.BaseProcessDao;
import com.xiaodou.ms.model.score.ScoreRuleModel;
import com.xiaodou.summer.dao.pagination.Page;
import com.xiaodou.summer.dao.param.IQueryParam;
import com.xiaodou.summer.dao.param.QueryParam;
import com.xiaodou.summer.dao.param.QueryEnums.Sort;

/**
 * @name ScoreRuleDao 
 * CopyRright (c) 2018 by <a href=mailto:zhuweijin@corp.51xiaodou.com>zhuweijin</a>
 *
 * @author <a href=mailto:zhuweijin@corp.51xiaodou.com>zhuweijin</a>
 * @date 2018年4月16日
 * @description 计分规则的dao层
 * @version 1.0
 */
@Repository("scoreRuleDao")
public class ScoreRuleDao extends BaseProcessDao<ScoreRuleModel> {
  
  
  /**
   * @param inputArgument 查询条件
   * @param outputField 需要返回的字段
   * @param page 分页数据
   * @return Page<Entity>
   * @throws
   * @Title: queryListByPaging
   * @Description: 根据条件 查询 列表 (分页)
   */
  public Page<ScoreRuleModel> queryListWithSort(Map inputArgument, Map outputField, Page page) {
    IQueryParam param = new QueryParam();
    param.addInputs(inputArgument);
    param.addOutputs(outputField);
    param.addSort("listOrder", Sort.DESC);
    param.addSort("updateTime", Sort.DESC); 
    param.addSort("createTime", Sort.DESC);
    return super.findEntityListByCond(param, page);
  }
  
}