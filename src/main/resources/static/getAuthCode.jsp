<%@ page import="org.jasig.cas.authentication.mobileauth.entity.MobileAuthenCode" %>
<%@ page import="java.util.Date" %>
<%@ page import="com.yooli.service.MobileAuthonService" %>
<%@ page import="org.jasig.cas.util.ApplicationContextProvider" %><%--
  Created by IntelliJ IDEA.
  User: lizhengnan
  Date: 2017/4/12
  Time: 下午5:10
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>验证码</title>
</head>
<body>
    <%
        String userName = request.getParameter("userName");
        String mobile = request.getParameter("mobile");
        MobileAuthenCode code = new MobileAuthenCode(userName, mobile, new Date(), "111111");
        MobileAuthonService mobileAuthonService = (MobileAuthonService) ApplicationContextProvider.getApplicationContext().getBean("mobileAuthonService");
        mobileAuthonService.insertMobileAuthon(code);
        mobileAuthonService = null;
	request.getSession().setAttribute("rand", "1111");
    %>
	<div>ok</div>
</body>
</html>
