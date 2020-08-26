package com.yinlz.controller;

import com.yinlz.config.ConfigFile;
import com.yinlz.service.UserService;
import com.yinlz.tool.ToolClient;
import org.apache.shiro.SecurityUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Enumeration;

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
@RequestMapping("user")//在有注解@RequiresPermissions时不要写final
public final class UserController{

    @Resource
    private HttpServletRequest request;

    @Resource
    private UserService userService;

    @PostMapping(value = "add",name = "user:add")
    public final void add(final HttpServletResponse response){
        ToolClient.responseJson(userService.add(ToolClient.getFormParams(request)),response);
    }

    // http://127.0.0.1:2001/user/login?userName=admin&password=1
    @GetMapping(value = "login",name = "user:login")
    public final void login(final HttpServletResponse response){
        ToolClient.responseJson(userService.login(request),response);
    }

    @GetMapping(value = "logout",name = "user:logout")
    public final String logout(final HttpServletRequest request){
        final String redirect_url = "redirect:/login";//重定向,否则会提示找不到页面,重定向会再次请求服务器一次
        SecurityUtils.getSubject().logout();//退出
        final HttpSession session = request.getSession(false);
        if(session == null)
            return redirect_url;
        final Object login_key = session.getAttribute(ConfigFile.LOGIN_KEY);
        if(login_key == null)
            return redirect_url;
        final Enumeration<String> e = session.getAttributeNames();
        while(e.hasMoreElements()){
            final String key = e.nextElement();
            session.removeAttribute(key);
        }
        return redirect_url;
    }

    // http://127.0.0.1:2001/user/auth401
    // http://127.0.0.1:2001/user/401
    @GetMapping(value = {"auth401"},name = "user:auth401")
    public final void auth401(final HttpServletResponse response){
        ToolClient.responseJson(ToolClient.notAuthorized(),response);
    }

    @GetMapping(value = {"logout"},name = "user:logout")
    public final void logout(final HttpServletResponse response){

    }
}