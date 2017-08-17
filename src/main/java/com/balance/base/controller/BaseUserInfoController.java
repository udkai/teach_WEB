package com.balance.base.controller;

import com.balance.base.model.BaseDic;
import com.balance.base.model.BaseUserInfo;
import com.balance.base.service.BaseDicService;
import com.balance.base.service.BaseUserInfoService;
import com.balance.base.vo.BaseUserInfoSearchVO;
import com.balance.common.service.CommonService;
import com.balance.util.backurl.BackUrlUtil;
import com.balance.util.code.SerialNumUtil;
import com.balance.util.config.PubConfig;
import com.balance.util.controller.BaseController;
import com.balance.util.date.DateUtil;
import com.balance.util.file.FileUtil;
import com.balance.util.global.GlobalConst;
import com.balance.util.page.PageNavigate;
import com.balance.util.session.SessionUtil;
import com.balance.util.string.StringUtil;
import com.balance.util.web.WebUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.List;

/**
 * Created by dsy on 2017/4/17.
 * 学员档案 Controller
 */
@Controller
@RequestMapping("/base/userinfo")
public class BaseUserInfoController extends BaseController {

    @Autowired
    private BaseUserInfoService baseUserInfoService;
    @Autowired
    private BaseDicService baseDicService;
    @Autowired
    private CommonService commonService;
    @Autowired
    private PubConfig pubConfig;

    /**
     * 学员档案类型首页
     *
     * @return 页面
     */
    @RequestMapping("/index")
    public ModelAndView index(HttpServletRequest request, BaseUserInfoSearchVO baseUserInfoSearchVO) {
        setBtnAutho(request, "BaseUserInfo");
        ModelAndView mv = new ModelAndView();
        int count = baseUserInfoService.count(baseUserInfoSearchVO);
        int pageIndex = WebUtil.getSafeInt(request.getParameter("pageIndex"), 1);// 获取当前页数
        int pageSize = GlobalConst.pageSize;// 直接取全局变量，每页记录数

        String url = createUrl(baseUserInfoSearchVO, pageIndex, pageSize);
        PageNavigate pageNavigate = new PageNavigate(url, pageIndex, pageSize, count);//

        List<BaseUserInfo> baseUserInfoList = baseUserInfoService.search(baseUserInfoSearchVO, pageIndex, pageSize);
        mv.addObject("baseUserInfoList", baseUserInfoList);
        mv.addObject("pageNavigate", pageNavigate);
        mv.addObject("backUrl", StringUtil.encodeUrl(url));
        mv.setViewName("/base/userinfo");

        return mv;
    }

    private String createUrl(BaseUserInfoSearchVO baseUserInfoSearchVO, int pageIndex, int pageSize) {
        String url = pubConfig.getDynamicServer() + "/base/userinfo/index.htm?";
        if (StringUtil.isNotNullOrEmpty(baseUserInfoSearchVO.getName())) {
            url += " &name=" + baseUserInfoSearchVO.getName();
        }
        if (StringUtil.isNotNullOrEmpty(baseUserInfoSearchVO.getCertificate_code())) {
            url += " &certificate_code=" + baseUserInfoSearchVO.getCertificate_code();
        }
        if (StringUtil.isNotNullOrEmpty(baseUserInfoSearchVO.getDepartment_level())) {
            url += " &department_level=" + baseUserInfoSearchVO.getDepartment_level();
        }
        return url;
    }

    @RequestMapping("/toAdd")
    public ModelAndView toAdd(HttpServletRequest request) {
        ModelAndView mv = new ModelAndView("/base/userinfoAdd");
        List<BaseDic> literacyList = baseDicService.findByType(1);
        List<BaseDic> dutylevelList = baseDicService.findByType(2);
        List<BaseDic> ranksList = baseDicService.findByType(3);
        List<BaseDic> technologylevelList = baseDicService.findByType(4);

        mv.addObject("literacyList", literacyList);
        mv.addObject("dutylevelList", dutylevelList);
        mv.addObject("ranksList", ranksList);
        mv.addObject("technologylevelList", technologylevelList);
        mv.addObject("classList", commonService.listClass());// 班级
        mv.addObject("baseUserinfo", new BaseUserInfo());
        BackUrlUtil.setBackUrl(mv, request);
        return mv;
    }

