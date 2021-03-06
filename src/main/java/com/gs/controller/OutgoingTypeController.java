package com.gs.controller;

import ch.qos.logback.classic.Logger;
import com.gs.bean.OutgoingType;
import com.gs.bean.User;
import com.gs.common.Constants;
import com.gs.common.bean.ComboBox4EasyUI;
import com.gs.common.bean.ControllerResult;
import com.gs.common.bean.Pager;
import com.gs.common.bean.Pager4EasyUI;
import com.gs.common.util.CheckRoleUtil;
import com.gs.common.util.SessionGetUtil;
import com.gs.service.OutgoingTypeService;
import org.apache.ibatis.annotations.Param;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by xiao-kang on 2017/4/16.
 */
@Controller
@RequestMapping("/outgoingType")
public class OutgoingTypeController {

    private Logger logger = (Logger) LoggerFactory.getLogger(OutgoingTypeController.class);


    @Resource
    private OutgoingTypeService outgoingTypeService;


    /**
     * 可以查看的角色：董事长、财务员、超级管理员、普通管理员
     */
    private String queryRole = Constants.COMPANY_ADMIN + "," + Constants.COMPANY_ACCOUNTING + ","
            + Constants.SYSTEM_ORDINARY_ADMIN + "," + Constants.SYSTEM_SUPER_ADMIN;

    /**
     * 可以修改的角色：董事长、财务员
     */
    private String editRole = Constants.COMPANY_ADMIN + "," + Constants.COMPANY_ACCOUNTING;

    /**
     * 显示支出类型页面
     * @return String
     */
    @RequestMapping(value = "show_outgoingType", method = RequestMethod.GET)
    public String outgoingType() {
        if(SessionGetUtil.isUser()) {
            if(CheckRoleUtil.checkRoles(queryRole)) {
                logger.info("显示支出类型页面");
                return "financeManage/outgoing_type";
            }
            return "error/notPermission";
        }else {
            logger.info("Session已失效，请重新登入");
            return "index/notLogin";
        }
    }

    /**
     * 分页查询所有支出类型
     * @param pageNumber
     * @param pageSize
     * @return Pager4EasyUI<OutgoingType>
     */
    @ResponseBody
    @RequestMapping(value="query_pager",method= RequestMethod.GET)
    public Pager4EasyUI<OutgoingType> queryPager(@Param("pageNumber")String pageNumber, @Param("pageSize")String pageSize) {
        if (SessionGetUtil.isUser()) {
            if(CheckRoleUtil.checkRoles(queryRole)) {
                logger.info("分页查询所有支出类型");
                Pager pager = new Pager();
                User user = SessionGetUtil.getUser();
                pager.setPageNo(Integer.valueOf(pageNumber));
                pager.setPageSize(Integer.valueOf(pageSize));
                pager.setTotalRecords(outgoingTypeService.count(user));
                List<OutgoingType> outgoingTypes = outgoingTypeService.queryByPager(pager,user);
                return new Pager4EasyUI<>(pager.getTotalRecords(), outgoingTypes);
            }
            return null;
        } else {
            logger.info("Session已失效，请重新登入");
            return null;
        }
    }

    /**
     * 根据条件分页查询所有支出类型
     * @param pageNumber
     * @param pageSize
     * @param inTypeName
     * @param companyId
     * @return Pager4EasyUI<OutgoingType>
     */
    @ResponseBody
    @RequestMapping(value="query_condition",method= RequestMethod.GET)
    public Pager4EasyUI<OutgoingType> queryPagerCondition(@Param("pageNumber")String pageNumber, @Param("pageSize")String pageSize
            ,@Param("inTypeName")String inTypeName,@Param("companyId")String companyId){
        if (SessionGetUtil.isUser()) {
            if(CheckRoleUtil.checkRoles(queryRole)) {
                logger.info("根据条件分页查询所有支出类型");
                Pager pager = new Pager();
                User user = SessionGetUtil.getUser();
                if(user.getCompanyId() != null && !"".equals(user.getCompanyId())){
                    companyId = user.getCompanyId();
                }
                pager.setPageNo(Integer.valueOf(pageNumber));
                pager.setPageSize(Integer.valueOf(pageSize));
                pager.setTotalRecords(outgoingTypeService.countCondition(companyId,inTypeName));
                List<OutgoingType> outgoingTypes = outgoingTypeService.queryByPagerCondition(companyId,inTypeName,pager);
                return new Pager4EasyUI<>(pager.getTotalRecords(), outgoingTypes);
            }
            return null;
        } else {
            logger.info("Session已失效，请重新登入");
            return null;
        }
    }

