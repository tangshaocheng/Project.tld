<html>
<head>
<%@ include file="/WEB-INF/jsp/header.jsp"%>
<%@ page contentType="text/html; charset=utf-8"%>
<title></title>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
<link href="${pageContext.request.contextPath}/css/layout.css" rel="stylesheet" type="text/css">
<script type="text/javascript">
function update(){
	if(strTrim($('#name').val())==''){
		alert('请输入应用名称');
		$('#name').focus();
		return;
	}
	if(strTrim($('#develope').val())==''){
		alert('请输入开发者');
		$('#develope').focus();
		return;
	}
	if(strTrim($('#version').val())==''){
		alert('请输入版本号');
		$('#version').focus();
		return;
	}
	if(strTrim($('#versionCode').val())==''){
		alert('请输入版本Code');
		$('#versionCode').focus();
		return;
	}
	if(strTrim($('#pakeage').val())==''){
		alert('请输入包名');
		$('#pakeage').focus();
		return;
	}
	if(strTrim($('#mainClass').val())==''){
		alert('请输入主类名');
		$('#mainClass').focus();
		return;
	}
	if(strTrim($('#embededVersion').val()) != '' || strTrim($('#embededPakeage').val()) != '' || strTrim($('#embededMainClass').val()) != '' || strTrim($('#embededApp').val()) != ''){
		if(strTrim($('#embededVersion').val())==''){
			alert('请输入内置版本号');
			$('#embededVersion').focus();
			return;
		}
		
		if(strTrim($('#embededPakeage').val())==''){
			alert('请输入内置包名');
			$('#embededPakeage').focus();
			return;
		}
		if(strTrim($('#embededMainClass').val())==''){
			alert('请输入内置主类名');
			$('#embededMainClass').focus();
			return;
		}
	}
	if(strTrim($('#appDesc').val())==''){
		alert('请输入应用描述');
		$('#appDesc').focus();
		return;
	}
	/*if(strTrim($('#upDesc').val())==''){
		alert('请输入版本更新介绍');
		$('#upDesc').focus();
		return;
	}*/
	if(strTrim($('#shareContent').val())==''){
		alert('请输入应用分享内容');
		$('#shareContent').focus();
		return;
	}
	if(strTrim($('#app').val())!=''){
		var picName = strTrim($('#app').val());
		var suffix = picName.substr(picName.lastIndexOf('.')+1).toLowerCase();  
		if(suffix!='apk'){
			alert('应用程序文件格式只能是 apk');
			return false;
		}
	}
	if(strTrim($('#mainPic').val())!=''){
		var picName = strTrim($('#mainPic').val());
		var suffix = picName.substr(picName.lastIndexOf('.')+1).toLowerCase();  
		if(suffix!='png'&&suffix!='jpg'){
			alert('主图片1文件格式只能是 png 或 jpg');
			return false;
		}
	}
	if(strTrim($('#mainPic2').val())!=''){
		var picName = strTrim($('#mainPic2').val());
		var suffix = picName.substr(picName.lastIndexOf('.')+1).toLowerCase();  
		if(suffix!='png'&&suffix!='jpg'){
			alert('主图片2文件格式只能是 png 或 jpg');
			return false;
		}
	}
	if(strTrim($('#icon').val())!=''){
		var picName = strTrim($('#icon').val());
		var suffix = picName.substr(picName.lastIndexOf('.')+1).toLowerCase();  
		if(suffix!='png'&&suffix!='jpg'){
			alert('图标文件格式只能是 png 或 jpg');
			return false;
		}
	}
	if(strTrim($('#pic1').val())!=''){
		var picName = strTrim($('#pic1').val());
		var suffix = picName.substr(picName.lastIndexOf('.')+1).toLowerCase();  
		if(suffix!='png'&&suffix!='jpg'){
			alert('应用截图1文件格式只能是 png 或 jpg');
			return false;
		}
	}
	if(strTrim($('#pic2').val())!=''){
		var picName = strTrim($('#pic2').val());
		var suffix = picName.substr(picName.lastIndexOf('.')+1).toLowerCase();  
		if(suffix!='png'&&suffix!='jpg'){
			alert('应用截图2文件格式只能是 png 或 jpg');
			return false;
		}
	}
	if(strTrim($('#pic3').val())!=''){
		var picName = strTrim($('#pic3').val());
		var suffix = picName.substr(picName.lastIndexOf('.')+1).toLowerCase();  
		if(suffix!='png'&&suffix!='jpg'){
			alert('应用截图3文件格式只能是 png 或 jpg');
			return false;
		}
	}
	if(strTrim($('#pic4').val())!=''){
		var picName = strTrim($('#pic4').val());
		var suffix = picName.substr(picName.lastIndexOf('.')+1).toLowerCase();  
		if(suffix!='png'&&suffix!='jpg'){
			alert('应用截图4文件格式只能是 png 或 jpg');
			return false;
		}
	}
	if(strTrim($('#pic5').val())!=''){
		var picName = strTrim($('#pic5').val());
		var suffix = picName.substr(picName.lastIndexOf('.')+1).toLowerCase();  
		if(suffix!='png'&&suffix!='jpg'){
			alert('应用截图5文件格式只能是 png 或 jpg');
			return false;
		}
	}

	$('#tform').submit();
}
function backUp_(data){
	if(data=='ok'){
		var pn = '${currentPageNum}';
		parent.queryData2(pn);
		alert('修改应用成功');
		parent.closeMyPop();
	}else if(data=='duplicate'){
		alert('此应用已存在');
		return;
	}else {
		alert('修改应用失败');
	}
}
</script>
</head>

