package com.aim.questionnaire.service;

import com.aim.questionnaire.common.utils.DateUtil;
import com.aim.questionnaire.common.utils.UUIDUtil;
//import com.aim.questionnaire.config.shiro.SysUserService;
//import com.aim.questionnaire.config.shiro.entity.UserOnlineBo;
import com.aim.questionnaire.dao.QuestionnaireEntityMapper;
import com.aim.questionnaire.dao.UserEntityMapper;
import com.aim.questionnaire.dao.entity.UserEntity;
//import com.alibaba.fastjson.JSONArray;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.bind.SchemaOutputResolver;
import javax.xml.ws.Action;
import java.util.*;

/**
 * Created by wln on 2018\8\9 0009.
 */
@Service
public class UserService {

    @Autowired
    private UserEntityMapper userEntityMapper;

    //@Autowired
    //private SysUserService sysUserService;

    @Autowired
    private QuestionnaireEntityMapper questionnaireEntityMapper;

    /**
     * 查询用户列表（模糊搜索）
     * @param map
     * @return
     */
    public PageInfo queryUserList(Map<String,Object> map) {
        if(!map.containsKey("pageSize") || !map.containsKey("pageNum")){
            return null;
        }
//        System.out.println(map);
        Integer pageNum = (Integer) map.get("pageNum");
        Integer pageSize = (Integer) map.get("pageSize");
        final HashMap<String ,Object> userNameMap = new HashMap<>();
        userNameMap.put("username",map.get("userName"));
        System.out.println(userNameMap);
        PageHelper.startPage(pageNum,pageSize);
//        userNameMap.put("username","admin");
        final List<Map<String ,Object>> users = userEntityMapper.queryUserList(userNameMap);
        Long total = null;
        if(users instanceof  Page){
            Page page = (Page)users;
            total = page.getTotal();
        }
        final PageInfo<Map<String ,Object>> usersPageInfo = new PageInfo<>(users);
        usersPageInfo.setTotal(total);
//        usersPageInfo.set
//        System.out.println("查询条目总数量：" + total);
        return usersPageInfo;
    }

//    public Integer getTotal



    /**
     * 创建用户的基本信息
     * @param map
     * @return
     */
    public int addUserInfo(Map<String,Object> map) {
        if(map.get("username") != null) {
            int userResult = userEntityMapper.queryExistUser(map);
            if(userResult != 0) {
                //用户名已经存在
                return 3;
            }
        }

        String id = UUIDUtil.getOneUUID();
        map.put("id",id);
        //创建时间
        Date date = DateUtil.getCreateTime();
        map.put("creationDate",date);
        map.put("lastUpdateDate",date);
        //创建人
        String user = "admin";
        map.put("createdBy",user);
        map.put("lastUpdatedBy",user);
        //前台传入的时间戳转换
        String startTimeStr = map.get("startTime").toString();
        String endTimeStr = map.get("stopTime").toString();
        Date startTime = DateUtil.getMyTime(startTimeStr);
        Date endTime = DateUtil.getMyTime(endTimeStr);
        map.put("startTime",startTime);
        map.put("stopTime",endTime);

        int result = userEntityMapper.addUserInfo(map);
        return result;
    }

    /**
     * 编辑用户的基本信息
     * @param map
     * @return
     */
    public int modifyUserInfo(Map<String, Object> map) {

        return 0;
    }

    /**
     * 修改用户状态
     * @param map
     * @return
     */
    public int modifyUserStatus(Map<String, Object> map) {
        return 0;
    }

    /**
     * 根据id查询用户信息
     * @param userEntity
     * @return
     */
    public Map<String,Object> selectUserInfoById(UserEntity userEntity) {
  
        return null;
    }

    /**
     * 删除用户信息
     * @param userEntity
     * @return
     */
    public int deteleUserInfoById(UserEntity userEntity) {

        return 0;
    }
}
