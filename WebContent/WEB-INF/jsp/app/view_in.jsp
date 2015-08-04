<html>
<head>
<%@ include file="/WEB-INF/jsp/header.jsp"%>
<%@ page contentType="text/html; charset=utf-8"%>
<title></title>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
<link href="${pageContext.request.contextPath}/css/layout.css" rel="stylesheet" type="text/css">
<script type="text/javascript">
</script>
</head>
<body>
<div class="main">
	<div class="nav">预览应用</div>
	<div class="selectthings">
		<table>
			<tr class="odd2">
				<td width="25%">应用ID：</td>
				<td width="75%">${vo.id}</td>
			</tr>
			<tr class="odd2">
				<td width="25%">名称：</td>
				<td width="75%">${vo.name}</td>
			</tr>
			<tr class="even">
				<td width="25%">作者：</td>
				<td width="75%">${vo.develope}</td>
			</tr>
			<tr class="odd2">
				<td width="25%">热度：</td>
				<td width="75%">${vo.heat}</td>
			</tr>
			<tr class="even">
				<td width="25%">版本号：</td>
				<td width="75%">${vo.version}</td>
			</tr>
			<tr class="even">
				<td width="25%">包名：</td>
				<td width="75%">${vo.pakeage}</td>
			</tr>
			<tr class="even">
				<td width="25%">主类名：</td>
				<td width="75%">${vo.mainClass}</td>
			</tr>
			<tr class="even">
				<td width="25%">内置版本号：</td>
				<td width="75%">${vo.embededVersion}</td>
			</tr>
			<tr class="even">
				<td width="25%">内置包名：</td>
				<td width="75%">${vo.embededPakeage}</td>
			</tr>
			<tr class="even">
				<td width="25%">内置主类名：</td>
				<td width="75%">${vo.embededMainClass}</td>
			</tr>
			<tr class="even">
				<td width="25%">应用分享内容：</td>
				<td width="75%">${vo.shareContent}</td>
			</tr>
			<tr class="odd2">
				<td valign="top">所属分类<font color="red">*</font>：</td>
				<td>
					<select id="categoryId" name="categoryId" style="width: 120px">
						<c:forEach items="${categoryList}" var="category">
				     		<c:if test="${vo.categoryId eq category.id}">
				     			<option value="${category.id}">${category.name}</option>
				     		</c:if>
				     	</c:forEach>
					</select>
				</td>
			</tr>
			<tr class="even">
				<td width="25%">应用描述：</td>
				<td width="75%">
					<textarea id="appDesc" name="appDesc" rows="12" cols="96" style='padding: 3px; font-family: "Courier New", Courier, monospace;'>${vo.appDesc}</textarea>
				</td>
			</tr>
			<tr class="even">
		     	<td width="25%">硬件支持<font color="red"></font>：</td>
		     	<td width="75%"><input type="text" name="support" id="support" maxlength="100" value="${vo.support}" style="width:400px"></td>
		    </tr>
		    <tr class="even">
		     	<td width="25%">年龄限制<font color="red"></font>：</td>
		     	<td width="75%"><input type="text" name="ageLimit" id="ageLimit" maxlength="100" value="${vo.ageLimit}" style="width:400px"></td>
		    </tr>
			<tr class="even">
				<td width="25%">版本更新介绍：</td>
				<td width="75%">
					<textarea id="upDesc" name="upDesc" rows="12" cols="96" style='padding: 3px; font-family: "Courier New", Courier, monospace;'>${vo.upDesc}</textarea>
				</td>
			</tr>
			<tr class="even">
				<td width="25%">apk程序：</td>
				<td width="75%">
					<a href="${pageContext.request.contextPath}/downloadApp.htm?appUrl=${vo.app}">下载</a>
				</td>
			</tr>
			<c:if test="${appBatchListSize == 0}">
		    	<tr class="even" title="batchId_0">
					<td width="25%">批次号：</td>
					<td width="75%"></td>
				</tr>
		    </c:if>
		    <c:if test="${appBatchListSize > 0}">
		    	<tr class="even" title="batchId_0">
					<td width="25%">批次号：</td>
					<td width="75%">
		    			<c:forEach items="${appBatchList}" var="appBatch" varStatus="status">
							<div style="padding: 10px">${appBatch.batchId}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="${pageContext.request.contextPath}/downloadApp.htm?appUrl=${appBatch.app}">下载</a></div>
		    			</c:forEach>
					</td>
				</tr>
		    </c:if>
			<tr class="even">
				<td width="25%">内置apk程序：</td>
				<td width="75%">
					<a href="${pageContext.request.contextPath}/downloadApp.htm?appUrl=${vo.embededApp}">下载</a>
				</td>
			</tr>
	        <tr class="even">
				<td width="25%">主图片1：</td>
				<td width="75%">
					<img src="${pageContext.request.contextPath}/downloadPic.htm?picUrl=${vo.mainPic}" width="105" height="45" border="0" />
				</td>
			</tr>
			<tr class="even">
				<td width="25%">主图片2：</td>
				<td width="75%">
					<img src="${pageContext.request.contextPath}/downloadPic.htm?picUrl=${vo.mainPic2}" width="105" height="45" border="0" />
				</td>
			</tr>
			<tr class="even">
				<td width="25%">图标：</td>
				<td width="75%">
					<img src="${pageContext.request.contextPath}/downloadPic.htm?picUrl=${vo.icon}" width="105" height="45" border="0" />
				</td>
			</tr>
			<tr class="even">
				<td width="25%">截图1：</td>
				<td width="75%">
					<img src="${pageContext.request.contextPath}/downloadPic.htm?picUrl=${vo.pic1}" width="105" height="45" border="0" />
				</td>
			</tr>
			<tr class="even">
				<td width="25%">截图2：</td>
				<td width="75%">
					<img src="${pageContext.request.contextPath}/downloadPic.htm?picUrl=${vo.pic2}" width="105" height="45" border="0" />
				</td>
			</tr>
			<tr class="even">
				<td width="25%">截图3：</td>
				<td width="75%">
					<img src="${pageContext.request.contextPath}/downloadPic.htm?picUrl=${vo.pic3}" width="105" height="45" border="0" />
				</td>
			</tr>
			<tr class="even">
				<td width="25%">截图4：</td>
				<td width="75%">
					<img src="${pageContext.request.contextPath}/downloadPic.htm?picUrl=${vo.pic4}" width="105" height="45" border="0" />
				</td>
			</tr>
			<tr class="even">
				<td width="25%">截图5：</td>
				<td width="75%">
					<img src="${pageContext.request.contextPath}/downloadPic.htm?picUrl=${vo.pic5}" width="105" height="45" border="0" />
				</td>
			</tr>
			<tr class="even">
				<td colspan="2" align="center"><input type="button" value="关     闭" onclick="parent.closeMyPop();" class="btn" /></td>
			</tr>
		</table>
	</div>
</div>
</body>