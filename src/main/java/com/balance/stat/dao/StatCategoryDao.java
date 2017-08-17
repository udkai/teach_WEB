package com.balance.stat.dao;

import com.balance.stat.vo.StatCategoryVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by dsy on 2017/6/1.
 * 知识点分类统计Dao
 */
@Repository
public class StatCategoryDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<StatCategoryVO> statCategory(StatCategoryVO statCategoryVO) {
        String sql = "SELECT tes.category_id,(SELECT name FROM t_teach_category WHERE id=tes.category_id) category_name," +
                "ifnull(sum(tesp.score),0) true_score,sum(tep.score) total_score,ifnull(sum(tesp.score),0)/sum(tep.score) ratio FROM t_exam_stupapersubject tesp LEFT JOIN t_exam_subject tes ON tesp.subject_id=tes.id " +
                "LEFT JOIN t_exam_papersubject tep ON tesp.paper_id=tep.paper_id AND tesp.subject_id=tep.subject_id " +
                " WHERE tesp.paper_id IN (SELECT paper_id FROM t_exam_studentpaper WHERE status=40)";
        sql += createSql(statCategoryVO);
        sql += " GROUP BY tes.category_id " +
                "ORDER BY ratio";
        SqlParameterSource param = new BeanPropertySqlParameterSource(statCategoryVO);
        return new NamedParameterJdbcTemplate(jdbcTemplate).query(sql,param, new BeanPropertyRowMapper<>(StatCategoryVO.class));
    }

    private String createSql(StatCategoryVO statCategoryVO){
        String sql ="";
        if(statCategoryVO.getCourse_id() !=null){
            if(statCategoryVO.getTopic_id() !=null){
                sql += " and tes.category_id in (select id from t_teach_category where parent_id=:topic_id)";
            }else{
                sql += " and tes.category_id in (select id from t_teach_category where parent_id in (select id from t_teach_category where parent_id=:course_id))";
            }
        }

        return sql;
    }
    /**
     * 获取答题人数
     *
     * @return 答题学员数
     */
    public List<Integer> getAnswerStuNumber() {
        String sql = "SELECT count(*) FROM t_exam_stupapersubject tesp " +
                " WHERE tesp.paper_id IN (SELECT paper_id FROM t_exam_studentpaper WHERE status=40) GROUP BY student_id";
        return jdbcTemplate.queryForList(sql, Integer.class);
    }

    /**
     * 获取答题题目数
     *
     * @return 答题题目数
     */
    public int getSubjectNumber() {
        String sql = "SELECT count(*) FROM t_exam_stupapersubject tesp " +
                " WHERE tesp.paper_id IN (SELECT paper_id FROM t_exam_studentpaper WHERE status=40)";
        return jdbcTemplate.queryForObject(sql, Integer.class);
    }
}
