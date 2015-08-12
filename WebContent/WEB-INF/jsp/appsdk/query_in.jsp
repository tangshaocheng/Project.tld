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
		ajaxPost('${pageContext.request.contextPath}/appsdk/query_in.htm',
				backQuery);
	}
	function backQuery(data) {
		var html = '';
		$('#resultData').html('');
		var result = data[0]['resultList'];
		for ( var i = 0; i < result.length; i++) {
			i % 2 == 0 ? html += "<tr class='even'>"
					: html += "<tr class='odd2'>";
			var operation = "[<a href='javascript:up_(" + result[i]['id']
					+ ")'>修改</a>]&nbsp;&nbsp;&nbsp;&nbsp;";
			operation += "[<a href='javascript:del_(" + result[i]['id']
					+ ")'>删除</a>]";
			var bagType = '';
			if (result[i]['OnOrOff'] == '1'){
				bagType = '开启';
			}
			var country='';
			if (result[i]['OnOrOff'] =='0'){
				bagType = '关闭';
			}
			if (result[i]['country'] == '1'){
				country = '中国';
			}
			if (result[i]['country'] =='0'){
				country = '外国';
			}
			html += '<td>' + result[i]['id'] + '</td>';
			html += '<td>' + result[i]['sdk_name'] + '</td>';
			html += '<td>' + country + '</td>';
			html += '<td>' + bagType + '</td>';
			html += '<td>' + result[i]['activeTime'] + '</td>';
			html += '<td>'
					+ formateDate(result[i]['createTime']['time'],
							'YYYY-MM-DD HH:mm:ss') + '</td>';
			html += '<td>' + operation + '</td>';
			html += '</tr>';
		}
		$('#resultData').html(html);
		ajaxPage(data);
	}
	function add_() {
		showDivDetail('${pageContext.request.contextPath}/appsdk/toAdd_in.htm',
				750, 600, 10, 10);
	}
	function up_() {
		showDivDetail(
				'${pageContext.request.contextPath}/appsdk/toUp_in.htm?id='
						+ arguments[0], 750, 500, 10, 10);
	}
	function del_() {
		if (confirm('确定删除此SDK吗？'))
			ajaxPost('${pageContext.request.contextPath}/appsdk/del_in.htm',
					backDel_, {
						'id' : arguments[0]
					});
	}
	function backDel_(data) {
		if (data[0]['info'] == 'ok') {
			ajaxQuery();
		}
	}
	function sort_() {
		var arrs = new Array();
		$("input[name='sort']").each(function(i, j) {
			if ($(this).val() != '')
				arrs[i] = $(this).val();
		});
		var flag = true;
		for ( var i = 0; i < arrs.length; i++) {
			for ( var j = i; j < arrs.length - 1; j++) {
				if (arrs[i] == arrs[j + 1]) {
					flag = false;
					break;
				}
			}
		}
		if (!flag) {
			alert('序列号不能重复');
			return;
		}
		ajaxPost('${pageContext.request.contextPath}/appsdk/sort_in.htm',
				backSort_);
	}
	function backSort_(data) {
		if (data[0]['info'] == 'ok') {
			ajaxQuery();
		}
	}
</script>
</head>
<body>
	<div class="main">
		<div class="nav">SDK管理</div>
		<div class="selectthings">
			<table>
				<tr>
					<td width="100%">SDK名称：<input type="text" name="name"
						style="width:100px">&nbsp;&nbsp; SDK类型：<select
						name="bagType" style="width:100px"><option value="">全部</option>
							<option value="1">开启</option>
							<option value="0">关闭</option>
					</select>&nbsp;&nbsp; <input type="button" value="查询" onclick="queryData()"
						class="btn">&nbsp;&nbsp; <input type="button" value="添加"
						onclick="add_()" class="btn">
					</td>
				</tr>
			</table>
		</div>
		<div class="qdata">
			<table width="100%" class="datatable">
				<tbody>
					<tr>
						<th width="5%">SDKID</th>
						<th width="8%">SDK名称</th>
						<th width="5%">国家</th>
						<th width="5%">开关</th>
						<th width="5%">激活时间</th>
						<th width="8%">创建时间</th>
						<th width="5%">操作</th>
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