package com.yinlz.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

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
@Controller
public final class PageController{

    @Resource
    private HttpServletRequest request;

    @GetMapping(value = "login",name = "login")
    public final String login(){
        return "login";
    }

    @GetMapping(value = {"/","index"},name = "index")
    public final String index(){
        return "index";
    }

    @GetMapping(value = "seccess",name = "seccess")
    public final String seccess(){
        return "seccess";
    }

    @GetMapping(value = "student",name = "student")
    public final String student(){
        return "student";
    }
}