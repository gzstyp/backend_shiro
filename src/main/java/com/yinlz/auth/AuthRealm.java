package com.yinlz.auth;

import com.yinlz.dao.DaoHandle;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * 自定义shiro框架Realm对象,此处的类不需要任何注解
 * @作者 田应平
 * @版本 v1.0
 * @创建时间 2020-02-15 23:15
 * @QQ号码 444141300
 * @Email service@yinlz.com
 * @官网 <url>http://www.yinlz.com</url>
*/
public class AuthRealm extends AuthorizingRealm{

    @Resource
    private DaoHandle dao;

    /**
     * 认证登录
     * @param
     * @作者 田应平
     * @QQ 444141300
     * @创建时间 2020/3/1 4:05
    */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(final AuthenticationToken authenticationToken) throws AuthenticationException{
        final UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        final String username = token.getUsername();
        final String password = new String(token.getPassword());
        final HashMap<String,String> params = new HashMap<>(2);
        params.put("username",username);
        params.put("password",password);
        final String userId = dao.queryForEntity("sys_user.userLoginShiro",params);
        if(userId == null){
            throw new AuthenticationException("用户名或密码错误");
        }else{
            return new SimpleAuthenticationInfo(userId,password,"authRealm");
        }
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(final PrincipalCollection principal){
        final String userId = (String)principal.getPrimaryPrincipal();
        final List<String> primarys = new ArrayList<>();
        final List<HashMap<String,String>> list = dao.queryForListString("sys_menu.getRolePermission",userId);
        final Iterator<HashMap<String,String>> iterator = list.iterator();
        final HashMap<String,String> hashMap = new HashMap<>();
        while(iterator.hasNext()){
            final HashMap<String,String> map = iterator.next();
            primarys.add(map.get("permission"));
            final String role_flag = map.get("role_flag");
            hashMap.put(role_flag,role_flag);
        }
        final List<String> roles = new ArrayList<>();
        for(final String key : hashMap.keySet()){
            roles.add(hashMap.get(key));//看看是否去重
        }
        final SimpleAuthorizationInfo infos = new SimpleAuthorizationInfo();
        infos.addStringPermissions(primarys);
        infos.addRoles(roles);
        return infos;
    }
}