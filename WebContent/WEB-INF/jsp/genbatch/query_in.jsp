<html>
<head>
<%@ include file="/WEB-INF/jsp/header.jsp"%>
<%@ page contentType="text/html; charset=utf-8"%>
<title></title>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
<link href="${pageContext.request.contextPath}/css/layout.css"
	rel="stylesheet" type="text/css">
<script type="text/javascript">
	function ajaxQuery() {
		ajaxPost('${pageContext.request.contextPath}/genbatch/query_in.htm',
				backQuery);
	}
	function backQuery(data) {
		var html = '';
		$('#resultData').html('');
		var result = data[0]['resultList'];
		for ( var i = 0; i < result.length; i++) {
			i % 2 == 0 ? html += "<tr class='even'>"
					: html += "<tr class='odd2'>";
			html += '<td>' + result[i]['name'] + '</td>';
			html += '<td>' + result[i]['batch_id'] + '</td>';
			html += '<td>' + result[i]['remark'] + '</td>';
			html += '</tr>';
		}
		$('#resultData').html(html);
		ajaxPage(data);
	}
	function add_() {
		showDivDetail(
				'${pageContext.request.contextPath}/genbatch/toAdd_in.htm',
				750, 600, 10, 10);
	}
</script>
</head>
<body>
	<div class="main">
		<div class="nav">生成批次号</div>
		<div class="selectthings">
			<table>
				<tr>
					<td width="100%">合作商：<input type="text" name="name"
						style="width:200px">&nbsp;&nbsp; 批次号：<input type="text"
						name="batchId" style="width:200px">&nbsp;&nbsp; 备注：<input
						type="text" name="remark" style="width:200px">&nbsp;&nbsp;
						<input type="button" value="查询" onclick="queryData()" class="btn">&nbsp;&nbsp;
						<input type="button" value="添加" onclick="add_()" class="btn">
					</td>
				</tr>
			</table>
		</div>
		<div class="qdata">
			<table width="100%" class="datatable">
				<tbody>
					<tr>
						<th width="20%">合作商</th>
						<th width="40%">批次号</th>
						<th width="40%">备注</th>
					</tr>
				</tbody>
				<tbody id="resultData">
				</tbody>
			</table>
			<script type="text/javascript"
				src="${pageContext.request.contextPath}/js/ajaxPage.js"></script>
		</div>
	</div>
</body>
</html>