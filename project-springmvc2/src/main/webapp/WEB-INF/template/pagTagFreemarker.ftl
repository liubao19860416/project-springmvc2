<#--分页工具条升级-->
<div  align="left">
	<#-- 分页部分文字信息 --> 
	<span>总记录数:${totalCount}条,</span>
	<span>每页记录数:${pageSize}条,</span>
	<span>当前第${pageNum}页,</span>
	<span>共${totalPages}页,</span>
	<#-- 首页 上一页-->
	<#if pageNum==1>
		<#-- 当前页是第一页 -->
		<a href="javascript:void(0)">首页</a>&nbsp;
		<a href="javascript:void(0)">上一页</a>
	<#else>
		<#-- 当前页不是第一页 -->
		<a href="${contextPath}/${pageQueryUrl}?pageNum=1&pageSize=${pageSize}&${parameterUrl}">首页</a>&nbsp;	
		<a href="${contextPath}/${pageQueryUrl}?pageNum=${pageNum-1}&pageSize=${pageSize}&${parameterUrl}">上一页</a>	
	</#if>
	
	
	<#-- 中间页码部分,begin为开始页码,end为结束页码 -->
	<#list pageNumStart..pageNumEnd as pageIndex>
		<#if pageNum==pageIndex>
			<#-- 当前页码  -->
			<a href="javascript:void(0)">[${pageIndex}]</a>
		<#else>
			<#-- 不是当前页码-->
			<a href="${contextPath}/${pageQueryUrl}?pageNum=${pageIndex}&pageSize=${pageSize}&${parameterUrl}">[${pageIndex}]</a>	
		</#if>
	</#list>
	
	
	<#-- 下一页 尾页 -->
	<#if pageNum==totalPages>
		<#--当前页已经是最后一页 -->
		<a href="javascript:void(0)">下一页</a>&nbsp;
		<a href="javascript:void(0)">尾页</a>
	<#else>
		<#-- 不是最后一页 -->
		<a href="${contextPath}/${pageQueryUrl}?pageNum=${pageNum+1}&pageSize=${pageSize}&${parameterUrl}">下一页</a>&nbsp;	
		<a href="${contextPath}/${pageQueryUrl}?pageNum=${totalPages}&pageSize=${pageSize}&${parameterUrl}">尾页</a>
	</#if>	
	
	
	<#-- 跳转页码位置 -->
	<input type="text" size="2" name="selectedPageNum"/>
	<input type="button" value="Go" id="selectedPageNumButton"/>
	<input type="hidden" value="${parameterUrl}" name="parameterUrl" id="parameterUrl" />
	<input type="hidden" value="${totalPages}" id="totalPages" />
	
</div>