package com.balance.base.model;

import java.util.List;

/**
 * Created by dsy on 2017/4/17.
 * 基础字典表 model
 */
public class BaseDic {
    private String code;  //编号 主键
    private String name;  //名称
    private int display_order;  //排序
    private int type; //类型 1-文化程度  2-职务等级  3-军衔  4-技术等级

    private List<BaseDic> baseDicList;  //接收数据

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDisplay_order() {
        return display_order;
    }

    public void setDisplay_order(int display_order) {
        this.display_order = display_order;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public List<BaseDic> getBaseDicList() {
        return baseDicList;
    }

    public void setBaseDicList(List<BaseDic> baseDicList) {
        this.baseDicList = baseDicList;
    }
}
