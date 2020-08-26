package com.yinlz.controller;

import com.yinlz.service.RoleService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 页面跳转中心
 * @作者 田应平
 * @版本 v1.0
 * @创建时间 2020-02-27 2:52
 * @QQ号码 444141300
 * @Email service@yinlz.com
 * @官网 <url>http://www.yinlz.com</url>
*/
@RestController
@RequestMapping("role")
public class RoleController{

    @Resource
    private HttpServletRequest request;

    @Resource
    private RoleService roleService;
}