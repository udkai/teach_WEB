package com.balance.train.vo;

public class TrainBranchSearchVO {

	private String name;// 党支部名称

	public String getName() {
		return name;
	}

	public String getName_param() {
		return "%" + name + "%";
	}

	public void setName(String name) {
		this.name = name;
	}
}
