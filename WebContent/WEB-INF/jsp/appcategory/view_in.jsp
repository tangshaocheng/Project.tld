<html>
<head>
<%@ include file="/WEB-INF/jsp/header.jsp"%>
<%@ page contentType="text/html; charset=utf-8"%>
<title></title>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
<link href="${pageContext.request.contextPath}/css/layout.css" rel="stylesheet" type="text/css">
</head>
<body>
	<div class="main">
		<div class="nav">预览分类</div>
		<div class="selectthings">
			<table>
				<tr class="even">
					<td width="25%">名称：</td>
					<td width="75%">${vo.name}</td>
				</tr>
				<tr class="even">
					<td width="25%">描述：</td>
					<td width="75%">${vo.remark}</td>
				</tr>
				<tr class="even">
					<td width="25%">序号：</td>
					<td width="75%">${vo.seq}</td>
				</tr>
				<tr class="even">
					<td width="25%">图标：</td>
					<td width="75%">
						<img name="pic-preview" id="pic-preview" src="${pageContext.request.contextPath}/downloadPic.htm?picUrl=${vo.icon}" width="105" height="45" border="0" />
					</td>
				</tr>
				<tr class="even">
					<td colspan="2" align="center">
						<input type="button" value="关     闭" onclick="parent.closeMyPop();" class="btn"/>
					</td>
				</tr>
			</table>
		</div>
	</div>
</body>