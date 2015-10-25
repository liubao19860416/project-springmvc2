<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.Liubao.local.tag/" prefix="local"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>UserLoginCheck登录校验标签测试页面</title>
<script src="${pageContext.request.contextPath}/js/jquery-1.7.1.min.js"
	type="text/javascript"></script>
</head>
<body>
	<H1>UserLoginCheck登录校验标签测试页面！！！</H1>
	<!-- 校验当前用户是否登录的显示标签 -->
	<local:userLoginCheck/>

	<form action="${pageContext.request.contextPath}/page/userLoginCheck.action" method="post">
		<table border="1px" align="center">
			<tr>
				<td><input type="text" id="username" name="username" value=""
					placeholder="请输入姓名"></td>
			</tr>
			<tr>
				<td><input type="password" id="password" name="password"
					value="" placeholder="请输入密码"></td>
			</tr>
			<tr>
				<td><input type="submit" id="submitButton" name="submitButton"
					value="登录"></td>
			</tr>
		</table>
	</form>

	<hr />
	<hr />


</body>

<script type="text/javascript">
	//初始化加载
	$(function() {

		$("#checkButton").click(function() {
			var paramMap = {};
			initParamMap(paramMap);

			$.ajax({
				type : "POST",
				url : "${pageContext.request.contextPath}/page/page2.action",
				contentType : "application/json; charset=UTF-8",
				processData : true,
				data : JSON.stringify(paramMap),
				dataType : "json",
				cache : true,
				"success" : function(data) {
					initTable(data.records);
					var subjects = [];
					for (var i = 0; i < data.records.length; i++) {
						//subjects.push(data.records[i].username);
					}
					//alert(subjects);
				}
			});

		});
	});
</script>
</html>