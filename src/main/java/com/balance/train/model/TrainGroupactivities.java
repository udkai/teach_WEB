package com.balance.train.model;

import java.util.Date;

/**
 * 党团活动
 * @author 孔垂云
 * @date 2017年4月22日
 */
public class TrainGroupactivities {
	private int id;//
	private String name;// 活动名称
	private String content;// 活动内容
	private String start;// 活动开始时间
	private String end;// 活动结束时间
	private String secretary;// 支部书记
	private String create_person;// 创建人
	private Date create_date;// 创建时间

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getStart() {
		return start;
	}

	public void setStart(String start) {
		this.start = start;
	}

	public String getEnd() {
		return end;
	}

	public void setEnd(String end) {
		this.end = end;
	}

	public String getSecretary() {
		return secretary;
	}

	public void setSecretary(String secretary) {
		this.secretary = secretary;
	}

	public String getCreate_person() {
		return create_person;
	}

	public void setCreate_person(String create_person) {
		this.create_person = create_person;
	}

	public Date getCreate_date() {
		return create_date;
	}

	public void setCreate_date(Date create_date) {
		this.create_date = create_date;
	}

}
