package com.balance.teach.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.balance.teach.dao.TeachCategoryDao;
import com.balance.teach.dao.TeachPlanDao;
import com.balance.teach.dao.TeachRepositoryDao;
import com.balance.teach.dao.TeachScheduleDao;
import com.balance.teach.model.TeachCategory;
import com.balance.teach.model.TeachClassPlan;
import com.balance.teach.model.TeachCreatePerson;
import com.balance.teach.model.TeachPlan;
import com.balance.teach.model.TeachPlanRepository;
import com.balance.teach.model.TeachRepository;
import com.balance.teach.model.TeachSchedule;
import com.balance.teach.vo.TeachPlanSearchVO;
import com.balance.util.date.DateUtil;
import com.balance.util.string.StringUtil;

@Service
public class TeachPlanService {

	@Autowired
	private TeachPlanDao teachPlanDao;

	@Autowired
	private TeachCategoryDao teachCategoryDao;

	@Autowired
	private TeachRepositoryDao teachRepositoryDao;
	@Autowired
	private TeachScheduleDao teachScheduleDao;

	/**
	 * z总数
	 * 
	 * @param teachPlanSearchVO
	 * @return
	 */
	public int count(TeachPlanSearchVO teachPlanSearchVO) {
		return teachPlanDao.count(teachPlanSearchVO);
	}

	public List<TeachCreatePerson> list(){
		return teachPlanDao.list();
	}
	/**
	 * 列表
	 * 
	 * @param teachPlanSearchVO
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	public List<TeachPlan> search(TeachPlanSearchVO teachPlanSearchVO, int pageIndex, int pageSize) {
		return teachPlanDao.search(teachPlanSearchVO, pageIndex, pageSize);
	}

	/**
	 * 资源文件复选框
	 * 
	 * @return
	 */
	public String createZtreeByPlan(int upload_user_id) {
		List<TeachCategory> listCategory = teachCategoryDao.list();// 模块列表
		List<TeachRepository> listRepository;
		StringBuilder sb = new StringBuilder();
		for (TeachCategory teachCategory : listCategory) {
			if (teachCategory.getLevel().equals(1) || teachCategory.getLevel().equals(2)) {
				sb.append("{id : \"" + teachCategory.getId() + "category" + "\",pId :\"" + teachCategory.getParent_id() + "category"
						+ "\",name :\"" + teachCategory.getName() + "\",level :\"" + teachCategory.getLevel() + "\",checked : false"
						+ ",open : false");
				sb.append("},");
				listRepository = teachRepositoryDao.findByCategoryIdAndUserId(teachCategory.getId(),upload_user_id);
				if (listRepository.size() > 0) {// 如果该章节有教学资源上传，则拼到下一级
					for (TeachRepository teachRepository : listRepository) {
						sb.append("{id : \"" + teachRepository.getId() + "\",pId :\"" + teachCategory.getId() + "category" + "\",name :\""
								+ teachRepository.getFile_name() + "\",title :\"" + teachRepository.getTitle() + "\",level :\"" + "3"
								+ "\",checked : false" + ",open : false");
						sb.append("},");
					}
				}
			}
		}
		return StringUtil.subTract(sb.toString());
	}

