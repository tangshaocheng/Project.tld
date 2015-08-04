<html>
<head>
<%@ include file="/WEB-INF/jsp/header.jsp"%>
<%@ page contentType="text/html; charset=utf-8"%>
<title></title>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
<link href="${pageContext.request.contextPath}/css/layout.css" rel="stylesheet" type="text/css">
<script type="text/javascript">
function add(){
	if(strTrim($("#name").val()) == ''){
		alert("请输入应用名称");
		$("#name").focus();
		return;
	}
	if(strTrim($("#develope").val()) == ''){
		alert("请输入开发者");
		$("#develope").focus();
		return;
	}
	if(strTrim($("#version").val()) == ''){
		alert("请输入版本号");
		$("#version").focus();
		return;
	}
	if(strTrim($("#versionCode").val()) == ''){
		alert("请输入版本Code");
		$("#versionCode").focus();
		return;
	}
	if(strTrim($("#pakeage").val()) == ''){
		alert("请输入包名");
		$("#pakeage").focus();
		return;
	}
	if(strTrim($("#mainClass").val()) == ''){
		alert("请输入主类名");
		$("#mainClass").focus();
		return;
	}
	if(strTrim($("#embededVersion").val()) != '' || strTrim($("#embededPakeage").val()) != '' || strTrim($("#embededMainClass").val()) != '' || strTrim($("#embededApp").val()) != ''){
		if(strTrim($("#embededVersion").val()) == ''){
			alert("请输入内置版本号");
			$("#embededVersion").focus();
			return;
		}
		
		if(strTrim($("#embededPakeage").val()) == ''){
			alert("请输入内置包名");
			$("#embededPakeage").focus();
			return;
		}
		if(strTrim($("#embededMainClass").val()) == ''){
			alert("请输入内置主类名");
			$("#embededMainClass").focus();
			return;
		}
		if(strTrim($("#embededApp").val()) == ''){
			alert("请选择内置apk程序");
			$("#embededApp").focus();
			return;
		}else{
			var picName = strTrim($("#embededApp").val());
			var suffix = picName.substr(picName.lastIndexOf(".")+1).toLowerCase();  
			if(suffix!='apk'){
				alert('内置apk文件格式只能是 apk');
				return false;
			}
		}
	}
	if(strTrim($("#appDesc").val()) == ''){
		alert("请输入应用描述");
		$("#appDesc").focus();
		return;
	}
	/*if(strTrim($("#upDesc").val()) == ''){
		alert("请输入版本更新介绍");
		$("#upDesc").focus();
		return;
	}*/
	if(strTrim($("#app").val()) == ''){
		alert("请选择apk程序");
		$("#app").focus();
		return;
	}else{
		var picName = strTrim($("#app").val());
		var suffix = picName.substr(picName.lastIndexOf(".")+1).toLowerCase();  
		if(suffix!='apk'){
			alert('apk文件格式只能是 apk');
			return false;
		}
	}
	
	/*if(strTrim($("#mainPic").val()) == ''){
		alert("请选择主图片");
		$("#imainPic").focus();
		return;
	}else{
		var picName = strTrim($("#mainPic").val());
		var suffix = picName.substr(picName.lastIndexOf(".")+1).toLowerCase();  
		if(suffix!='png'&&suffix!='jpg'){
			alert('图标文件格式只能是 png 或 jpg');
			return false;
		}
	}
	if(strTrim($("#mainPic2").val()) == ''){
		alert("请选择主图片");
		$("#imainPic2").focus();
		return;
	}else{
		var picName = strTrim($("#mainPic2").val());
		var suffix = picName.substr(picName.lastIndexOf(".")+1).toLowerCase();  
		if(suffix!='png'&&suffix!='jpg'){
			alert('图标文件格式只能是 png 或 jpg');
			return false;
		}
	}*/
	if(strTrim($("#mainPic").val())!='') {
		var picName = strTrim($("#mainPic").val());
		var suffix = picName.substr(picName.lastIndexOf(".")+1).toLowerCase();  
		if(suffix!='png'&&suffix!='jpg'){
			alert('图标文件格式只能是 png 或 jpg');
			return false;
		}
	}
	if(strTrim($("#mainPic2").val()) != '') {
		var picName = strTrim($("#mainPic2").val());
		var suffix = picName.substr(picName.lastIndexOf(".")+1).toLowerCase();  
		if(suffix!='png'&&suffix!='jpg'){
			alert('图标文件格式只能是 png 或 jpg');
			return false;
		}
	}
	if(strTrim($("#icon").val()) == ''){
		alert("请选择应用图标");
		$("#icon").focus();
		return;
	}else{
		var picName = strTrim($("#icon").val());
		var suffix = picName.substr(picName.lastIndexOf(".")+1).toLowerCase();  
		if(suffix!='png'&&suffix!='jpg'){
			alert('图标文件格式只能是 png 或 jpg');
			return false;
		}
	}
	if(strTrim($("#pic1").val()) == ''){
		alert("请选择应用截图1");
		$("#pic1").focus();
		return;
	}else{
		var picName = strTrim($("#pic1").val());
		var suffix = picName.substr(picName.lastIndexOf(".")+1).toLowerCase();  
		if(suffix!='png'&&suffix!='jpg'){
			alert('应用截图1文件格式只能是 png 或 jpg');
			return false;
		}
	}
	if(strTrim($("#pic2").val()) == ''){
		alert("请选择应用截图2");
		$("#pic2").focus();
		return;
	}else{
		var picName = strTrim($("#pic2").val());
		var suffix = picName.substr(picName.lastIndexOf(".")+1).toLowerCase();  
		if(suffix!='png'&&suffix!='jpg'){
			alert('应用截图2文件格式只能是 png 或 jpg');
			return false;
		}
	}
	if(strTrim($("#pic3").val())!=''){
		var picName = strTrim($("#pic3").val());
		var suffix = picName.substr(picName.lastIndexOf(".")+1).toLowerCase();  
		if(suffix!='png'&&suffix!='jpg'){
			alert('应用截图3文件格式只能是 png 或 jpg');
			return false;
		}
	}
	if(strTrim($("#pic4").val())!=''){
		var picName = strTrim($("#pic4").val());
		var suffix = picName.substr(picName.lastIndexOf(".")+1).toLowerCase();  
		if(suffix!='png'&&suffix!='jpg'){
			alert('应用截图4文件格式只能是 png 或 jpg');
			return false;
		}
	}
	if(strTrim($("#pic5").val())!=''){
		var picName = strTrim($("#pic5").val());
		var suffix = picName.substr(picName.lastIndexOf(".")+1).toLowerCase();  
		if(suffix!='png'&&suffix!='jpg'){
			alert('应用截图5文件格式只能是 png 或 jpg');
			return false;
		}
	}
	$("#tform").submit();
}
function backAdd(data){
	if(data == 'ok'){
		parent.queryData();
		if(!confirm("添加应用成功。继续添加？")){
			parent.closeMyPop();
		} else {
			location.reload();
		}
	} else if(data == 'duplicate'){
		alert("此应用ID已存在");
		return;
	} else if(data == 'isR'){
		alert("此应用已存在");
		return;
	} else {
		alert("添加应用失败");
	}
}
</script>
</head>
<body>

