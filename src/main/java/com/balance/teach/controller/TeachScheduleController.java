package com.balance.teach.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.balance.common.service.CommonService;
import com.balance.common.vo.ComboboxVO;
import com.balance.teach.model.TeachSchedule;
import com.balance.teach.service.TeachScheduleService;
import com.balance.teach.vo.TeachScheduleSearchVO;
import com.balance.util.backurl.BackUrlUtil;
import com.balance.util.config.PubConfig;
import com.balance.util.controller.BaseController;
import com.balance.util.date.DateUtil;
import com.balance.util.global.GlobalConst;
import com.balance.util.page.PageNavigate;
import com.balance.util.session.SessionUtil;
import com.balance.util.session.UserSession;
import com.balance.util.string.StringUtil;
import com.balance.util.web.WebUtil;

/**
 * 课表管理
 * @author 孔垂云
 * @date 2017年4月20日
 */
@Controller
@RequestMapping("/teach/schedule")
public class TeachScheduleController extends BaseController {
	@Autowired
	private TeachScheduleService teachScheduleService;
	@Autowired
	private PubConfig pubConfig;
	@Autowired
	private CommonService commonService;

	/**
	 * 课表信息管理
	 * @return
	 */
	@RequestMapping("/index")
	public ModelAndView index(HttpServletRequest request, TeachScheduleSearchVO teachScheduleSearchVO) {
		setBtnAutho(request, "TeachSchedule");
		ModelAndView mv = new ModelAndView();

		List<ComboboxVO> listCourse = new ArrayList<>();// 课程列表

		int role_id = SessionUtil.getUserSession(request).getRole_id();
		if (role_id == pubConfig.getAudit_role_id()) {
			listCourse = commonService.listCourse();// 所有课程列表
		} else if (role_id == pubConfig.getTeacher_role_id()) {
			listCourse = commonService.listCourseByUser(SessionUtil.getUserSession(request).getUser_id());// 自己的课程列表
			teachScheduleSearchVO.setTeach_teacher_id(SessionUtil.getUserSession(request).getUser_id());
		}

		int recordCount = teachScheduleService.listCount(teachScheduleSearchVO);// 获取查询总数
		int pageIndex = WebUtil.getSafeInt(request.getParameter("pageIndex"), 1);// 获取当前页数
		int pageSize = GlobalConst.pageSize;// 直接取全局变量，每页记录数
		String url = createUrl(teachScheduleSearchVO, pageIndex, pageSize);
		PageNavigate pageNavigate = new PageNavigate(url, pageIndex, pageSize, recordCount);//
		List<TeachSchedule> list = teachScheduleService.list(teachScheduleSearchVO, pageNavigate.getPageIndex(), pageSize);
		mv.addObject("pageNavigate", pageNavigate);// 设置分页的变量
		mv.addObject("list", list);// 把获取的记录放到mv里面
		mv.setViewName("/teach/schedule");// 跳转至指定页面
		BackUrlUtil.createBackUrl(mv, request, url);// 设置返回url

		List<ComboboxVO> listClass = commonService.listClass();// 班级列表
		List<ComboboxVO> listTeacher = commonService.listClassTeacher();// 教员列表
		mv.addObject("listClass", listClass);
		mv.addObject("listCourse", listCourse);
		mv.addObject("listTeacher", listTeacher);
		;

		return mv;
	}

	// 设置分页url，一般有查询条件的才会用到
	private String createUrl(TeachScheduleSearchVO teachScheduleSearchVO, int pageIndex, int pageSize) {
		String url = pubConfig.getDynamicServer() + "/teach/schedule/index.htm?";
		if (teachScheduleSearchVO.getTeach_class_id() != null)
			url += "&teach_class_id=" + teachScheduleSearchVO.getTeach_class_id();
		if (teachScheduleSearchVO.getTeach_course_id() != null)
			url += "&teach_course_id=" + teachScheduleSearchVO.getTeach_course_id();
		if (teachScheduleSearchVO.getTeach_teacher_id() != null)
			url += "&teach_teacher_id=" + teachScheduleSearchVO.getTeach_teacher_id();
		if (StringUtil.isNotNullOrEmpty(teachScheduleSearchVO.getTeach_date()))
			url += "&teach_date=" + teachScheduleSearchVO.getTeach_date();
		return url;
	}

	/**
	 * 我的课表信息
	 * 查询当周的课表信息，简化一些
	 *
	 * @return
	 */
	@RequestMapping("/my")
	public ModelAndView my(HttpServletRequest request, TeachScheduleSearchVO teachScheduleSearchVO) {
		setBtnAutho(request, "TeachScheduleMy");
		ModelAndView mv = new ModelAndView();
		UserSession userSession = SessionUtil.getUserSession(request);
		String query_date = teachScheduleSearchVO.getTeach_date();
		int class_id = 0;
		if (StringUtil.isNullOrEmpty(query_date))
			query_date = DateUtil.getSystemDate();
		if (userSession.getType() == 4) {// 如果为学员，则从session中取
			class_id = userSession.getClass_id();
		} else {
			if (teachScheduleSearchVO.getTeach_class_id() == null)
				class_id = 0;
			else
				class_id = teachScheduleSearchVO.getTeach_class_id();// 如果为教员则从选择的取
		}
		List<TeachSchedule> list = teachScheduleService.searchSchedule(class_id, query_date, DateUtil.getOpeDate(query_date, 7));
		mv.addObject("list", list);// 把获取的记录放到mv里面
		List<String> listDate = new ArrayList<>();
		List<String> listDateStr = new ArrayList<>();
		for (int i = 0; i < 7; i++) {
			String date = DateUtil.getOpeDate(query_date, i);
			String week = DateUtil.getWeekOfDate(date);
			listDateStr.add(date + "</br>" + week);
			listDate.add(date);
		}
		mv.addObject("listDate", listDate);
		mv.addObject("listDateStr", listDateStr);
		List<ComboboxVO> listClass = commonService.listClass();// 班级列表
		mv.addObject("listClass", listClass);
		teachScheduleSearchVO.setTeach_date(query_date);
		mv.setViewName("/teach/scheduleMy");// 跳转至指定页面
		return mv;
	}
}
