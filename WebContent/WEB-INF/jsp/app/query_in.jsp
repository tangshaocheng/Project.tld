<html>
<head>
<%@ include file="/WEB-INF/jsp/header.jsp"%>
<%@ page contentType="text/html; charset=utf-8"%>
<title></title>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
<link href="${pageContext.request.contextPath}/css/layout.css"
	rel="stylesheet" type="text/css">
<script type="text/javascript">
	var currentPageNum = 0;
	function ajaxQuery() {
		ajaxPost('${pageContext.request.contextPath}/app/query_in.htm',
				backQuery);
	}
	function backQuery(data) {
		var html = '';
		$('#resultData').html('');
		var result = data[0]['resultList'];
		currentPageNum = data[0]['currentPageNum'];
		for ( var i = 0; i < result.length; i++) {
			var single = result[i];
			var operation = '';
			operation += "[<a href='javascript:view_(" + single['id']
					+ ")'>预览</a>]&nbsp;&nbsp;";
			operation += "[<a href='javascript:up_(" + single['id']
					+ ")'>修改</a>]&nbsp;&nbsp;";
			if (single['id'] != 128)
				operation += "[<a href='javascript:del_(" + single['id']
						+ ")'>删除</a>]";
			i % 2 == 0 ? html += "<tr class='even'>"
					: html += "<tr class='odd2'>";
			html += '<td>' + single['id'] + '</td>';
			html += '<td>' + single['name'] + '</td>';
			html += '<td>' + single['version'] + '</td>';
			html += '<td>' + single['develope'] + '</td>';
			html += "<td>"
					+ $(
							"select[name='appSource'] option[value='"
									+ single['app_source'] + "']").text()
					+ "</td>";
			html += '<td>' + operation + '</td>';
			html += '</tr>';
		}
		$('#resultData').html(html);
		ajaxPage(data);
	}
	function view_() {
		showDivDetail('${pageContext.request.contextPath}/app/view_in.htm?id='
				+ arguments[0], 750, 500, 10, 10);
	}
	function add_() {
		showDivDetail('${pageContext.request.contextPath}/app/toAdd_in.htm',
				750, 700, 10, 10);
	}
	function up_() {
		showDivDetail('${pageContext.request.contextPath}/app/toUp_in.htm?id='
				+ arguments[0] + "&currentPageNum=" + currentPageNum, 750, 700,
				10, 10);
	}
	function upPush_() {
		showDivDetail(
				'${pageContext.request.contextPath}/app/toUpPush_in.htm?id='
						+ arguments[0], 750, 500, 10, 10);
	}
	function delPushNotifyHistory_() {
		if (confirm('确定重新发布此应用的PUSH通知吗？'))
			ajaxPost(
					'${pageContext.request.contextPath}/app/delPushNotifyHistory_in.htm',
					backDelPushNotifyHistory_, {
						"id" : arguments[0]
					});
	}
	function backDelPushNotifyHistory_(data) {
		if (data[0]['info'] == 'ok') {
			alert('重新发布成功');
			ajaxQuery();
		} else {
			alert('重新发布失败');
		}
	}
	function del_() {
		if (confirm('确定删除此应用吗？'))
			ajaxPost('${pageContext.request.contextPath}/app/del_in.htm',
					backDel_, {
						'id' : arguments[0]
					});
	}
	function backDel_(data) {
		if (data[0]['info'] == 'ok') {
			alert('删除成功');
			ajaxQuery();
		} else if (data[0]['info'] == 'TAppArticle') {
			alert('此应用和资讯有关联,不允许删除');
		} else if (data[0]['info'] == 'TAppComment') {
			alert('此应用和应用评论有关联,不允许删除');
		} else if (data[0]['info'] == 'TAppDownload') {
			alert('此应用和应用下载有关联,不允许删除');
		} else if (data[0]['info'] == 'THomeRecommend') {
			alert('此应用和主页推荐有关联,不允许删除');
		} else if (data[0]['info'] == 'THomeRecommendTop') {
			alert('此应用和主页推荐TOP有关联,不允许删除');
		} else if (data[0]['info'] == 'TMonthRank') {
			alert('此应用和月排行有关联,不允许删除');
		} else if (data[0]['info'] == 'TRecommend') {
			alert('此应用和应用推荐有关联,不允许删除');
		} else if (data[0]['info'] == 'TUserApp') {
			alert('此应用和用户应用有关联,不允许删除');
		} else if (data[0]['info'] == 'TWeekRank') {
			alert('此应用和周排行有关联,不允许删除');
		} else {
			alert('删除失败');
		}
	}
</script>
</head>
<body>
	<div class="main">
		<div class="nav">应用管理</div>
		<div class="selectthings">
			<table>
				<tr>
					<td width="100%">应用ID：<input type="text" name="id"
						style="width:100px"
						onkeyup="this.value=this.value.replace(/[^0-9]/g,'')">&nbsp;&nbsp;
						应用名称：<input type="text" name="name" style="width:100px">&nbsp;&nbsp;

						应用来源： <select name="appSource">
							<option value=""></option>
							<option value="0">艾麒</option>
							<option value="1">360市场</option>
							<option value="4">应用宝市场</option>
					</select> 作者名称：<input type="text" name="develope" style="width:100px">&nbsp;&nbsp;
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
						<th width="10%">应用ID</th>
						<th width="10%">应用名称</th>
						<th width="10%">版本</th>
						<th width="10%">作者/公司</th>
						<th width="10%">应用来源</th>
						<th width="10%">操作</th>
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