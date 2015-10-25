<#--分页工具条升级-->
<div align="left">
<#--案例一--->
	<#if userinfo.username == "">
		<span>您当前还没有登录:</span>
		<span>${logininfo}</span>
	<#else>
		<span>当前登录用户为:${userinfo.username}</span>
	</#if>
</div>


<#--案例二: 这里可以包含指定的页面显示
<#include "#">
--->