<div class="main">
	<div class="nav">添加应用</div>
	<div class="selectthings">
		<form name="tform" id="tform" action="${pageContext.request.contextPath}/app/add.htm" method="post" enctype="multipart/form-data" target="hidden_frame">
			<table>
				<input type="hidden" id="id"  name="id" value="-2">
				<tr class="odd2">
					<td width="25%">应用名<font color="red">*</font>：</td>
					<td width="75%"><input type="text" name="name" id="name" maxlength="100" style="width: 300px"></td>
				</tr>
				<tr class="even">
					<td width="25%">作者<font color="red">*</font>：</td>
					<td width="75%"><input type="text" name="develope" id="develope" maxlength="50" style="width: 300px"></td>
				</tr>
				<tr class="odd2">
					<td width="25%">热度<font color="red">*</font>：</td>
					<td width="75%">
						<select id="heat" name="heat" style="width: 100px">
								<option value="0">☆☆☆☆☆</option>
								<option value="1">★☆☆☆☆</option>
								<option value="2">★★☆☆☆</option>
								<option value="3">★★★☆☆</option>
								<option value="4">★★★★☆</option>
								<option value="5">★★★★★</option>
						</select>
					</td>
				</tr>
				<tr class="odd2">
					<td>线上app信息：<font color="red">*</font></td>
					<td>
						<table>
							<tr class="even">
								<td width="25%">版本号<font color="red">*</font>：</td>
								<td width="75%"><input type="text" id="version" maxlength="100" name="version" style="width: 300px"></td>
							</tr>
							<tr class="even">
						     	<td width="25%">版本Code<font color="red">*</font>：</td>
						     	<td width="75%"><input type="text" name="versionCode" id="versionCode" maxlength="10" value="" style="width:300px" onkeyup="this.value=this.value.replace(/\D/g,'')" ></td>
						     </tr>
							<tr class="even">
								<td width="25%">包名<font color="red">*</font>：</td>
								<td width="75%"><input type="text" id="pakeage" maxlength="100" name="pakeage" style="width: 300px"></td>
							</tr>
		
							<tr class="even">
								<td width="25%">主类名<font color="red">*</font>：</td>
								<td width="75%"><input type="text" id="mainClass" maxlength="100" name="mainClass" style="width: 300px"></td>
							</tr>
							<tr class="even">
								<td width="25%">apk程序<font color="red">*</font>：</td>
								<td width="75%"><input type="file" id="app" name="app" style="width: 300px"></td>
							</tr>
							<tr class="even">
						     	<td width="25%">系统固件<font color="red"></font>：</td>
						     	<td width="75%"><input type="text" name="osVersionMin" id="osVersionMin" maxlength="100" value="" style="width:300px" ></td>
						    </tr>
						</table>
					</td>
				</tr>
				<tr class="odd2">
					<td>内置app信息：</td>
					<td>
						<table>
							<tr class="even">
								<td width="25%">内置版本号：</td>
								<td width="75%"><input type="text" id="embededVersion" maxlength="100" name="embededVersion" style="width: 300px"></td>
							</tr>
		
							<tr class="even">
								<td width="25%">内置包名：</td>
								<td width="75%"><input type="text" id="embededPakeage" maxlength="100" name="embededPakeage" style="width: 300px"></td>
							</tr>
		
							<tr class="even">
								<td width="25%">内置主类名：</td>
								<td width="75%"><input type="text" id="embededMainClass" maxlength="100" name="embededMainClass" style="width: 300px"></td>
							</tr>
							<tr class="even">
								<td width="25%">内置apk程序：</td>
								<td width="75%"><input type="file" id="embededApp" name="embededApp" style="width: 300px"></td>
							</tr>
						</table>
					</td>
				</tr>
				<tr class="even">
			     	<td width="25%">应用简描述<font color="red"></font>：</td>
			     	<td width="75%"><input type="text" name="singleWord" id="singleWord" maxlength="200" value="" style="width:400px" ></td>
			    </tr>
				<tr class="even">
					<td width="25%">应用详描述<font color="red">*</font>：</td>
					<td width="75%">
					    <textarea id="appDesc" name="appDesc" rows="12" maxlength="1000" cols="96" style='padding: 3px; font-family: "Courier New", Courier, monospace;'></textarea>
					</td>
				</tr>
				<tr class="even">
					<td width="25%">版本更新介绍<font color="red">*</font>：</td>
					<td width="75%">
					    <textarea id="upDesc" name="upDesc" rows="12" cols="96" maxlength="1000" style='padding: 3px; font-family: "Courier New", Courier, monospace;'></textarea>
					</td>
				</tr>
				<tr class="even">
				    <td width="25%">应用分享内容<!-- <font color="red">*</font> -->：</td>
					<td width="75%">
					   <textarea id="shareContent" name="shareContent" rows="5" maxlength="1000" cols="96" style='padding: 3px; font-family: "Courier New", Courier, monospace;'></textarea>
					</td>
				</tr>
				<tr class="odd2">
					<td valign="top">所属分类<font color="red">*</font>：</td>
					<td>
						<select id="categoryId" name="categoryId" style="width: 120px">
							<c:forEach items="${categoryList}" var="category">
					     		<option value="${category.id}">${category.name}</option>
					     	</c:forEach>
						</select>
					</td>
				</tr>
				<tr class="even">
				    <td width="25%">是否新游戏<font color="red">*</font>：</td>
					<td width="75%">
					    <select id="notifyStatus" name="notifyStatus" style="width: 100px">
					        <option value="1">新游戏</option>
					        <option value="0">老游戏</option>
					    </select>
					</td>
				</tr>
				<tr class="odd2">
				    <td width="25%">PUSH新游戏通知内容<font color="red">*</font>：</td>
					<td width="75%">
					   <textarea id="notify" name="notify" rows="5" maxlength="1000" cols="96" style='padding: 3px; font-family: "Courier New", Courier, monospace;'></textarea>
					</td>
				</tr>
				<tr class="even">
					<td width="25%">主图片1<!-- <font color="red">*</font> -->：</td>
					<td width="75%"><input type="file" id="mainPic" name="mainPic" style="width: 300px"></td>
				</tr>
				<tr class="odd2">
					<td width="25%">主图片2<!-- <font color="red">*</font> -->：</td>
					<td width="75%"><input type="file" id="mainPic2" name="mainPic2" style="width: 300px"></td>
				</tr>
				<tr class="even">
					<td width="25%">图标<font color="red">*</font>：</td>
					<td width="75%"><input type="file" id="icon" name="icon" style="width: 300px"></td>
				</tr>
				<tr class="odd2">
					<td width="25%">截图1<font color="red">*</font>：</td>
					<td width="75%"><input type="file" id="pic1" name="pic1" style="width: 300px"></td>
				</tr>
				<tr class="even">
					<td width="25%">截图2<font color="red">*</font>：</td>
					<td width="75%"><input type="file" id="pic2" name="pic2" style="width: 300px"></td>
				</tr>
				<tr class="odd2">
					<td width="25%">截图3：</td>
					<td width="75%"><input type="file" id="pic3" name="pic3" style="width: 300px"></td>
				</tr>
				<tr class="even">
					<td width="25%">截图4：</td>
					<td width="75%"><input type="file" id="pic4" name="pic4"  style="width: 300px"></td>
				</tr>
				<tr class="odd2">
					<td width="25%">截图5：</td>
					<td width="75%"><input type="file" id="pic5" name="pic5" style="width: 300px"></td>
				</tr>
				<tr class="even">
			     	<td width="25%">应用标签<font color="red"></font>：</td>
			     	<td width="75%"><input type="text" name="appTag" id="appTag" maxlength="100" value="" style="width:400px"></td>
			    </tr>
			    <tr class="even">
			     	<td width="25%">硬件支持<font color="red"></font>：</td>
			     	<td width="75%"><input type="text" name="support" id="support" maxlength="100" value="" style="width:400px"></td>
			    </tr>
			    <tr class="even">
			     	<td width="25%">年龄限制<font color="red"></font>：</td>
			     	<td width="75%"><input type="text" name="ageLimit" id="ageLimit" maxlength="100" value="" style="width:400px"></td>
			    </tr>
				<tr class="odd2">
					<td colspan="2" align="center">
						<input type="button" value="确     定" onclick="add()" class="btn" />&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="button" value="关     闭" onclick="parent.closeMyPop();" class="btn" />
					</td>
				</tr>
			</table>
		</form>
	</div>
</div>
<iframe name="hidden_frame" id="hidden_frame" style="display: none"></iframe>
</body>