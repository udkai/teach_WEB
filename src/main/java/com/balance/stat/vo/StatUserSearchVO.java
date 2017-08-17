package com.balance.stat.vo;

/**
 * 查询条件
 * @author 孔垂云
 * @date 2017年4月20日
 */
public class StatUserSearchVO {

	private String certificate_code;// 军官证号
	private String name;// 姓名
	private Integer class_id; //班级id
	private Integer course_id;  //课程id

	public Integer getCourse_id() {
		return course_id;
	}

	public void setCourse_id(Integer course_id) {
		this.course_id = course_id;
	}

	public Integer getClass_id() {
		return class_id;
	}

	public void setClass_id(Integer class_id) {
		this.class_id = class_id;
	}

	public String getName_param() {
		return "%" + name + "%";
	}

	public String getCertificate_code() {
		return certificate_code;
	}

	public void setCertificate_code(String certificate_code) {
		this.certificate_code = certificate_code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
