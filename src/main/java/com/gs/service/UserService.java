package com.gs.service;

import com.gs.bean.Company;
import com.gs.bean.Role;
import com.gs.bean.User;
import com.gs.common.bean.Pager;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
*
*
*@author qm
*@since 2017-04-14 16:36:52
*/
public interface UserService extends BaseService<String, User>{

     List<User> queryByUser(Pager pager, String companyId);

     int countByUser(String companyId);

    /*查询所有管理员 */
     List<User> queryByAdminPager(Pager pager);

    /*统计所有管理员的个数*/
     int  countAdmin();

    /*查询汽修公司管理员 */
     List<User> queryByCompanyAdminPager(Pager pager);

    /*统计汽修公司管理员的个数*/
     int  countCompanyAdmin();

    /*查询系统管理员 */
     List<User> queryBySystemAdminPager(Pager pager);

    /*统计系统管理员的个数*/
     int  countSystemAdmin();

    /*分页查询所有车主个数*/
     int countByCustomer();

    /*分页查询所有车主*/
     List<User> queryCustomerPager(Pager pager);

    /*分页查询所有员工*/
     List<User> queryPeoplePager(Pager pager, User user);

    /*登陆*/
     User queryLogin(User user);

    /**添加管理员*/
     void insertAdmin(User user);

    /*根据手机号查询id*/
     User queryByPhone(String phone);

    /**更新最后一次登陆的时间*/
     void updateLoginTime(String userId);

    /**统计当前登陆者公司的所有员工*/
     int countCompanyEmp(User user);

    /**查询可用员工个数*/
     int countPeopleEmp(User user);

    /*验证手机号*/
     int queryPhone(String userPhone);

    /**验证邮箱*/
     int queryEmail(String userEmail);

    /*验证身份证*/
     int queryIdentity(String userIdentity);

    /**修改管理员*/
     void updateAdmin(User user);

    /**条件查询管理员*/
     List<User> selectQuery(Pager pager, String userName, String userPhone, String userEmail);

    /**统计条件查询管理员的个数*/
     int countSelectAdmin(String userName, String userPhone, String userEmail);

    /*查询自己公司的员工*/
     List<User> queryUser(String companyId);

    /*分页查询所有不可用车主个数*/
     int countStatus();

    /*分页查询所有不可用车主*/
     List<User> queryCustomerPagerStatus(Pager pager);

    /*分页查询所有车主个数*/
     int countCustomer();

    /*分页查询所有车主*/
     List<User> queryCustomer(Pager pager);

    /*条件查询车主个数*/
     int selectCountCustomer(User user);

    /*条件查询车主*/
     List<User> selectCustomer(Pager pager, User user);

    /*分页查询所有不可用员工个数*/
     int countStatusEmp(User user);

    /*分页查询所有不可用员工*/
     List<User> queryPeoplePagerStatus(Pager pager, User user);

    /*分页查询所有员工个数*/
     int countAllEmp(User user);

    /*分页查询所有员工*/
     List<User> queryPeoplePagerAll(Pager pager, User user);

    /*条件查询员工个数*/
     int countSelectQueryEmp(User user, Role role, Company company);

    /*条件查询员工*/
     List<User> selectQueryEmp(Pager pager, User user, Role role, Company company);

    /*查询自己公司的技师*/
     List<User> queryByCompanyRole(User user);

     int updatePwd(User user);

     int updateMessage(User user);

    /**
     * 查询平台管理员的信息
     * @return
     */
     User queryAdmin();

    /*
    *  根据手机号或邮箱修改密码
    * */
     int updatePwdPhone( User user);

    /**
     * 根据手机号查询用户信息
     * @param phone
     * @return
     */
     User queryUserByPhone(String phone);
}