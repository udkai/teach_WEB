package com.balance.base.model;

import java.util.Date;

/**
 * Created by dsy on 2017/4/19.
 * 班级表  Model
 */
public class BaseClass {
    private int id;  //主键自增
    private String name;  //班级名称
    private String batch;  //批次
    private String total_batch;  //总批次
    private String detail;  //班级描述
    private String create_person;  //创建人
    private Date create_at;   //创建时间

    private Integer userinfo_cnt;  //班级下学员人数 有学员不可删除

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

    public String getBatch() {
        return batch;
    }

    public void setBatch(String batch) {
        this.batch = batch;
    }

    public String getTotal_batch() {
        return total_batch;
    }

    public void setTotal_batch(String total_batch) {
        this.total_batch = total_batch;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
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

    public Integer getUserinfo_cnt() {
        return userinfo_cnt;
    }

    public void setUserinfo_cnt(Integer userinfo_cnt) {
        this.userinfo_cnt = userinfo_cnt;
    }
}