	/**
	 * 新增
	 * 
	 * @param teachPlan
	 * @return
	 */
	public int add(TeachPlan teachPlan) {
		int id = teachPlanDao.add(teachPlan);
		if (id > 0) {
			if (StringUtil.isNotNullOrEmpty(teachPlan.getRepository_id())) {// 如果教学资源不为空
				String st[] = teachPlan.getRepository_id().split(",");
				TeachPlanRepository teachPlanRepository = new TeachPlanRepository();
				for (String repository_id : st) {
					teachPlanRepository.setPlan_id(id);
					teachPlanRepository.setRepository_id(Integer.parseInt(repository_id));
					teachPlanDao.addPlanRepository(teachPlanRepository);// 新增教学计划对应教学资源信息
				}
			}
			if (StringUtil.isNotNullOrEmpty(teachPlan.getPlanclass_type())) {// 如果勾选了每周几排课
				String week[] = teachPlan.getPlanclass_type().split(",");
				String start_time[] = teachPlan.getStart_time().split(",");// 起始时间
				String end_time[] = teachPlan.getEnd_time().split(",");// 终止时间
				TeachClassPlan teachClassPlan = new TeachClassPlan();
				for (String day : week) {
					teachClassPlan.setPlan_id(id);// 教学计划ID
					teachClassPlan.setPlan_type(Integer.parseInt(day));// 排课类型
					teachClassPlan.setStart_time(start_time[Integer.parseInt(day) - 1]);// 起始时间
					teachClassPlan.setEnd_time(end_time[Integer.parseInt(day) - 1]);// 终止时间
					teachPlanDao.addPlanClass(teachClassPlan);
				}
			}
			return 1;
		} else {
			return 0;
		}
	}

	/**
	 * 修改
	 * 
	 * @param teachPlan
	 * @return
	 */
	public int update(TeachPlan teachPlan) {
		TeachPlan teachPlanOld = teachPlanDao.get(teachPlan.getId());
		if (teachPlanOld.getStatus().equals(1)) {
			return 2;
		}
		int flag = teachPlanDao.update(teachPlan);
		if (flag == 1) {
			if (StringUtil.isNotNullOrEmpty(teachPlan.getRepository_id())) {// 如果教学资源不为空
				String st[] = teachPlan.getRepository_id().split(",");
				TeachPlanRepository teachPlanRepository = new TeachPlanRepository();
				teachPlanDao.deletePlanRepository(teachPlan.getId());
				for (String repository_id : st) {
					teachPlanRepository.setPlan_id(teachPlan.getId());
					teachPlanRepository.setRepository_id(Integer.parseInt(repository_id));
					teachPlanDao.addPlanRepository(teachPlanRepository);// 新增教学计划对应教学资源信息
				}
			}
			if (StringUtil.isNotNullOrEmpty(teachPlan.getPlanclass_type())) {// 如果勾选了每周几排课
				String week[] = teachPlan.getPlanclass_type().split(",");
				String start_time[] = teachPlan.getStart_time().split(",");// 起始时间
				String end_time[] = teachPlan.getEnd_time().split(",");// 终止时间
				TeachClassPlan teachClassPlan = new TeachClassPlan();
				teachPlanDao.deletePlanClass(teachPlan.getId());
				for (String day : week) {
					teachClassPlan.setPlan_id(teachPlan.getId());// 教学计划ID
					teachClassPlan.setPlan_type(Integer.parseInt(day));// 排课类型
					teachClassPlan.setStart_time(start_time[Integer.parseInt(day) - 1]);// 起始时间
					teachClassPlan.setEnd_time(end_time[Integer.parseInt(day) - 1]);// 终止时间
					teachPlanDao.addPlanClass(teachClassPlan);
				}
			}
		}
		return flag;
	}

	/**
	 * 删除
	 * 
	 * @param id
	 * @return
	 */
	public int delete(int id) {
		TeachPlan teachPlanOld = teachPlanDao.get(id);
		if (teachPlanOld.getStatus().equals(1)) {
			return 2;
		}
		teachPlanDao.deletePlanRepository(id);
		teachPlanDao.deletePlanClass(id);
		return teachPlanDao.delete(id);
	}

	/**
	 * 审核
	 * 
	 * @param teachPlan
	 * @param type
	 * @return
	 */
	public int saveCheck(TeachPlan teachPlan, int type) {
		TeachPlan teachPlanOld = teachPlanDao.get(teachPlan.getId());
		if (teachPlanOld == null) {
			return 2;
		}
		if (!teachPlanOld.getStatus().equals(0)) {
			return 3;
		}
		teachPlanOld.setStatus(type);
		int flag = teachPlanDao.saveCheck(teachPlanOld);
		if (type == 1 && flag == 1) {// 如果审核通过且成功 生成课表
			addSchedule(teachPlanOld);
		}
		return flag;
	}