    /**
     * 添加支出类型
     * @param outgoingType
     * @return
     */
    @ResponseBody
    @RequestMapping(value="add_outgoingType", method=RequestMethod.POST)
    public ControllerResult outgoingTypeAdd(OutgoingType outgoingType){
        if(SessionGetUtil.isUser()) {
            if(CheckRoleUtil.checkRoles(editRole)) {
                logger.info("添加支出类型");
                User user = SessionGetUtil.getUser();
                outgoingType.setCompanyId(user.getCompanyId());
                outgoingTypeService.insert(outgoingType);
                return ControllerResult.getSuccessResult("添加支出类型成功");
            }
            return ControllerResult.getFailResult("添加支出类型失败，没有该权限");
        }else{
            logger.info("Session已失效，请重新登入");
            return ControllerResult.getNotLoginResult("登入信息已失效，请重新登入");
        }
    }

    /**
     * 更新支出类型
     * @param outgoingType
     * @return
     */
    @ResponseBody
    @RequestMapping(value="update_outgoingType", method=RequestMethod.POST)
    public ControllerResult outgoingUpdate(OutgoingType outgoingType){
        if(SessionGetUtil.isUser()) {
            if(CheckRoleUtil.checkRoles(editRole)) {
                logger.info("更新支出类型");
                User user = SessionGetUtil.getUser();
                outgoingType.setCompanyId(user.getCompanyId());
                outgoingTypeService.update(outgoingType);
                return ControllerResult.getSuccessResult("更新支出类型成功");
            }
            return ControllerResult.getFailResult("更新支出类型失败，没有该权限");
        } else{
            logger.info("Session已失效，请重新登入");
            return ControllerResult.getNotLoginResult("登入信息已失效，请重新登入");
        }
    }

    /**
     * 更新支出类型状态
     * @param id
     * @param status
     * @return
     */
    @ResponseBody
    @RequestMapping(value="update_status", method=RequestMethod.GET)
    public ControllerResult updateStatus(@Param("id") String id, @Param("status")String status){
        if(SessionGetUtil.isUser()) {
            if(CheckRoleUtil.checkRoles(editRole)) {
                User user = SessionGetUtil.getUser();
                user.setCompanyId(user.getCompanyId());
                logger.info("更新支出类型状态");
                if ("Y".equals(status)) {
                    outgoingTypeService.active(id);
                } else if ("N".equals(status)) {
                    outgoingTypeService.inactive(id);
                }
                return ControllerResult.getSuccessResult("更新支出类型状态成功");
            }
            return ControllerResult.getFailResult("更新支出类型状态失败，没有该权限");
        } else{
            logger.info("Session已失效，请重新登入");
            return ControllerResult.getNotLoginResult("登入信息已失效，请重新登入");
        }
    }

    /**
     *根据支出类型状态查询
     * @param pageNumber
     * @param pageSize
     * @param status
     * @return
     */
    @ResponseBody
    @RequestMapping(value="query_status", method=RequestMethod.GET)
    public Pager4EasyUI<OutgoingType> queryPager(@Param("pageNumber")String pageNumber, @Param("pageSize")String pageSize,@Param("status")String status){
        if(SessionGetUtil.isUser()) {
            if(CheckRoleUtil.checkRoles(queryRole)) {
                logger.info("根据支出类型状态查询");
                Pager pager = new Pager();
                User user = SessionGetUtil.getUser();
                pager.setPageNo(Integer.valueOf(pageNumber));
                pager.setPageSize(Integer.valueOf(pageSize));
                List<OutgoingType> outgoingTypes = null;
                if ("Y".equals(status)) {
                    outgoingTypes = outgoingTypeService.queryPagerStatus(status, pager,user);
                    pager.setTotalRecords(outgoingTypeService.countStatus(status,user));
                } else if ("N".equals(status)) {
                    outgoingTypes = outgoingTypeService.queryPagerStatus(status, pager,user);
                    pager.setTotalRecords(outgoingTypeService.countStatus(status,user));
                } else if ("ALL".equals(status)) {
                    pager.setTotalRecords(outgoingTypeService.count(user));
                    outgoingTypes = outgoingTypeService.queryByPager(pager,user);
                }
                return new Pager4EasyUI<>(pager.getTotalRecords(), outgoingTypes);
            }
            return  null;
        } else{
            logger.info("Session已失效，请重新登入");
            return null;
        }
    }

    /**
     * 查询所有支出类型
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "outType_all", method = RequestMethod.GET)
    public List<ComboBox4EasyUI> queryUserAll() {
        try {
            logger.info("查询所有支出类型");
            User user = SessionGetUtil.getUser();
            List<OutgoingType> outgoingTypes = outgoingTypeService.queryAll(user);
            List<ComboBox4EasyUI> comboBox4EasyUIs = new ArrayList<>();
            for (OutgoingType out : outgoingTypes) {
                ComboBox4EasyUI comboBox4EasyUI = new ComboBox4EasyUI();
                comboBox4EasyUI.setId(out.getOutTypeId());
                comboBox4EasyUI.setText(out.getOutTypeName());
                comboBox4EasyUIs.add(comboBox4EasyUI);
            }
            return comboBox4EasyUIs;
        } catch (Exception e){
            return null;
        }
    }
}
