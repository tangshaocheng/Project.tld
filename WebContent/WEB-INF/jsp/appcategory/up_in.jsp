<html>
<head>
<%@ include file="/WEB-INF/jsp/header.jsp"%>
<%@ page contentType="text/html; charset=utf-8"%>
<title></title>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
<link href="${pageContext.request.contextPath}/css/layout.css" rel="stylesheet" type="text/css">
<script type="text/javascript">
function add_(){
	if(strTrim($('#name').val())==''){
		alert('请填写名称');
		$("input[name='name']").focus();
		return;
	}
	if(strTrim($('#seq').val())==''){
		alert('请填写序号');
		$("input[name='seq']").focus();
		return;
	}
	if(strTrim($('#remark').val())==''){
		alert('请填写备注');
		$("input[name='remark']").focus();
		return;
	}
	if(strTrim($('#icon').val())!=''){
		var picName = strTrim($('#icon').val());
		var suffix = picName.substr(picName.lastIndexOf('.')+1).toLowerCase();  
		if(suffix!='png'&&suffix!='jpg'){
			alert('图标文件格式只能是 png 或 jpg');
			return false;
		}
	}

	$('#tform').submit();
}
function backAdd_(data){
	if(data=='ok'){
		parent.queryData();
		alert('修改成功');
		parent.closeMyPop();
	}else if(data=='duplicate'){
		alert('此分类已存在');
		return;
	}else if (data=='seqDuplicate'){
		alert('序号不能重复');
		return;
	}else{
		alert('修改失败');
	}
}
function previewPicture(){
	previewImage('icon');
	var ext = file.value.substring(file.value.lastIndexOf('.')+1).toLowerCase();
	if(ext!='png' && ext!='jpg'){
		aler('图片文件格式只能是 png 或 jpg');
		return false;
	}
	return true;
}
/**
 * 预览图片
 */
function previewImage(type){
	var pic = document.getElementById(type+'-preview');
	var file = document.getElementById(type);
     /** IE浏览器 **/
	if(document.all){
		file.select();
      	var reallocalpath = document.selection.createRange().text;
       	var ie6 = /msie 6/i.test(navigator.userAgent);
		/** IE6浏览器设置img的src为本地路径可以直接显示图片 **/
		if(ie6){
			pic.src = reallocalpath;
		}else{
			/** 非IE6版本的IE由于安全问题直接设置img的src无法显示本地图片，但是可以通过滤镜来实现 **/
        	pic.style.filter = "progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod='image', src='" + reallocalpath + "')";
      		/** 设置img的src为base64编码的透明图片 取消显示浏览器默认图片 **/
			pic.src = 'data:image/gif;base64,R0lGODlhAQABAIAAAP///wAAACH5BAEAAAAALAAAAAABAAEAAAICRAEAOw==';
		}
	}else{
		var file = file.files[0];
		var reader = new FileReader();
		reader.readAsDataURL(file);
		reader.onload = function(e){
			var pic = document.getElementById(type+'-preview');
			pic.src = this.result;
		}
	}
}
</script>
</head>
<body>

<div class="main">
	<div class="nav">修改分类</div>
	<div class="selectthings">
		<form name="tform" id="tform" action="${pageContext.request.contextPath}/appcategory/up_in.htm" method="post" enctype="multipart/form-data" target="hidden_frame">
			<input type="hidden" name="id" id="id" value="${vo.id}">
			<table>
				<tr class="odd2">
					<td width="25%">父分类<font color="red">*</font>：</td>
					<td width="75%">${fatherName}</td>
			 	</tr>
		     	<tr class="odd2">
		     		<td width="25%">名称<font color="red">*</font>：</td>
		     		<td width="75%"><input type="text" name="name" id="name" value="${vo.name}" maxlength="100" style="width:300px"></td>
		     	</tr>
		     	<tr class="even">
		     		<td width="25%">序号<font color="red">*</font>：</td>
		     		<td width="75%"><input type="text" name="seq" id="seq" value="${vo.seq}" maxlength="11" style="width:300px" onkeyup="this.value=this.value.replace(/\D/g,'")" ></td>
		     	</tr>
		     	<tr class="even">
		     		<td width="25%">描述<font color="red">*</font>：</td>
		     		<td width="75%"><textarea id="remark" name="remark" rows="12" cols="96" maxlength="1000" style='padding:3px; font-family:"Courier New",Courier,monospace;'>${vo.remark}</textarea></td>
		     	</tr>
		     	<tr class="even">
		     		<td width="25%">图标<font color="red">*</font>：</td>
		     		<td width="75%">
		     			<input type="file" name="icon" id="icon" onchange="previewPicture('icon');" style="width:200px">
		       			<img name="icon-preview" id="icon-preview" src="${pageContext.request.contextPath}/downloadPic.htm?picUrl=${vo.icon}" width="105" height="45" border="0" />
		     		</td>
		     	</tr>
		     	<tr class="even">
					<td width="25%">发布状态<font color="red">*</font>：</td>
					<td width="75%">
				   		<select id="pubStatus" name="pubStatus" style="width: 100px">
				        	<option value="1"<c:if test="${vo.pubStatus==1}"> selected</c:if>>是</option>
				       	 	<option value="0"<c:if test="${vo.pubStatus==0}"> selected</c:if>>否</option>
				    	</select>
					</td>
			 	</tr>
		     	<tr class="even">
		        	<td colspan="2" align="center">
		        		<input type="button" value="确     定" onclick="add_()" class="btn"/>&nbsp;&nbsp;&nbsp;&nbsp;
		        		<input type="button" value="关     闭" onclick="parent.closeMyPop();" class="btn"/>
		        	</td>
		     	</tr>
		</table>
		</form>
	</div>
</div>
<iframe name="hidden_frame" id="hidden_frame" style="display:none"></iframe>

</body>
</html>