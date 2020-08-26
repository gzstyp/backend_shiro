package com.yinlz.service;

import com.yinlz.config.ConfigFile;
import com.yinlz.dao.DaoHandle;
import com.yinlz.tool.ToolClient;
import com.yinlz.tool.ToolSHA;
import com.yinlz.tool.ToolString;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;

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
public class UserService{

    @Resource
    private DaoHandle dao;

    public String login(final HttpServletRequest request){
        final HashMap<String,String> params = ToolClient.getFormParams(request);
        final String user_name = "userName";
        final String user_password = "password";
        if(!ToolClient.validateField(params,new String[]{user_name,user_password}))return ToolClient.jsonValidateField();
        final String userName = params.get(user_name);
        final String userPassword = params.get(user_password);
        final String encoder = ToolSHA.encoder(userName,userPassword);
        final Subject subject = SecurityUtils.getSubject();
        final UsernamePasswordToken token = new UsernamePasswordToken(userName,encoder);
        try {
            subject.login(token);
            final String userId = (String)subject.getPrincipals().getPrimaryPrincipal();
            final HttpSession session = request.getSession();
            session.setAttribute(ConfigFile.LOGIN_KEY,userId);
            session.setAttribute(ConfigFile.LOGIN_USER,userName);
            return ToolClient.createJsonSuccess("登录成功");
        } catch (Exception e) {
            return ToolClient.createJsonFail("账号或密码错误");
        }
    }

    @Transactional
    public String add(final HashMap<String,String> params){
        final String user_name = "user_name";
        final String user_password = "user_password";
        if(!ToolClient.validateField(params,new String[]{user_name,user_password}))return ToolClient.jsonValidateField();
        final String userName = params.get(user_name);
        final String userPassword = params.get(user_password);
        final String kid = ToolString.getIdsChar32();
        final String encoder = ToolSHA.encoder(userName,userPassword);
        params.put("kid",kid);
        params.put("user_name",userName);
        params.put("user_password",encoder);
        final int rowUser = dao.execute("sys_user.addUser",params);
        final int rowPassword = dao.execute("sys_user.addUserPassword",params);
        if(rowUser <= 0 || rowPassword <= 0){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return ToolClient.createJsonFail("操作失败");
        }
        return ToolClient.createJsonSuccess("添加成功");
    }
}