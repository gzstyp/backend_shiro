package com.yinlz.service;

import com.yinlz.dao.DaoHandle;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * 角色管理
 * @作者 田应平
 * @版本 v1.0
 * @创建时间 2020-02-27 3:34
 * @QQ号码 444141300
 * @Email service@yinlz.com
 * @官网 <url>http://www.yinlz.com</url>
*/
@Service
public class RoleService{

    @Resource
    private DaoHandle dao;

    public List<HashMap<String,String>> getRolePermission(final String userId){
        return dao.queryForListString("sys_menu.getRolePermission",userId);
    }
}