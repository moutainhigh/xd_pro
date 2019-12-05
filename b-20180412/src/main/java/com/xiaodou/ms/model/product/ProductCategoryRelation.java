package com.xiaodou.ms.model.product;

import java.sql.Timestamp;

import com.xiaodou.autobuild.annotations.Column;
import com.xiaodou.autobuild.tool.MybatisXmlTool;
import com.xiaodou.ms.constants.XdmsConstant;

/**
 * @name @see com.xiaodou.ms.model.product.ProductCategoryRelation.java
 * @CopyRright (c) 2016 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2016年5月20日
 * @description 产品分类与产品关系模型
 * @version 1.0
 */
public class ProductCategoryRelation {

  /** id 主键ID */
  @Column(isMajor = true)
  private Long id;
  /** productCategoryId 产品分类ID */
  private Long productCategoryId;
  /** productId 产品ID */
  private Long productId;
  /** relationState 关系状态 */
  private Integer relationState = XdmsConstant.PRODUCT_RELATION_VALID;
  /** createTime 创建时间 */
  @Column(betweenScope = true, sortBy = true)
  private Timestamp createTime = new Timestamp(System.currentTimeMillis());

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getProductCategoryId() {
    return productCategoryId;
  }

  public void setProductCategoryId(Long productCategoryId) {
    this.productCategoryId = productCategoryId;
  }

  public Long getProductId() {
    return productId;
  }

  public void setProductId(Long productId) {
    this.productId = productId;
  }

  public Integer getRelationState() {
    return relationState;
  }

  public void setRelationState(Integer relationState) {
    this.relationState = relationState;
  }

  public Timestamp getCreateTime() {
    return createTime;
  }

  public void setCreateTime(Timestamp createTime) {
    this.createTime = createTime;
  }

}
