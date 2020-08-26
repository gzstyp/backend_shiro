<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%
    final String path = request.getContextPath();
    final String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8"/>
    <title>student</title>
    <base href="<%=basePath%>">
    <link rel="shortcut icon" href="favicon.ico"/>
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
</head>
<body>
student
<shiro:hasPermission name="student:add">
    <td><button type="button" class="btnBlue" onclick="thisPage.edit(2);">权限是student:add</button></td>
</shiro:hasPermission>
<shiro:hasPermission name="admin:delete">
    <td><button type="button" class="btnWarn" onclick="thisPage.delByIds();">删除</button></td>
</shiro:hasPermission>
</body>
</html>