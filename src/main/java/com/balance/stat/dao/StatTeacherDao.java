package com.balance.stat.dao;

import java.util.List;

import com.balance.teach.model.TeachSchedule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.balance.stat.vo.StatTeacherSearchVO;
import com.balance.stat.vo.StatTeacherVO;
import com.balance.util.page.PageUtil;
import com.balance.util.string.StringUtil;

@Repository
public class StatTeacherDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * 查询
     *
     * @param pageIndex
     * @param pageSize
     * @return
     */
    public List<StatTeacherVO> list(StatTeacherSearchVO statTeacherSearchVO, int pageIndex, int pageSize) {
        String sql = "select *,t.id teacher_id,(select count(*) from t_base_classstudent where class_id in " +
                "(select teach_class_id from t_teach_schedule where teach_teacher_id=t.id)) teach_num," +
                "(select avg(score) from t_exam_result where class_id in " +
                "(select teach_class_id from t_teach_schedule where teach_teacher_id=t.id)) average_score  from t_base_teacherinfo t where 1=1 and type=3 ";
        sql += createSearchSql(statTeacherSearchVO);
        sql += " order by certificate_code asc";
        sql = PageUtil.createMysqlPageSql(sql, pageIndex, pageSize);
        NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
        SqlParameterSource paramSource = new BeanPropertySqlParameterSource(statTeacherSearchVO);
        List<StatTeacherVO> list = namedParameterJdbcTemplate.query(sql, paramSource,
                BeanPropertyRowMapper.newInstance(StatTeacherVO.class));
        return list;
    }

    /**
     * 查询
     *
     * @param statTeacherSearchVO
     * @return
     */
    public int listCount(StatTeacherSearchVO statTeacherSearchVO) {
        String sql = "select count(*) from t_base_teacherinfo where 1=1  and type=3 ";
        sql += createSearchSql(statTeacherSearchVO);
        NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
        SqlParameterSource paramSource = new BeanPropertySqlParameterSource(statTeacherSearchVO);
        return namedParameterJdbcTemplate.queryForObject(sql, paramSource, Integer.class);
    }

    private String createSearchSql(StatTeacherSearchVO statTeacherSearchVO) {
        String sql = "";
        if (StringUtil.isNotNullOrEmpty(statTeacherSearchVO.getCertificate_code())) {
            sql += " and certificate_code=:certificate_code";
        }
        if (StringUtil.isNotNullOrEmpty(statTeacherSearchVO.getName())) {
            sql += " and name like :name_param";
        }
        return sql;
    }

    /**
     * 获取上课教员已教授的课表信息
     * @param teacher_id 教员id
     * @return 课表列表
     */
    public List<TeachSchedule> getScheduledByTeacher(int teacher_id) {
        String sql = "SELECT * FROM  t_teach_schedule WHERE teach_teacher_id=? AND teach_date<=current_date " +
                "AND id IN (SELECT schedule_id FROM t_teach_signup WHERE before_class IS NOT NULL AND after_class IS NOT NULL)";
        return jdbcTemplate.query(sql, new Object[]{teacher_id}, new BeanPropertyRowMapper<>(TeachSchedule.class));
    }
}
