package com.balance.exam.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.balance.exam.model.*;
import com.balance.util.json.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.balance.base.model.BaseUserInfo;
import com.balance.base.service.BaseUserInfoService;
import com.balance.common.service.CommonService;
import com.balance.common.vo.ComboboxVO;
import com.balance.exam.service.ExamPaperService;
import com.balance.exam.service.ExamSubjectService;
import com.balance.exam.vo.ExamPaperSearchVO;
import com.balance.util.backurl.BackUrlUtil;
import com.balance.util.config.PubConfig;
import com.balance.util.controller.BaseController;
import com.balance.util.global.GlobalConst;
import com.balance.util.page.PageNavigate;
import com.balance.util.session.SessionUtil;
import com.balance.util.string.StringUtil;
import com.balance.util.web.WebUtil;

/**
 * Created by dsy on 2017/4/25.
 * 在线考试 Controller
 */
@Controller
@RequestMapping("/exam/online")
public class ExamStudentPaperController extends BaseController {

    @Autowired
    private ExamPaperService examPaperService;
    @Autowired
    private ExamSubjectService examSubjectService;
    @Autowired
    private BaseUserInfoService baseUserInfoService;
    @Autowired
    private PubConfig pubConfig;
    @Autowired
    private CommonService commonService;

    /**
     * 试卷类型首页
     *
     * @return 页面
     */
    @RequestMapping("/index")
    public ModelAndView index(HttpServletRequest request, ExamPaperSearchVO examPaperSearchVO) {
        setBtnAutho(request, "ExamOnline");
        ModelAndView mv = new ModelAndView();
        examPaperSearchVO.setStatus(30);
        int pageIndex = WebUtil.getSafeInt(request.getParameter("pageIndex"), 1);// 获取当前页数
        int pageSize = GlobalConst.pageSize;// 直接取全局变量，每页记录数


        int role_id = SessionUtil.getUserSession(request).getRole_id();
        List<ExamPaper> examPaperList = new ArrayList<>();
        if (role_id == pubConfig.getStudent_role_id()) {
            examPaperSearchVO.setStudent_id(SessionUtil.getUserSession(request).getUser_id());
            if (examPaperSearchVO.getType() == null) {
                examPaperSearchVO.setType(1);
            }
            examPaperList = examPaperService.searchForStudent(examPaperSearchVO, pageIndex, pageSize);
        } else if (role_id == pubConfig.getTeacher_role_id()) {
            examPaperSearchVO.setTeacher_id(SessionUtil.getUserSession(request).getUser_id());
            examPaperSearchVO.setType(1);
            examPaperList = examPaperService.searchForTeacher(examPaperSearchVO, pageIndex, pageSize);
        }
        mv.addObject("examPaperList", examPaperList);

        String url = createUrl(examPaperSearchVO, pageIndex, pageSize);
        int count = examPaperService.count(examPaperSearchVO);
        PageNavigate pageNavigate = new PageNavigate(url, pageIndex, pageSize, count);//
        mv.addObject("pageNavigate", pageNavigate);
        mv.addObject("backUrl", StringUtil.encodeUrl(url));

        List<ComboboxVO> listClass = commonService.listClass();// 班级列表
        List<ComboboxVO> listCourse = commonService.listCourse();// 课程列表

        mv.addObject("listClass", listClass);
        mv.addObject("listCourse", listCourse);

        mv.setViewName("/exam/online");

        return mv;
    }

    private String createUrl(ExamPaperSearchVO examPaperSearchVO, int pageIndex, int pageSize) {
        String url = "/exam/online/index.htm?pageIndex=" + pageIndex + "&pageSize=" + pageSize;
        if (StringUtil.isNotNullOrEmpty(examPaperSearchVO.getTitle())) {
            url += "&title=" + examPaperSearchVO.getTitle();
        }
        if (examPaperSearchVO.getCourse_id() != null) {
            url += "&course_id=" + examPaperSearchVO.getCourse_id();
        }
        if (examPaperSearchVO.getClass_id() != null) {
            url += "&class_id=" + examPaperSearchVO.getClass_id();
        }
        if (examPaperSearchVO.getStatus() != null) {
            url += "&status=" + examPaperSearchVO.getStatus();
        }
        if (examPaperSearchVO.getType() != null) {
            url += "&type=" + examPaperSearchVO.getType();
        }
        return url;
    }

    @RequestMapping("/toAnswer")
    public ModelAndView toAnswer(HttpServletRequest request, int paper_id) {
        ModelAndView mv = new ModelAndView("/exam/onlineAnswer");
        ExamPaper examPaper = examPaperService.get(paper_id);
        mv.addObject("examPaper", examPaper);
        int userinfo_id = SessionUtil.getUserSession(request).getUser_id();

        ExamStudentPaper examStudentPaper = examPaperService.getByPaperIdAndUserId(paper_id, userinfo_id);
        mv.addObject("examStudentPaper", examStudentPaper);


        List<ExamStudentAnswerPaper> paperSubjectList = examSubjectService.getByUserInfoId(paper_id, userinfo_id);
        mv.addObject("paperSubjectList", paperSubjectList);


        /*答题时 将状态改为已开始答题  并记录开始答题时间*/
        examPaperService.updateStatus(20, paper_id, userinfo_id);
        examPaperService.updateStartTime(paper_id, userinfo_id);
        BackUrlUtil.setBackUrl(mv, request);

        return mv;
    }

