package com.xiaodou.ms.model.course;

import lombok.Data;

/**
 * @name @see com.xiaodou.ms.model.course.CourseResourceAudioModel.java
 * @CopyRright (c) 2017 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年8月21日
 * @description 音频模型model
 * @version 1.0
 */
@Data
public class CourseResourceAudioModel {

  // 视频ID
  private Long id;

  // 课程Id
  private Long courseId;

  // 音频所属的大纲章节ID
  private Long chapterId;

  // 音频名称
  private String name;

  // 音频所在的URL地址
  private String url;

  // 时长展示分钟段
  private Integer timeLengthMinute;

  // 时长展示秒数段
  private Integer timeLengthSecond;

  // 音频文件大小M
  private Double size;

  // 音频详细描述
  private String detail;

  // 音频的关键字，方便全局搜索
  private String keyPoint;

  // 章节名称
  private String chapterName;

  private Integer status;

public Long getId() {
	return id;
}

public void setId(Long id) {
	this.id = id;
}

public Long getCourseId() {
	return courseId;
}

public void setCourseId(Long courseId) {
	this.courseId = courseId;
}

public Long getChapterId() {
	return chapterId;
}

public void setChapterId(Long chapterId) {
	this.chapterId = chapterId;
}

public String getName() {
	return name;
}

public void setName(String name) {
	this.name = name;
}

public String getUrl() {
	return url;
}

public void setUrl(String url) {
	this.url = url;
}

public Integer getTimeLengthMinute() {
	return timeLengthMinute;
}

public void setTimeLengthMinute(Integer timeLengthMinute) {
	this.timeLengthMinute = timeLengthMinute;
}

public Integer getTimeLengthSecond() {
	return timeLengthSecond;
}

public void setTimeLengthSecond(Integer timeLengthSecond) {
	this.timeLengthSecond = timeLengthSecond;
}

public Double getSize() {
	return size;
}

public void setSize(Double size) {
	this.size = size;
}

public String getDetail() {
	return detail;
}

public void setDetail(String detail) {
	this.detail = detail;
}

public String getKeyPoint() {
	return keyPoint;
}

public void setKeyPoint(String keyPoint) {
	this.keyPoint = keyPoint;
}

public String getChapterName() {
	return chapterName;
}

public void setChapterName(String chapterName) {
	this.chapterName = chapterName;
}

public Integer getStatus() {
	return status;
}

public void setStatus(Integer status) {
	this.status = status;
}

  
}
