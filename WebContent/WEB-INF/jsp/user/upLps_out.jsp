<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/WEB-INF/jsp/header.jsp"%>
<%@ page contentType="text/html; charset=utf-8"%>
<title>上海艾麒乐七助手计费系统</title>
<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/new_layout.css" />
<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/themes/base/jquery-ui-10.css" />
<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/font-awesome.min.css" />
<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/table.css" />
<script type="text/javascript">
function add(){
	if($("input[name='oldps']").val()==''){
		alert("请输入原密码");
		$("input[name='oldps']").focus();
		return;
	}
	if(!inputGtLength("oldps",20,"原密码长度不允许超过20个字符")) return;
	if($("input[name='oldps']").val()==$("input[name='ps']").val()){
		alert("原密码和新密码一致，无需修改");
		$("input[name='oldps']").focus();
		return;
	}
	if($("input[name='ps']").val()==''){
		alert("请输入新密码");
		$("input[name='ps']").focus();
		return;
	}
	if(!inputGtLength("ps",20,"新密码长度不允许超过20个字符")) return;
	if($("input[name='rps']").val()==''){
		alert("请输入重复新密码");
		$("input[name='rps']").focus();
		return;
	}
	if(!inputGtLength("rps",20,"重复新密码长度不允许超过20个字符")) return;
	if($("input[name='ps']").val()!=$("input[name='rps']").val()){
		alert("两次输入密码不一致");
		return;
	}
	ajaxPost("${pageContext.request.contextPath}/user/upLps_out.htm",backAdd);
}
function backAdd(data){
	if(data[0]['info']=='isUser'){
		alert("用户超时,请退出重新登录！");
		return;
	}
	if(data[0]['info']=='old'){
		alert("原密码输入错误");
		return;
	}
	if(data[0]['info']=='ok'){
		alert("密码修改成功");
		location.reload();
		return;
	}
}
</script>
</head>
<body>
	<div class="main">
		<br/>
		<div class="formRow">
			<table id="datatable" class="ui-widget-content" cellspacing="1" style="width: 70%;">
		   		<tr class="even">
					<th width="25%" class="ui-widget-header" style="height: 35px; font-size: 13px; margin: 0 auto; font: '黑体';">原密码<font color="red">*</font>：</th>
			     	<td width="75%"><input type="password" name="oldps" style="width:300px" maxlength="20" id="oldps" onKeyDown= "if(event.keyCode==32) return false "></td>
				</tr>
			   	<tr class="odd2">
			     	<th width="25%" class="ui-widget-header" style="height: 35px; font-size: 13px; margin: 0 auto; font: '黑体';">新密码<font color="red">*</font>：</th>
			     	<td width="75%">
			     		<input type="password" name="ps" style="width:300px"  maxlength="20" id="ps"  onKeyDown= "if(event.keyCode==32) return false ">
			     	</td>
				</tr>
				<tr class="even">
			     	<th width="25%" class="ui-widget-header" style="height: 35px; font-size: 13px; margin: 0 auto; font: '黑体';">重复新密码<font color="red">*</font>：</th>
			     	<td width="75%">
			     		<input type="password" name="rps" style="width:300px"  maxlength="20" id="rps"  onKeyDown= "if(event.keyCode==32) return false ">
			     	</td>
				</tr>
				<tr class="odd2">
			  		<td colspan="2" align="center">
			        	<input type="button" value="确&nbsp;定" onclick="add()" class="buttonSubmit" style="width: 60px;">
			        </td>	
				</tr>
			</table>
		</div>
	</div>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-ui.min.js"></script>
</body>