<body>
<div class="main">
	<div class="nav">修改应用</div>
	<div class="selectthings">
		<form name="tform" id="tform" action="${pageContext.request.contextPath}/app/up.htm" method="post" enctype="multipart/form-data" target="hidden_frame">
			<input type="hidden" name="appSize" id="appSize" value="${vo.appSize}">
			<input type="hidden" name="downCnt" id="downCnt" value="${vo.downCnt}">
		    <table>
		 		<tr class="odd2">
			     	<td width="25%">应用ID<font color="red">*</font>：</td>
			     	<td width="75%"><input type="text" name="id" value="${vo.id}" id="id" maxlength="100" readonly="readonly" style="width:300px"></td>
		     	</tr> 
		     	<tr class="odd2">
		     		<td width="25%">应用名<font color="red">*</font>：</td>
		     		<td width="75%"><input type="text" name="name" id="name" value="${vo.name}" maxlength="100" style="width:300px"></td>
		     	</tr>
		     	<tr class="even">
		     		<td width="25%">作者<font color="red">*</font>：</td>
		     		<td width="75%"><input type="text" name="develope" id="develope" maxlength="100" value="${vo.develope}" style="width:300px"></td>
		     	</tr>
		     	<tr class="odd2">
			     	<td width="25%">热度：</td>
			     	<td width="75%">
			     	    <select name="heat" id="heat" style="width:100px" >
			     	      <option value=0 <c:if test="${vo.heat==0}">selected </c:if>   >☆☆☆☆☆</option>
			              <option value=1 <c:if test="${vo.heat==1}">selected </c:if>   >★☆☆☆☆</option>
			              <option value=2 <c:if test="${vo.heat==2}">selected </c:if>   >★★☆☆☆</option>
			              <option value=3 <c:if test="${vo.heat==3}">selected </c:if>   >★★★☆☆</option>
			              <option value=4 <c:if test="${vo.heat==4}">selected </c:if>   >★★★★☆</option>
			              <option value=5 <c:if test="${vo.heat==5}">selected </c:if>   >★★★★★</option>
			            </select>
			     	</td>
		     	</tr>
		     	<tr class="odd2">
					<td>线上app信息：<font color="red">*</font></td>
					<td>
						<table>
							 <tr class="even">
						     	<td width="25%">版本号<font color="red">*</font>：</td>
						     	<td width="75%"><input type="text" name="version" id="version" maxlength="100" value="${vo.version}" style="width:300px"></td>
						     </tr>
						     <tr class="even">
						     	<td width="25%">版本Code<font color="red">*</font>：</td>
						     	<td width="75%"><input type="text" name="versionCode" id="versionCode" maxlength="10" value="${vo.versionCode}" style="width:300px" onkeyup="this.value=this.value.replace(/\D/g,'')" ></td>
						     </tr>
						     <tr class="even">
						     	<td width="25%">包名<font color="red">*</font>：</td>
						     	<td width="75%"><input type="text" name="pakeage" id="pakeage" maxlength="100" value="${vo.pakeage}" style="width:300px"></td>
						     </tr>
						     <tr class="even">
						     	<td width="25%">主类名<font color="red">*</font>：</td>
						     	<td width="75%"><input type="text" name="mainClass" id="mainClass" maxlength="100" value="${vo.mainClass}" style="width:300px"></td>
						     </tr>
						      <tr class="even">
						     	<td width="25%">apk程序<font color="red">*</font>：</td>
						     	<td width="75%">
						     		<input type="file" name="app" id="app" style="width:300px"><a href="${pageContext.request.contextPath}/downloadApp.htm?appUrl=${vo.app}">下载</a>
						     	</td>
						     </tr>
						     <tr class="even">
						     	<td width="25%">系统固件<font color="red"></font>：</td>
						     	<td width="75%"><input type="text" name="osVersionMin" id="osVersionMin" maxlength="100" value="${vo.osVersionMin}" style="width:300px"></td>
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
								<td width="75%"><input type="text" id="embededVersion" maxlength="100"  value="${vo.embededVersion}" name="embededVersion" style="width: 300px"></td>
							</tr>
		
							<tr class="even">
								<td width="25%">内置包名：</td>
								<td width="75%"><input type="text" id="embededPakeage" maxlength="100" value="${vo.embededPakeage}" name="embededPakeage" style="width: 300px"></td>
							</tr>
		
							<tr class="even">
								<td width="25%">内置主类名：</td>
								<td width="75%"><input type="text" id="embededMainClass" maxlength="100" value="${vo.embededMainClass}" name="embededMainClass" style="width: 300px"></td>
							</tr>
							<tr class="even">
								<td width="25%">内置apk程序：</td>
								<td width="75%"><input type="file" id="embededApp" name="embededApp" style="width: 300px">
								  <a href="${pageContext.request.contextPath}/downloadApp.htm?appUrl=${vo.embededApp}">下载</a>
								</td>
							</tr>
						</table>
					</td>
				</tr>
		     	<tr class="even">
		     		<td width="25%">应用简描述<font color="red"></font>：</td>
		     		<td width="75%">
		     			<input type="text" name="singleWord" id="singleWord" maxlength="200" value="${vo.singleWord}" style="width:400px">
		     		</td>
		    	</tr>
		     	<tr class="even">
		     		<td width="25%">应用详描述<font color="red">*</font>：</td>
		     		<td width="75%">
		     			<textarea id="appDesc" name="appDesc" rows="12" cols="96" maxlength="1000" style='padding:3px; font-family:"Courier New",Courier,monospace;'>${vo.appDesc}</textarea>
		     		</td>
		     	</tr>
		    	<tr class="even">
		     		<td width="25%">版本更新介绍<font color="red">*</font>：</td>
		     		<td width="75%">
		     			<textarea id="upDesc" name="upDesc" rows="12" cols="96" maxlength="1000" style='padding:3px; font-family:"Courier New",Courier,monospace;'>${vo.upDesc}</textarea>
		     		</td>
		     	</tr>
		     	<tr class="even">
			    	<td width="25%">应用分享内容<!-- <font color="red">*</font> -->：</td>
					<td width="75%">
				  	 	<textarea id="shareContent" name="shareContent" rows="5" maxlength="1000" cols="96" style='padding: 3px; font-family: "Courier New", Courier, monospace;'>${vo.shareContent}</textarea>
					</td>
				</tr>
				<tr class="odd2">
					<td valign="top">所属分类<font color="red">*</font>：</td>
					<td>
						<select id="categoryId" name="categoryId" style="width: 120px">
							<c:forEach items="${categoryList}" var="category">
					     		<option value="${category.id}"<c:if test="${vo.categoryId eq category.id}"> selected</c:if>>${category.name}</option>
					     	</c:forEach>
						</select>
					</td>
				</tr>
		     	<tr class="odd2">
		     		<td width="25%">图标<font color="red">*</font>：</td>
		     		<td width="75%">
		     			<input type="file" name="icon" id="icon" style="width:300px"><img src="${pageContext.request.contextPath}/downloadPic.htm?picUrl=${vo.icon}" width="105" height="45" border="0" />
		     		</td>
		     	</tr>
		     
		     	<tr class="even">
		     		<td width="25%">主图片1<!-- <font color="red">*</font> -->：</td>
		     		<td width="75%">
		     			<input type="file" name="mainPic" id="mainPic" style="width:300px"><img src="${pageContext.request.contextPath}/downloadPic.htm?picUrl=${vo.mainPic}" width="105" height="45" border="0" />
					</td>
		     	</tr>
		     	<tr class="even">
		     		<td width="25%">主图片2<!-- <font color="red">*</font> -->：</td>
		     		<td width="75%">
		     			<input type="file" name="mainPic2" id="mainPic2" style="width:300px"><img src="${pageContext.request.contextPath}/downloadPic.htm?picUrl=${vo.mainPic2}" width="105" height="45" border="0" />
					</td>
		     	</tr>
		     	<tr class="even">
		     		<td width="25%">截图1<font color="red">*</font>：</td>
		     		<td width="75%">
		     			<input type="file" name="pic1" id="pic1" style="width:300px"><img src="${pageContext.request.contextPath}/downloadPic.htm?picUrl=${vo.pic1}" width="105" height="45" border="0" />
					</td>
		     	</tr>
		     	<tr class="even">
		     		<td width="25%">截图2<font color="red">*</font>：</td>
		     		<td width="75%">
		     			<input type="file" name="pic2" id="pic2" style="width:300px"><img src="${pageContext.request.contextPath}/downloadPic.htm?picUrl=${vo.pic2}" width="105" height="45" border="0" />
					</td>
		     	</tr>
		     	<tr class="even">
		     		<td width="25%">截图3：</td>
		     		<td width="75%">
		     			<input type="file" name="pic3" id="pic3" style="width:300px"><img src="${pageContext.request.contextPath}/downloadPic.htm?picUrl=${vo.pic3}" width="105" height="45" border="0" />
					</td>
		     	</tr>
		     	<tr class="even">
		     		<td width="25%">截图4：</td>
		     		<td width="75%">
		     			<input type="file" name="pic4" id="pic4" style="width:300px"><img src="${pageContext.request.contextPath}/downloadPic.htm?picUrl=${vo.pic4}" width="105" height="45" border="0" />
					</td>
		     	</tr>
		     	<tr class="even">
			     	<td width="25%">截图5：</td>
			     	<td width="75%">
			     		<input type="file" name="pic5" id="pic5" style="width:300px"><c:if test=""></c:if>  <img src="${pageContext.request.contextPath}/downloadPic.htm?picUrl=${vo.pic5}" width="105" height="45" border="0" />
					</td>
		     	</tr>
		     	<tr class="even">
			     	<td width="25%">应用标签<font color="red"></font>：</td>
			     	<td width="75%"><input type="text" name="appTag" id="appTag" maxlength="100" value="${vo.appTag}" style="width:400px"></td>
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
			        <td colspan="2" align="center">
			        	<input type="button" value="确     定" onclick="update()" class="btn"/>&nbsp;&nbsp;&nbsp;&nbsp;
			       	 	<input type="button" value="关     闭" onclick="javascript:parent.closeMyPop();" class="btn"/>
			        </td>	
		     	</tr>
			</table>
		</form>
	</div>
</div>
<iframe name="hidden_frame" id="hidden_frame" style="display:none"></iframe>

</body>
</html>