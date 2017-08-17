package com.balance.base.model;

import java.util.Date;

/**
 * Created by dsy on 2017/4/19.
 * 学员档案表  model
 */
public class BaseUserInfo {
	private int id;// 主键 自增
	private String certificate_code; // 军官证号
	private String name; // 姓名
	private String department_level;// 部别
	private String duty_name;// 职务名称
	private String telephone;// 手机
	private String native_place;// 籍贯
	private String birth_date;// 出生日期
	private String enlisted_date;// 入伍时间
	private String political_date;// 党团时间
	private String literacy;// 文化程度
	private String dutylevel;// 职务等级
	private String technologylevel;// 技术等级
	private String technologylevel_date;// 现职级时间
	private String ranks; // 军衔
	private String ranks_date; // 衔级时间
	private String note;// 备注
	private String cover_url; // 保存路径ss
	private String resume; // 简介
	private String create_person;
	private Date create_at;
	private Integer class_id;// 班级
	private String class_name;// 班级

	private Integer is_audit; //是否已评分 1是  0否
	private Integer status; //是否已答卷 10未答卷  20已答卷 30已交卷

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public String getDepartment_level() {
		return department_level;
	}

	public void setDepartment_level(String department_level) {
		this.department_level = department_level;
	}

	public String getDuty_name() {
		return duty_name;
	}

	public void setDuty_name(String duty_name) {
		this.duty_name = duty_name;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getNative_place() {
		return native_place;
	}

	public void setNative_place(String native_place) {
		this.native_place = native_place;
	}

	public String getBirth_date() {
		return birth_date;
	}

	public void setBirth_date(String birth_date) {
		this.birth_date = birth_date;
	}

	public String getEnlisted_date() {
		return enlisted_date;
	}

	public void setEnlisted_date(String enlisted_date) {
		this.enlisted_date = enlisted_date;
	}

	public String getPolitical_date() {
		return political_date;
	}

	public void setPolitical_date(String political_date) {
		this.political_date = political_date;
	}

	public String getLiteracy() {
		return literacy;
	}

	public void setLiteracy(String literacy) {
		this.literacy = literacy;
	}

	public String getDutylevel() {
		return dutylevel;
	}

	public void setDutylevel(String dutylevel) {
		this.dutylevel = dutylevel;
	}

	public String getTechnologylevel() {
		return technologylevel;
	}

	public void setTechnologylevel(String technologylevel) {
		this.technologylevel = technologylevel;
	}

	public String getTechnologylevel_date() {
		return technologylevel_date;
	}

	public void setTechnologylevel_date(String technologylevel_date) {
		this.technologylevel_date = technologylevel_date;
	}

	public String getRanks() {
		return ranks;
	}

	public void setRanks(String ranks) {
		this.ranks = ranks;
	}

	public String getRanks_date() {
		return ranks_date;
	}

	public void setRanks_date(String ranks_date) {
		this.ranks_date = ranks_date;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getCover_url() {
		return cover_url;
	}

	public void setCover_url(String cover_url) {
		this.cover_url = cover_url;
	}

	public String getResume() {
		return resume;
	}

	public void setResume(String resume) {
		this.resume = resume;
	}

	public String getCreate_person() {
		return create_person;
	}

	public void setCreate_person(String create_person) {
		this.create_person = create_person;
	}

	public Date getCreate_at() {
		return create_at;
	}

	public void setCreate_at(Date create_at) {
		this.create_at = create_at;
	}

	public Integer getClass_id() {
		return class_id;
	}

	public void setClass_id(Integer class_id) {
		this.class_id = class_id;
	}

	public String getClass_name() {
		return class_name;
	}

	public void setClass_name(String class_name) {
		this.class_name = class_name;
	}

	@Override
	public String toString() {
		return "BaseUserInfo [id=" + id + ", certificate_code=" + certificate_code + ", name=" + name + ", department_level=" + department_level
				+ ", duty_name=" + duty_name + ", telephone=" + telephone + ", native_place=" + native_place + ", birth_date=" + birth_date + ", enlisted_date="
				+ enlisted_date + ", political_date=" + political_date + ", literacy=" + literacy + ", dutylevel=" + dutylevel + ", technologylevel="
				+ technologylevel + ", technologylevel_date=" + technologylevel_date + ", ranks=" + ranks + ", ranks_date=" + ranks_date + ", note=" + note
				+ ", cover_url=" + cover_url + ", resume=" + resume + ", create_person=" + create_person + ", create_at=" + create_at + ", class_id=" + class_id
				+ ", class_name=" + class_name + "]";
	}

	public Integer getIs_audit() {
		return is_audit;
	}

	public void setIs_audit(Integer is_audit) {
		this.is_audit = is_audit;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
}
