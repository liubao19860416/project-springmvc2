<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.Liubao.local.tag/" prefix="local"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>welcome登录后欢迎主页</title>
<script src="${pageContext.request.contextPath}/js/jquery-1.7.1.min.js"
	type="text/javascript"></script>
</head>
<body>
	<H1>welcome登录后欢迎主页！！！</H1>
	<!-- 校验当前用户是否登录的显示标签 -->
	<local:userLoginCheck/>

	<hr />

</body>

</html>