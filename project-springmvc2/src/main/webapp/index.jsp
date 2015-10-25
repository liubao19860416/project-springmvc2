<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<html>
<head>
    <meta charset="utf-8" />
    <title>project-springmvc2主页</title>
    <meta name="description" content="" />
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta content="width=device-width, initial-scale=1.0" name="viewport" />
    <meta name="MobileOptimized" content="320">
    
    
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/main.css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/jquery.autocomplete.css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/thickbox.css" />

<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/jquery.autocomplete.css" />

<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.js"></script>
<script type='text/javascript' src='${pageContext.request.contextPath}/js/jquery.bgiframe.min.js'></script>
<script type='text/javascript' src='${pageContext.request.contextPath}/js/jquery.ajaxQueue.js'></script>
<script type='text/javascript' src='${pageContext.request.contextPath}/js/thickbox-compressed.js'></script>
<script type='text/javascript' src='${pageContext.request.contextPath}/js/jquery.autocomplete.js'></script>
<script type='text/javascript' src='${pageContext.request.contextPath}/js/localdata.js'></script> 


<script src="${pageContext.request.contextPath}/js/jquery-1.7.1.min.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/js/mylocal.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/js/jquery.autocomplete.min.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/js/jquery.autocomplete.pack.js" type="text/javascript"></script>
<%-- 
--%>
</head>
<body>
	<H1>默认刘保的访问主页面！！！</H1> <br/>
	<form action="user/userIndex2.action" id="submitForm" name="submitForm" method="post" >
		请输入用户名1：<input type="text" id="username"  autofocus="autofocus" name="username"  placeholder="请输入用户名" value="" >
		请输入用户名2：<input type="text" id="username2" name="username2"  placeholder="请输入用户名2" value="" autocomplete="off">
		 <input type="submit" value="提交" >
	</form>
</body>

<script type="text/javascript">

 $(function(){
	 
		var subjects=[];
		var username=$("#username").val();
		var requestParam={};
		requestParam.username=username;
		$.ajax({
			   type: "POST",
			   url: "user/userIndex3.action",
			   contentType:"application/json; charset=UTF-8",
			   processData : true,
			   data: JSON.stringify(requestParam),
			   dataType:"json",
			   cache: true,
			   "success": function(data){
						 /* for(var i = 0 ; i < data.length;i++){
						 subjects.push(data[i].name);
						 }
			          	$('#username').typeahead({source: subjects}); */
				   		$('#username').typeahead({source: data});
					}
					
			   });
			
			
		$("#username2").autocomplete("user/userIndex3.action", {
			width: 320,
			max: 4,
			highlight: false,
			scroll: true,
			scrollHeight: 300,
			matchContains: true,
			dataType: 'json',
			formatItem: function(data, i, n, value) {
				alert(data+":"+i+":"+n+":"+value);
				//return "<img src='images/" + value + "'/> " + value.split(".")[0];
				return  value ;
			},
			formatResult: function(data, value) {
				alert(data);
				return value.split(".")[0];
			}
		});
	
			
			//OK
			/* var username=$("#username2").val();
			var requestParam={};
			requestParam.username=username;
		    $.ajax({
		        type: 'POST',
		        dataType: 'json',
		        url: 'user/userIndex3.action',
		        contentType:"application/json; charset=UTF-8",
		        data: JSON.stringify(requestParam),
		        cache: true,
		        success: function(data) {
		        	alert(data);
		        	$("#username2").autocomplete(data,
		        		{
						 max: 10, 
						 minChars: 0, 
						 width: 400, 
						 matchContains: true,
						 dataType: 'json',
					});
		        }
		    }); */
	
	
	});

</script>

</html>