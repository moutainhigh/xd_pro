package com.xiaodou.ms.dao.sms;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.xiaodou.ms.dao.SmsBaseDao;
import com.xiaodou.ms.model.sms.SmsTemplateTypeModel;
import com.xiaodou.summer.dao.pagination.Page;

/**
 * Created by zyp on 15/6/26.
 */
@Repository("smsTemplateTypeDao")
public class SmsTemplateTypeDao extends SmsBaseDao<SmsTemplateTypeModel> {
	
	/**
	 * @param inputArgument
	 *            查询条件
	 * @return Page<Entity>
	 * @throws
	 * @Title: queryListByPaging
	 * @Description: 根据条件 查询 列表 (分页)
	 */
	public Page<SmsTemplateTypeModel> cascadeQueryProduct(Map inputArgument,
			Map output) {
		Map<String, Map> mapParam = new HashMap<String, Map>();
		mapParam.put("input", inputArgument);
		mapParam.put("output", output);
		return this.selectPaginationList(this.getEntityClass().getSimpleName()
				+ ".cascadeQueryProduct", mapParam, null);
	}
}