    @RequestMapping("/answer")
    public String answer(HttpServletRequest request, ExamStuPaperSubject examStuPaperSubject) {
        examStuPaperSubject.setStudent_id(SessionUtil.getUserSession(request).getUser_id());
        int flag = examSubjectService.updateAnswer(examStuPaperSubject);
        if (flag == 0)
            return "forward:/error.htm?msg=" + StringUtil.encodeUrl("答题失败");
        else
            return "forward:/success.htm?msg=" + StringUtil.encodeUrl("答题完成");
    }

    @RequestMapping("/toViewOne")
    public ModelAndView toViewOne(HttpServletRequest request, int paper_id) {
        ModelAndView mv = new ModelAndView("/exam/onlineView");

        int student_id = SessionUtil.getUserSession(request).getUser_id();
        ExamPaper examPaper = examPaperService.get(paper_id);
        if (examPaper == null) {
            mv.addObject("msg", StringUtil.encodeUrl("试卷不存在"));
            mv.setViewName("/error");
            return mv;
        }

        BaseUserInfo baseUserInfo = baseUserInfoService.get(student_id);
        mv.addObject("baseUserInfo", baseUserInfo);


        List<ExamStudentAnswerPaper> studentAnswerPaperList = examSubjectService.findStudentPaper(paper_id, student_id);
        mv.addObject("studentAnswerPaperList", studentAnswerPaperList);

        mv.addObject("examPaper", examPaper);
        BackUrlUtil.setBackUrl(mv, request);
        return mv;
    }

    @RequestMapping("/toAudit")
    public ModelAndView toAudit(HttpServletRequest request, int paper_id) {
        ModelAndView mv = new ModelAndView("/exam/onlineStudent");

        ExamPaper examPaper = examPaperService.get(paper_id);

        if (examPaper == null) {
            mv.addObject("msg", StringUtil.encodeUrl("试卷不存在"));
            mv.setViewName("/error");
            return mv;
        }
        List<BaseUserInfo> answerStudentList = examSubjectService.findForAudit(paper_id);
        mv.addObject("answerStudentList", answerStudentList);
        mv.addObject("paper_id", paper_id);

        mv.addObject("examPaper", examPaper);
        BackUrlUtil.setBackUrl(mv, request);

        return mv;
    }

    @RequestMapping("/toAuditOne")
    public ModelAndView toAuditOne(HttpServletRequest request, int paper_id, int student_id) {
        ModelAndView mv = new ModelAndView("/exam/onlineAudit");

        ExamPaper examPaper = examPaperService.get(paper_id);
        if (examPaper == null) {
            mv.addObject("msg", StringUtil.encodeUrl("试卷不存在"));
            mv.setViewName("/error");
            return mv;
        }

        BaseUserInfo baseUserInfo = baseUserInfoService.get(student_id);
        mv.addObject("baseUserInfo", baseUserInfo);


        List<ExamStudentAnswerPaper> studentAnswerPaperList = examSubjectService.findStudentPaper(paper_id, student_id);
        mv.addObject("studentAnswerPaperList", studentAnswerPaperList);
        mv.addObject("paper_id", paper_id);

        mv.addObject("examPaper", examPaper);
        BackUrlUtil.createBackUrl(mv, request, "/exam/online/toAudit.htm?paper_id=" + paper_id);
        return mv;
    }

    @RequestMapping("/audit")
    public String audit(HttpServletRequest request, ExamStudentAnswerPaper examStudentAnswerPaper) {
        examStudentAnswerPaper.setTeacher_id(SessionUtil.getUserSession(request).getUser_id());
        examStudentAnswerPaper.setTeacher_name(SessionUtil.getUserSession(request).getRealname());

        int flag = examSubjectService.updateAudit(examStudentAnswerPaper);
        if (flag == 0)
            return "forward:/error.htm?msg=" + StringUtil.encodeUrl("评分失败");
        else
            return "forward:/success.htm?msg=" + StringUtil.encodeUrl("评分完成");
    }

    /**
     * 保存答题记录
     *
     * @param answer   答案
     * @param response 响应
     */
    @RequestMapping("/saveAnswer")
    public void saveAnswer(String answer, int paper_id, int subject_id, int minutes, String subject_type, HttpServletResponse response, HttpServletRequest request) {
        int userinfo_id = SessionUtil.getUserSession(request).getUser_id();
        examPaperService.updateMinutes(minutes, paper_id, userinfo_id);
        int flag = examSubjectService.updateTestAnswer(answer, paper_id, subject_id, userinfo_id, subject_type);
        if (flag == 1)
            WebUtil.out(response, JsonUtil.createOperaStr(true, "保存成功"));
        else
            WebUtil.out(response, JsonUtil.createOperaStr(false, "保存失败"));
    }

}
