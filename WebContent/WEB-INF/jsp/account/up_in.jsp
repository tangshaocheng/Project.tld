<html>
<head>
<%@ include file="/WEB-INF/jsp/header.jsp"%>
<%@ page contentType="text/html; charset=utf-8"%>
<title></title>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
<link href="${pageContext.request.contextPath}/css/layout.css" rel="stylesheet" type="text/css">
<script type="text/javascript">
function add_(){
	if($.trim($('input[name=lname]').val()) == ''){
		alert('登陆账号不能为空');
		$('input[name=lname]').focus();
		return;
	}
	if($.trim($('input[name=lps]').val()) == ''){
		alert('登陆密码不能为空');
		$('input[name=lps]').focus();
		return;
	}
	if($.trim($('input[name=rlps]').val()) == ''){
		alert('确认密码不能为空');
		$('input[name=rlps]').focus();
		return;
	}
	if($.trim($('input[name=lps]').val()) != $.trim($('input[name=rlps]').val())){
		alert('两次输入密码不一致');
		$('input[name=rlps]').focus();
		return;
	}
	if($('select[name=userId]@selected').val()==null){
		alert('关联用户不能为空');
		$('select[name=userId]').focus();
		return;
	}

	ajaxPost('${pageContext.request.contextPath}/account/up_in.htm',backAdd_);
}
function backAdd_(data){
	if(data[0]['info']=='ok'){
		parent.queryData();
		alert('修改账号成功');
		parent.closeMyPop();
	}else if(data[0]['info']=='exits'){
		alert('此账号已经存在');
		return;
	}else{
		alert('修改账号失败');
	}
}
$(function(){
	$('input[name=lname]').val('${vo.lname}');
});
</script>
</head>

<body>
<div class="main">
	<div class="nav">修改账号</div>
	<div class="selectthings">
		<table>
			<tr class="odd2">
				<th width="25%">登陆账号</th>
				<td width="75%">
					<input type="text" name="lname" value="${vo.lname}" readonly style="width:300px" />
				</td>
	     	</tr>
	     	<tr class="odd2">
	     		<th width="25%">登陆密码</th>
	     		<td width="75%">
	     	    	<input type="password" name="lps" style="width:300px"/>
	     		</td>
	     	</tr>
	     	<tr class="odd2">
	     		<th width="25%">确认密码</th>
	     		<td width="75%">
	     	    	<input type="password" name="rlps" style="width:300px"/>
	     		</td>
	     	</tr>
	     	<tr class="odd2">
	     		<th width="25%">账号类型</th>
	     		<td width="75%">
	     	    	<select name="userType" style="width:300px">
	     	    		<c:if test="${vo.user_type==3}">
	     	    			<option value="3">个人</option>
	     	    		</c:if>
	     	    		<c:if test="${vo.user_type==4}">
	     	    			<option value="4">公司</option>
	     	    		</c:if>
	     	    	</select>
	     		</td>
	     	</tr>
	     	<tr class="odd2">
	     		<th width="25%">关联用户</th>
	     		<td width="75%">
	     			<select name="userId" style="width:300px">
	     				<option value="${vo.user_id}">${vo.nickname}</option>
	     			</select>
	     		</td>
			</tr>
			<tr class="even">
		        <td colspan="4" align="center">
		        	<input type="button" value="确     定" onclick="add_()" class="btn"/>&nbsp;&nbsp;&nbsp;&nbsp;
		        	<input type="button" value="关     闭" onclick="parent.closeMyPop();" class="btn"/>
		 		</td>
			</tr>
		</table>
	</div>
</div>
</body>
</html>