	/**
	 * 生成课表
	 * 
	 * @param teachPlan
	 */
	public void addSchedule(TeachPlan teachPlan) {
		Map<Integer, TeachClassPlan> planMap = teachPlanDao.listPlanClassById(teachPlan.getId());
		List<String> dayList = DateUtil.getDayList(teachPlan.getStart_date(), teachPlan.getEnd_date());// 通过教学计划获取起始终止时间天数
		for (String day : dayList) {
			int week = DateUtil.getWeekByDate(day);// 获取起始至终止时间 每一天都是星期几
			if (planMap.containsKey(week)) {// 如果在设置的每周几上中
				TeachSchedule teachSchedule = new TeachSchedule();
				teachSchedule.setEnd_time(planMap.get(week).getEnd_time());// 终止时间
				teachSchedule.setPlan_id(teachPlan.getId());// 教学计划ID
				teachSchedule.setStart_time(planMap.get(week).getStart_time());// 起始时间
				teachSchedule.setTeach_class_id(teachPlan.getTeach_class_id());// 班级iD
				teachSchedule.setTeach_course_id(teachPlan.getTeach_course_id());// 课程id
				teachSchedule.setTeach_date(day);// 上课日期
				teachSchedule.setTeach_teacher_id(teachPlan.getTeach_teacher_id());// 教员id
				teachScheduleDao.add(teachSchedule);
			}
		}

	}

	/**
	 * 通过ID获取实体
	 * 
	 * @param id
	 * @return
	 */
	public TeachPlan get(int id) {
		return teachPlanDao.get(id);
	}

	/**
	 * 教学计划对应教学资源
	 * 
	 * @param id
	 * @return
	 */
	public List<TeachPlanRepository> getPlanRepositoryById(int id) {
		return teachPlanDao.listPlanRepositoryById(id);
	}

	/**
	 * 教学计划对应排课内容(新增 修改使用)
	 * 
	 * @param id
	 * @return
	 */
	public List<TeachClassPlan> getPlanClassById(int id) {
		List<TeachClassPlan> list = new ArrayList<>();
		Map<Integer, TeachClassPlan> planMap = teachPlanDao.listPlanClassById(id);
		if (planMap.containsKey(1)) {// 如果设置了周一
			TeachClassPlan teachClassPlan = planMap.get(1);
			teachClassPlan.setPlan_type_name("每周一");
			teachClassPlan.setChecked("checked");// 设置前台选中状态
			list.add(teachClassPlan);
		} else {
			TeachClassPlan teachClassPlan = new TeachClassPlan();
			teachClassPlan.setPlan_type_name("每周一");
			teachClassPlan.setChecked("false");
			list.add(teachClassPlan);
		}
		if (planMap.containsKey(2)) {// 如果设置了周二
			TeachClassPlan teachClassPlan = planMap.get(2);
			teachClassPlan.setPlan_type_name("每周二");
			teachClassPlan.setChecked("checked");// 设置前台选中状态
			list.add(teachClassPlan);
		} else {
			TeachClassPlan teachClassPlan = new TeachClassPlan();
			teachClassPlan.setPlan_type_name("每周二");
			teachClassPlan.setChecked("false");
			list.add(teachClassPlan);
		}
		if (planMap.containsKey(3)) {// 如果设置了周三
			TeachClassPlan teachClassPlan = planMap.get(3);
			teachClassPlan.setPlan_type_name("每周三");
			teachClassPlan.setChecked("checked");// 设置前台选中状态
			list.add(teachClassPlan);
		} else {
			TeachClassPlan teachClassPlan = new TeachClassPlan();
			teachClassPlan.setPlan_type_name("每周三");
			teachClassPlan.setChecked("false");
			list.add(teachClassPlan);
		}
		if (planMap.containsKey(4)) {// 如果设置了周四
			TeachClassPlan teachClassPlan = planMap.get(4);
			teachClassPlan.setPlan_type_name("每周四");
			teachClassPlan.setChecked("checked");// 设置前台选中状态
			list.add(teachClassPlan);
		} else {
			TeachClassPlan teachClassPlan = new TeachClassPlan();
			teachClassPlan.setPlan_type_name("每周四");
			teachClassPlan.setChecked("false");
			list.add(teachClassPlan);
		}
		if (planMap.containsKey(5)) {// 如果设置了周五
			TeachClassPlan teachClassPlan = planMap.get(5);
			teachClassPlan.setPlan_type_name("每周五");
			teachClassPlan.setChecked("checked");// 设置前台选中状态
			list.add(teachClassPlan);
		} else {
			TeachClassPlan teachClassPlan = new TeachClassPlan();
			teachClassPlan.setPlan_type_name("每周五");
			teachClassPlan.setChecked("false");
			list.add(teachClassPlan);
		}
		if (planMap.containsKey(6)) {// 如果设置了周六
			TeachClassPlan teachClassPlan = planMap.get(6);
			teachClassPlan.setPlan_type_name("每周六");
			teachClassPlan.setChecked("checked");// 设置前台选中状态
			list.add(teachClassPlan);
		} else {
			TeachClassPlan teachClassPlan = new TeachClassPlan();
			teachClassPlan.setPlan_type_name("每周六");
			teachClassPlan.setChecked("false");
			list.add(teachClassPlan);
		}
		if (planMap.containsKey(7)) {// 如果设置了周日
			TeachClassPlan teachClassPlan = planMap.get(7);
			teachClassPlan.setPlan_type_name("每周日");
			teachClassPlan.setChecked("checked");// 设置前台选中状态
			list.add(teachClassPlan);
		} else {
			TeachClassPlan teachClassPlan = new TeachClassPlan();
			teachClassPlan.setPlan_type_name("每周日");
			teachClassPlan.setChecked("false");
			list.add(teachClassPlan);
		}

		return list;
	}