    @RequestMapping("/add")
    public String add(BaseUserInfo baseUserInfo, HttpServletRequest request) {
        baseUserInfo.setCreate_person(SessionUtil.getUserSession(request).getRealname());
        int flag = baseUserInfoService.add(baseUserInfo);
        if (flag == 2)
            return "forward:/error.htm?msg=" + StringUtil.encodeUrl("该学员已存在");
        else if (flag == 0)
            return "forward:/error.htm?msg=" + StringUtil.encodeUrl("学员档案新增失败");
        else
            return "forward:/success.htm?msg=" + StringUtil.encodeUrl("学员档案新增成功");
    }

    @RequestMapping("/toUpdate")
    public ModelAndView toUpdate(HttpServletRequest request, int id) {
        ModelAndView mv = new ModelAndView("/base/userinfoUpdate");

        BaseUserInfo baseUserInfo = baseUserInfoService.get(id);
        mv.addObject("baseUserInfo", baseUserInfo);

        List<BaseDic> literacyList = baseDicService.findByType(1);
        List<BaseDic> dutylevelList = baseDicService.findByType(2);
        List<BaseDic> ranksList = baseDicService.findByType(3);
        List<BaseDic> technologylevelList = baseDicService.findByType(4);

        mv.addObject("classList", commonService.listClass());// 班级

        mv.addObject("literacyList", literacyList);
        mv.addObject("dutylevelList", dutylevelList);
        mv.addObject("ranksList", ranksList);
        mv.addObject("technologylevelList", technologylevelList);

        BackUrlUtil.setBackUrl(mv, request);
        return mv;
    }

    @RequestMapping("/update")
    public String update(BaseUserInfo baseUserInfo) {
        int flag = baseUserInfoService.update(baseUserInfo);
        if (flag == 0)
            return "forward:/error.htm?msg=" + StringUtil.encodeUrl("学员档案修改失败");
        else
            return "forward:/success.htm?msg=" + StringUtil.encodeUrl("学员档案修改成功");
    }

    @RequestMapping("/toView")
    public ModelAndView toView(HttpServletRequest request, int id) {
        ModelAndView mv = new ModelAndView("/base/userinfoView");

        BaseUserInfo baseUserInfo = baseUserInfoService.get(id);
        mv.addObject("baseUserInfo", baseUserInfo);

        BackUrlUtil.setBackUrl(mv, request);
        return mv;
    }

    @RequestMapping("/delete")
    public String delete(int id) {
        int flag = baseUserInfoService.delete(id);
        if (flag == 0)
            return "forward:/error.htm?msg=" + StringUtil.encodeUrl("学员档案删除失败");
        else
            return "forward:/success.htm?msg=" + StringUtil.encodeUrl("学员档案删除成功");
    }

    /**
     * 校验编号是否存在
     *
     * @param code     编号
     * @param response 响应
     */
    @RequestMapping("/checkCode")
    public void checkCode(String code, HttpServletResponse response) {
        int flag = baseUserInfoService.existByCertificate_code(code);
        if (flag == 0)
            WebUtil.out(response, "true");
        else
            WebUtil.out(response, "false");
    }

    @RequestMapping("/importUserInfo")
    public void importUserInfo(@RequestParam(value = "excel_file", required = false) MultipartFile excel_file, HttpServletRequest request,
                               HttpServletResponse response) {
        String create_person = SessionUtil.getUserSession(request).getRealname();
        String uploadPath = pubConfig.getImageUploadPath();
        String storePath = "/student/" + DateUtil.getShortSystemDate() + "/";
        String fileName = excel_file.getOriginalFilename();
        String suffix = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());
        // String fileName = new Date().getTime()+".jpg";
        String createFilename = DateUtil.getShortSystemTime() + SerialNumUtil.createRandowmNum(6) + "." + suffix;
        File targetFile = new File(uploadPath + storePath, createFilename);
        if (!targetFile.exists()) {
            targetFile.mkdirs();
        }
        String json;

        if (excel_file.getSize() > 200 * 1024 * 1024) {
            json = "{success:" + false + ",msgText:'" + "文件超过200M" + "'}";
        } else {
            // 保存
            try {
                excel_file.transferTo(targetFile);
                String result = baseUserInfoService.saveImport(uploadPath + storePath + File.separator + createFilename, create_person);
                if (result.equals("")) {
                    json = "{success:" + true + ",msgText:'" + "导入成功" +  "'}";
                } else {
                    json = "{success:" + false + ",msgText:'" + "导入失败" + "',errorInfo:'" + result + "'}";
                }
                FileUtil.delete(uploadPath + storePath + File.separator + createFilename);
            } catch (Exception e) {
                json = "{success:" + false + ",msgText:'" + "上传失败" + e.getMessage() + "'}";
                e.printStackTrace();
            }
        }
        WebUtil.out(response, json);
    }
}
