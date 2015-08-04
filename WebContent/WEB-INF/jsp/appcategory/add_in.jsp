<html>
<head>
<%@ include file="/WEB-INF/jsp/header.jsp"%>
<%@ page contentType="text/html; charset=utf-8"%>
<title></title>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
<link href="${pageContext.request.contextPath}/css/layout.css" rel="stylesheet" type="text/css">
<script type="text/javascript">
function add() {
	if(strTrim($('#categoryId').val())=='0'){
		alert('请选择一级分类');
		$('select[name="categoryId"]').focus();
		return;
	}
	if(strTrim($('#name').val())==''){
		alert('请填写名称');
		$('input[name="name"]').focus();
		return;
	}
	if(strTrim($('#seq').val())==''){
		alert('请填写序号');
		$('input[name="seq"]').focus();
		return;
	}
	if(strTrim($('#remark').val())==''){
		alert('请填写备注');
		$('input[name="remark"]').focus();
		return;
	}
	if(strTrim($('#icon').val())==''){
		alert('请选择图标');
		$('input[name="icon"]').focus();
		return;
	}else{
		var picName = strTrim($('#icon').val());
		var suffix = picName.substr(picName.lastIndexOf('.')+1).toLowerCase();  
		if(suffix!='png'&&suffix!='jpg'){
			alert('图标文件格式只能是 png 或 jpg');
			return false;
		}
	}
	$('#tform').submit();
}
function backAdd_(data) {
	if(data=='ok'){
		parent.queryData();
		if(!confirm('添加分类成功。继续添加？')){
			parent.closeMyPop();
		}else{
			location.reload();
		}
	}else if(data=='duplicate'){
		alert('此分类已存在');
		return;
	}else if(data=='seqDuplicate'){
		alert('序号不能重复');
		return;
	}else{
		alert('添加分类失败');
	}
}
$(document).ready(function (){
	$('#categoryId').change(function (){
		if($('#categoryId').val() != '') {
			ajaxPost('${pageContext.request.contextPath}/appcategory/getListByFatherId_in.htm',backCity,{'categoryId':$('#categoryId').val()});
		}else{
			$('#subCategoryId').empty();
			$('#subCategoryId').append('<option value="0" selected> -- 二级 -- </option>');
		}
	});
	ajaxPost('${pageContext.request.contextPath}/appcategory/getListByFatherId_in.htm',backCity,{'categoryId':$('#categoryId').val()});
});
function backCity(data){
	$('#subCategoryId').empty();
	$('#subCategoryId').append('<option value="0" selected> -- 二级 -- </option>');
	$.each(data,function(i,n){
		$('#subCategoryId').append('<option value='+n.id+'>'+n.name+'</option>');
    });
}
</script>
</head>
<body>

<div class="main">
<div class="nav">添加分类</div>
<div class="selectthings">
	<form name="tform" id="tform" action="${pageContext.request.contextPath}/appcategory/add_in.htm" method="post" enctype="multipart/form-data" target="hidden_frame">
		<table>
			<tr class="odd2">
				<td width="25%">父分类<font color="red">*</font>：</td>
				<td width="75%">
					<select name="categoryId" id="categoryId" style="width: 120">
						<option value="0"> -- 一级 -- </option>
						<c:forEach items="${categoryList}" var="category">
							<option value="${category.id}">${category.name}</option>
						</c:forEach>
					</select>&nbsp;&nbsp;
					<select name="subCategoryId" id="subCategoryId" style="width: 120">
						<option value="0"> -- 二级 -- </option>
					</select>&nbsp;&nbsp;
					<span style="color: C5C5C5;font-size: 10px;">不选则默认二级分类</span>
				</td>
			</tr>
			<tr class="odd2">
				<td width="25%">名称<font color="red">*</font>：</td>
				<td width="75%"><input type="text" id="name" name="name" maxlength="100" style="width: 300px"></td>
			</tr>
			<tr class="even">
				<td width="25%">序号<font color="red">*</font>：</td>
				<td width="75%"> <input type="text" id="seq" name="seq" maxlength="11"  style="width: 100px"  onkeyup="this.value=this.value.replace(/\D/g,'')" ></td>
			</tr>
					
			<tr class="even">
				<td width="25%">描述<font color="red">*</font>：</td>
				<td width="75%"> <input type="text" id="remark" name="remark" maxlength="1000" style="width: 300px"></td>
			</tr>

			<tr class="even">
				<td width="25%">图标<font color="red">*</font>：</td>
				<td width="75%"><input type="file" id="icon" name="icon" style="width: 300px"></td>
			</tr>
			<tr class="even">
				<td width="25%">发布状态<font color="red">*</font>：</td>
				<td width="75%">
					<select id="pubStatus" name="pubStatus" style="width: 100px">
						<option value="1" selected="selected">是</option>
						<option value="0">否</option>
					</select>
				</td>
			</tr>
			<tr class="even">
				<td colspan="2" align="center">
					<input type="button" value="确     定" onclick="add()" class="btn" />&nbsp;&nbsp;&nbsp;&nbsp;
					<input type="button" value="关     闭" onclick="parent.closeMyPop();" class="btn"/>
				</td>
			</tr>
		</table>
	</form>
</div>
</div>
	<iframe name="hidden_frame" id="hidden_frame" style="display: none"></iframe>
</body>
</html>