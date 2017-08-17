package com.balance.common.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.balance.common.vo.ComboboxVO;
import com.balance.util.string.StringUtil;

@Repository
public class CommonDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * 下拉列表
     *
     * @param table_name数据库表名
     * @param value编号
     * @param content显示内容
     * @param order按照什么排序
     * @param sort            排序是升序还是降序
     * @param filter过滤条件
     * @return
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    public List<ComboboxVO> listCombobox(String table_name, String value, String content, String order, String sort,
                                         String condition) {
        String strSql = "select " + value + " value," + content + " content from " + table_name;
        if (StringUtil.isNotNullOrEmpty(condition))
            strSql += " where " + condition;
        if (StringUtil.isNotNullOrEmpty(order))
            strSql += " order by " + order;
        if (StringUtil.isNotNullOrEmpty(sort))
            strSql += sort;
        return jdbcTemplate.query(strSql, new BeanPropertyRowMapper(ComboboxVO.class));
    }

    /**
     * 班级列表
     *
     * @return
     */
    public List<ComboboxVO> listClass() {
        String strSql = "SELECT  id value, name content FROM t_base_class ORDER BY id DESC";
        return jdbcTemplate.query(strSql, BeanPropertyRowMapper.newInstance(ComboboxVO.class));
    }

    /**
     * 课程列表
     *
     * @return
     */
    public List<ComboboxVO> listCourse() {
        String strSql = "SELECT  id value, name content FROM t_base_course ORDER BY convert(name USING gbk) ASC";
        return jdbcTemplate.query(strSql, BeanPropertyRowMapper.newInstance(ComboboxVO.class));
    }

    public List<ComboboxVO> listCourseByUser(int create_id) {
        String strSql = "SELECT  id value, name content FROM t_base_course WHERE create_id=? ORDER BY convert(name USING gbk) ASC";
        return jdbcTemplate.query(strSql, new Object[]{create_id}, BeanPropertyRowMapper.newInstance(ComboboxVO.class));
    }

    /**
     * 课程列表
     *
     * @return
     */
    public List<ComboboxVO> listCourse(int type) {
        String strSql = "SELECT  id value, name content FROM t_base_course WHERE type= ? ORDER BY convert(name USING gbk) ASC";
        Object[] params = new Object[type];
        return jdbcTemplate.query(strSql, params, BeanPropertyRowMapper.newInstance(ComboboxVO.class));
    }

    /**
     * 教员列表
     *
     * @return
     */
    public List<ComboboxVO> listTeacher() {
        String strSql = "SELECT  id value, name content FROM t_base_teacherinfo ORDER BY convert(name USING gbk) ASC";
        return jdbcTemplate.query(strSql, BeanPropertyRowMapper.newInstance(ComboboxVO.class));
    }

    /**
     * 上课教员列表
     *
     * @return
     */
    public List<ComboboxVO> listClassTeacher() {
        String strSql = "SELECT  id value, name content FROM t_base_teacherinfo WHERE type=3 ORDER BY convert(name USING gbk) ASC";
        return jdbcTemplate.query(strSql, BeanPropertyRowMapper.newInstance(ComboboxVO.class));
    }

    /**
     * 课程类型列表
     *
     * @return
     */
    public List<ComboboxVO> listCourseType() {
        String strSql = "SELECT  id value, name content FROM t_base_coursetype ORDER BY convert(name USING gbk) ASC";
        return jdbcTemplate.query(strSql, BeanPropertyRowMapper.newInstance(ComboboxVO.class));
    }
}
