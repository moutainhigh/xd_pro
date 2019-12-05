package com.xiaodou.course.dao.product;

import com.xiaodou.course.dao.BaseProcessDao;
import com.xiaodou.course.model.product.ProductCategoryModel;
import com.xiaodou.summer.dao.pagination.Page;

import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zyp on 15/6/26.
 */
@Repository("productCategoryDao")
public class ProductCategoryDao extends BaseProcessDao<ProductCategoryModel> {

  /**
   * @param inputArgument 查询条件
   * @return Page<Entity>
   * @throws
   * @Title: queryListByPaging
   * @Description: 根据条件 查询 列表 (分页)
   */
  @SuppressWarnings("rawtypes")
  public Page<ProductCategoryModel> cascadeQueryProduct(Map inputArgument, Map output) {
    Map<String, Map> mapParam = new HashMap<String, Map>();
    mapParam.put("input", inputArgument);
    mapParam.put("output", output);
    return this.selectPaginationList(
        this.getEntityClass().getSimpleName() + ".cascadeQueryProduct", mapParam, null);
  }
}
