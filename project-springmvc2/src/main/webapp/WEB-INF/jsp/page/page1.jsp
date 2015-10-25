<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.Liubao.local.tag/" prefix="local"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>page1分页标签测试页面</title>
<script src="${pageContext.request.contextPath}/js/jquery-1.7.1.min.js"
	type="text/javascript"></script>
</head>
<body>
	<H1>page1分页标签测试页面！！！</H1>

	<form id="myform" action="">
		<table border="1px" align="center">
			<tr>
				<td>
				<input type="text" id="id" name="id" value=""  placeholder="请输入ID" >
				</td>
			</tr>
			<tr>
				<td>
				<input type="text" id="username" name="username" value=""  placeholder="请输入姓名" >
				</td>
			</tr>
			<tr>
				<td>
				<input type="button" id="checkButton" name="checkButton"  value="查询" >
				</td>
			</tr>
		</table>
	</form>

	<hr />
	<table border="5px" align="center">
		<tr >
			<th>用户ID</th>
			<th>用户姓名</th>
			<th>用户密码</th>
		</tr>
		<!--  begin="${page.pageIndex}" end="${page.pageIndex+page.pageSize-1}" -->
		<c:forEach items="${page.records}" var="user">
			<tr>
				<td>${user.id}</td>
				<td>${user.username}</td>
				<td>${user.password}</td>
			</tr>
		</c:forEach>
		<tr>
			<hr/>
		</tr>
		<tr>
			<table id="tableContent" border="5px" align="center">
			</table>
		</tr>
		
	</table>
	<h1>分页显示标签1:url跳转路径是自己配置的</h1>

	<%-- <local:pageTag totalCount="${page.totalCount}"
		pageSize="${page.pageSize}" parameterUrl="${page.parameterUrl}"
		pageNum="${page.pageNum}" pageQueryUrl="/page/page1.action"></local:pageTag>
		 --%>
	<local:pageTag totalCount="${page.totalCount}"
		pageSize="${page.pageSize}" parameterUrl="${page.parameterUrl}"
		pageNum="${page.pageNum}" pageQueryUrl="/page/page1.action"></local:pageTag>
		

	<hr />

</body>

<script type="text/javascript">
	
	function initTable(obj){
		var htmlContent="";
		for(var i = 0 ; i < obj.length;i++){
			htmlContent+="<tr><td>"+obj[i].id+"</td>";
			htmlContent+="<td>"+obj[i].username+"</td>";
			htmlContent+="<td>"+obj[i].password+"</td></tr>";
		 }
		$("#tableContent").html(htmlContent);
		
	}
	
	
	function initParamMap(obj){
		if(obj ==null){
			obj={};
		}
		var id=$("#id").val();
		var username=$("#username").val();
		var pageIndex=$("#pageIndex").val();
		var pageSize=$("#pageSize").val();
		obj.id=id;
		obj.username=username;
		obj.username=pageIndex;
		obj.username=pageSize;
	}
	
	//初始化加载
	$(function() {
		
		function serializeMyform(){
			return $("#myform").serialize();
		}
		
		$("#checkButton").click(function(){
		var paramMap={};
		initParamMap(paramMap);
		
		alert($("#myform").serialize());
		
		$.ajax({
			   type: "POST",
			   url: "${pageContext.request.contextPath}/page/page2.action",
			   contentType:"application/json; charset=UTF-8",
			   processData : true,
			   data: JSON.stringify(paramMap),
			   dataType:"json",
			   cache: true,
			   "success": function(data){
						 initTable(data.records);
				   		 var subjects=[];
						 for(var i = 0 ; i < data.records.length;i++){
						 	//subjects.push(data.records[i].username);
						 }
						 //alert(subjects);
					}
			   });
			
		});
	});
</script>
</html>