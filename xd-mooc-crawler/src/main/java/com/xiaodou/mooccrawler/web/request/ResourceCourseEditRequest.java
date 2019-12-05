package com.xiaodou.mooccrawler.web.request;

import com.xiaodou.summer.validator.annotion.NotEmpty;
import com.xiaodou.summer.vo.in.BaseRequest;

import java.sql.Timestamp;

/**
 * Created by zyp on 15/4/19.
 */
public class ResourceCourseEditRequest extends BaseRequest {

  // 课程ID
  @NotEmpty
  private Integer id;

  // 课程对应的分类ID
  @NotEmpty
  private Integer categoryId;

  // 课程名称
  @NotEmpty
  private String name;

  // 课程详情
  private String detail;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Integer getCategoryId() {
    return categoryId;
  }

  public void setCategoryId(Integer categoryId) {
    this.categoryId = categoryId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDetail() {
    return detail;
  }

  public void setDetail(String detail) {
    this.detail = detail;
  }
}
