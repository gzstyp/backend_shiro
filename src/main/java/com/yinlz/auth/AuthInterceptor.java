package com.yinlz.auth;

import com.yinlz.config.ConfigFile;
import com.yinlz.service.RoleService;
import com.yinlz.tool.ToolClient;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * 菜单|权限拦截器[认证和权限拦截应该分开,各执其职]
 * @作者 田应平
 * @版本 v1.0
 * @创建时间 2020-02-23 18:22
 * @QQ号码 444141300
 * @Email service@yinlz.com
 * @官网 <url>http://www.yinlz.com</url>
*/
public final class AuthInterceptor implements HandlerInterceptor{

    @Override
    public boolean preHandle(final HttpServletRequest request,final HttpServletResponse response,final Object handler) throws Exception{

        if(handler instanceof ResourceHttpRequestHandler){return true;}
        if(handler instanceof HandlerMethod){
            final String userId = (String)request.getSession().getAttribute(ConfigFile.LOGIN_KEY);
            final RequestMapping annotation = ((HandlerMethod)handler).getMethodAnnotation(RequestMapping.class);
            final String name = annotation.name();
            final BeanFactory factory = WebApplicationContextUtils.getRequiredWebApplicationContext(request.getServletContext());
            final RoleService roleService = factory.getBean(RoleService.class);
            final List<HashMap<String,String>> list = roleService.getRolePermission(userId);
            final Iterator<HashMap<String,String>> iterator = list.iterator();
            final Set<String> setPermission = new HashSet<>();
            final Set<String> setUrl = new HashSet<>();
            while(iterator.hasNext()){
                final HashMap<String,String> map = iterator.next();
                setPermission.add(map.get("permission"));
                setUrl.add(map.get("url"));
            }
            if(name != null && name.length() > 0){
                if(setPermission.contains(name)){
                    return true;
                }
            }
            final String uri = request.getRequestURI().substring(1);
            for(final String key : setUrl){
                if(key.equals(uri))return true;
            }
            response(response);
            return false;
        }else{
            response(response);
            return false;
        }
    }

    private final void response(final HttpServletResponse response){
        ToolClient.responseJson(ToolClient.notAuthorized(),response);
    }
}