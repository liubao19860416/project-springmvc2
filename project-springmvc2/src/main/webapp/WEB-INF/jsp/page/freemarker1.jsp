<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.Liubao.local.tag/" prefix="local"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Freemarker分页标签测试页面</title>
<script src="${pageContext.request.contextPath}/js/jquery-1.7.1.min.js"
	type="text/javascript"></script>
</head>
<body>
	<H1>Freemarker分页标签测试页面！！！</H1>

	<form action="">
		<table border="1px" align="center">
			<tr>
				<td><input type="text" id="id" name="id" value=""
					placeholder="请输入ID"></td>
			</tr>
			<tr>
				<td><input type="text" id="username" name="username" value=""
					placeholder="请输入姓名"></td>
			</tr>
			<tr>
				<td><input type="button" id="checkButton" name="checkButton"
					value="查询"></td>
			</tr>
		</table>
	</form>

	<hr />
	<table border="5px" align="center">
		<tr>
			<th>用户ID</th>
			<th>用户姓名</th>
			<th>用户密码</th>
		</tr>
		<c:forEach items="${page.records}" var="user">
			<tr>
				<td>${user.id}</td>
				<td>${user.username}</td>
				<td>${user.password}</td>
			</tr>
		</c:forEach>
		<tr>
			<hr />
		</tr>
		<tr>
			<table id="tableContent" border="5px" align="center">
			</table>
		</tr>

	</table>
	<hr />
	<h1>分页显示标签2:url跳转路径是自己配置的</h1>
	<hr />

	<local:pageTagFreemarker pageSize="${page.pageSize}" 
		totalCount="${page.totalCount}" pageNum="${page.pageNum}"
		parameterUrl="${page.parameterUrl}"
		pageQueryUrl="page/freemarker1.action"></local:pageTagFreemarker>

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