package com.balance.train.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.balance.train.model.TrainGroupactivities;
import com.balance.train.service.TrainGroupactivitiesService;
import com.balance.train.vo.TrainGroupactivitiesSearchVO;
import com.balance.util.backurl.BackUrlUtil;
import com.balance.util.config.PubConfig;
import com.balance.util.controller.BaseController;
import com.balance.util.global.GlobalConst;
import com.balance.util.page.PageNavigate;
import com.balance.util.session.SessionUtil;
import com.balance.util.string.StringUtil;
import com.balance.util.web.WebUtil;

/**
 * 党团活动
 * @author 孔垂云
 * @date 2017年4月22日
 */
@Controller
@RequestMapping("/train/groupactivities")
public class TrainGroupactivitiesController extends BaseController {
	@Autowired
	private TrainGroupactivitiesService trainGroupactivitiesService;
	@Autowired
	private PubConfig pubConfig;

	/**
	 *  首页
	 *
	 * @return 页面
	 */
	@RequestMapping("/index")
	public ModelAndView index(HttpServletRequest request, TrainGroupactivitiesSearchVO trainGroupactivitiesSearchVO) {
		setBtnAutho(request, "TrainGroupactivities");
		ModelAndView mv = new ModelAndView();
		int count = trainGroupactivitiesService.listCount(trainGroupactivitiesSearchVO);
		int pageIndex = WebUtil.getSafeInt(request.getParameter("pageIndex"), 1);// 获取当前页数
		int pageSize = GlobalConst.pageSize;// 直接取全局变量，每页记录数
		String url = createUrl(trainGroupactivitiesSearchVO, pageIndex, pageSize);
		PageNavigate pageNavigate = new PageNavigate(url, pageIndex, pageSize, count);//
		List<TrainGroupactivities> trainGroupactivitiesList = trainGroupactivitiesService.list(trainGroupactivitiesSearchVO, pageIndex, pageSize);
		mv.addObject("trainGroupactivitiesList", trainGroupactivitiesList);
		mv.addObject("pageNavigate", pageNavigate);
		BackUrlUtil.createBackUrl(mv, request, url);// 设置返回url
		mv.setViewName("/train/groupactivities");

		return mv;
	}

	private String createUrl(TrainGroupactivitiesSearchVO trainGroupactivitiesSearchVO, int pageIndex, int pageSize) {
		String url = pubConfig.getDynamicServer() + "/train/groupactivities/index.htm?";
		if (StringUtil.isNotNullOrEmpty(trainGroupactivitiesSearchVO.getName())) {
			url += " &name=" + trainGroupactivitiesSearchVO.getName();
		}
		return url;
	}

	@RequestMapping("/toAdd")
	public ModelAndView toAdd(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView("/train/groupactivitiesAdd");
		BackUrlUtil.setBackUrl(mv, request);
		return mv;
	}

	@RequestMapping("/add")
	public String add(TrainGroupactivities trainGroupactivities, HttpServletRequest request) {
		trainGroupactivities.setCreate_person(SessionUtil.getUserSession(request).getRealname());
		int flag = trainGroupactivitiesService.add(trainGroupactivities);
		if (flag == 0)
			return "forward:/error.htm?msg=" + StringUtil.encodeUrl("党团活动新增失败");
		else
			return "forward:/success.htm?msg=" + StringUtil.encodeUrl("党团活动新增成功");
	}

	@RequestMapping("/toUpdate")
	public ModelAndView toUpdate(HttpServletRequest request, int id) {
		ModelAndView mv = new ModelAndView("/train/groupactivitiesUpdate");

		TrainGroupactivities trainGroupactivities = trainGroupactivitiesService.get(id);
		mv.addObject("trainGroupactivities", trainGroupactivities);
		BackUrlUtil.setBackUrl(mv, request);
		return mv;
	}

	@RequestMapping("/update")
	public String update(TrainGroupactivities trainGroupactivities) {
		int flag = trainGroupactivitiesService.update(trainGroupactivities);
		if (flag == 0)
			return "forward:/error.htm?msg=" + StringUtil.encodeUrl("党团活动修改失败");
		else
			return "forward:/success.htm?msg=" + StringUtil.encodeUrl("党团活动修改成功");
	}

	@RequestMapping("/delete")
	public String delete(int id) {
		int flag = trainGroupactivitiesService.delete(id);
		if (flag == 0)
			return "forward:/error.htm?msg=" + StringUtil.encodeUrl("党团活动删除失败");
		else
			return "forward:/success.htm?msg=" + StringUtil.encodeUrl("党团活动删除成功");
	}
}