	/**
	 * 教学计划对应排课内容（审核时使用 只显示设置的排课内容）
	 * 
	 * @param id
	 * @return
	 */
	public List<TeachClassPlan> getPlanClassByIdAndAudit(int id) {
		List<TeachClassPlan> list = new ArrayList<>();
		Map<Integer, TeachClassPlan> planMap = teachPlanDao.listPlanClassById(id);
		if (planMap.containsKey(1)) {// 如果设置了周一
			TeachClassPlan teachClassPlan = planMap.get(1);
			teachClassPlan.setPlan_type_name("每周一");
			list.add(teachClassPlan);
		}
		if (planMap.containsKey(2)) {// 如果设置了周二
			TeachClassPlan teachClassPlan = planMap.get(2);
			teachClassPlan.setPlan_type_name("每周二");
			list.add(teachClassPlan);
		}
		if (planMap.containsKey(3)) {// 如果设置了周三
			TeachClassPlan teachClassPlan = planMap.get(3);
			teachClassPlan.setPlan_type_name("每周三");
			list.add(teachClassPlan);
		}
		if (planMap.containsKey(4)) {// 如果设置了周四
			TeachClassPlan teachClassPlan = planMap.get(4);
			teachClassPlan.setPlan_type_name("每周四");
			list.add(teachClassPlan);
		}
		if (planMap.containsKey(5)) {// 如果设置了周五
			TeachClassPlan teachClassPlan = planMap.get(5);
			teachClassPlan.setPlan_type_name("每周五");
			list.add(teachClassPlan);
		}
		if (planMap.containsKey(6)) {// 如果设置了周六
			TeachClassPlan teachClassPlan = planMap.get(6);
			teachClassPlan.setPlan_type_name("每周六");
			list.add(teachClassPlan);
		}
		if (planMap.containsKey(7)) {// 如果设置了周日
			TeachClassPlan teachClassPlan = planMap.get(7);
			teachClassPlan.setPlan_type_name("每周日");
			list.add(teachClassPlan);
		}

		return list;
	}
}
