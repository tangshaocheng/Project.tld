<html>
<head>
<%@ include file="/WEB-INF/jsp/header.jsp"%>
<%@ page contentType="text/html; charset=utf-8"%>
<title></title>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
<link href="${pageContext.request.contextPath}/css/layout.css" rel="stylesheet" type="text/css">
<script src="${pageContext.request.contextPath}/js/jquery.dragsort-0.4.min.js"  type="text/javascript"></script>
<script type="text/javascript">
function add_(){
	if($('#name').val()==''){
		alert('专题名称不能为空');
		$('#name').focus();
		return;
	}
	if($('input[name="appId"]').length==0){
		alert('应用列表不能为空');
		return;
	}
	var flag = sort_();
	if(flag==1) {
		alert('序列号不能为空');
		return;
	}else if(flag==2){
		alert('序列号不能重复');
		return;
	}

	$('#tform').submit();
}
function backAdd_(data){
	if(data=='ok'){
		parent.queryData();
		alert('修改专题成功');
		parent.closeMyPop();
	}else if(data=='exits'){
		alert('此专题已经存在');
		return;
	}else{
		alert('修改专题失败');
	}
}
function previewPicture(){
	previewImage('pic');
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
function cApp(){
	showDivDetail('${pageContext.request.contextPath}/app/choose.htm?type=2',750,500,10,10);
}
var indexApp=1;
function chooseApp(){
	var ids = arguments[0].split(',');
	var names = arguments[1].split(',');
	var authors = arguments[2].split(',');
	var currentHtml = $('#gridtbody').html();
	var html = "";
	for(var i=0;i<ids.length;i++){
		var flag = true;
		$('input[name="appId"]').each(function(){
	    	if($(this).val()==ids[i]) flag = false;
	  	});
	  	if(!flag) continue;
		var operation="[<a href='javascript:del_("+indexApp+")'>删除</a>]";
		html+="<tr class='even' id='tr_td_"+indexApp+"'>";
			//html+="<td><input type='text' name='sort' value='' onkeyup='value=(value==0?\"\":value.replace(/[^\\d]|_/ig,\"\"))'></td>";
			html+='<td>'+ids[i]+"<input type='hidden' name='appId' value='"+ids[i]+"'></td>";
			html+='<td>'+names[i]+'</td>';
			html+='<td>'+authors[i]+'</td>';
			html+='<td>'+operation+'</td>';
			html+='</tr>';
		//$('#listTable').append(html);
		indexApp++;
	}
	$('#gridtbody').html(html + "" + currentHtml);
	closeMyPop();
}
function del_(){
	$('#tr_td_'+arguments[0]).remove();
}
function sort_(){
  	var flag = true;
	var arrs = new Array();
	$("input[name='sort']").each(function(i,j){
    	if($(this).val()!='') arrs[i] = $(this).val();
    	else{
			 flag = false;
			$(this).focus();
			return false;
    	}
  	});
  	if(!flag) return 1;
  	for(var i=0;i<arrs.length;i++){
  		for(var j=i;j<arrs.length-1;j++){
			if(arrs[i]==arrs[j+1]){
				flag = false;
				break;
			}
		}
  	}
	if(!flag) return 2;
	return 0;
}
</script>
</head>

<body>
<div class="main">
	<div class="nav">修改专题</div>
	<div class="selectthings">
		<form name="tform" id="tform" action="${pageContext.request.contextPath}/apptheme/up.htm" method="post" enctype="multipart/form-data" target="hidden_frame">
			<input type="hidden" name="id" id="id" value="${vo.id}">
			<table>
		       	<tr class="even">
		     		<td width="25%">专题名称：<font color="red">*</font></td>
		     		<td width="75%">
		     	    	<input type="text" name="name" id="name" value="${vo.name}"/>
		     		</td>
				</tr>
				<tr class="even">
			     	<td width="100%" colspan="2">
				     	<span style="width: 300px;">应用列表：<font color="red">*</font></span><input type="button" value="请选择" class="btn" onclick="cApp()">
			     	</td>
				</tr>
				<tr class="odd2">
	     			<td width="100%" colspan="2">
	     	    		<table id="listTable">
	     	    			<thead id="gridThead">
	     	    			<tr id="tr_th_app">
					   			<!-- 
					   			<th width="10%">排序</th> -->
					           	<th width="5%">应用ID</th>
					           	<th width="10%">应用名称</th>
					           	<th width="10%">作者</th>
					           	<th width="5%">操作</th>
					         </tr>
					         </thead>
					         <tbody id="gridtbody">
					         <c:forEach items="${vo.list}" var="tlist" varStatus="status">
					         	<tr class="even" id="tr_td_${status.index}">
									<!-- 
									<td><input type="text" name="sort" value="${tlist.sort}" onkeyup="value=(value=='0'?'0':value.replace(/[^\d|^\.]|_/ig,''))"></td> -->
									<td>${tlist.appId}<input type="hidden" name="appId" value="${tlist.appId}"></td>
									<td>${tlist.appName}</td>
									<td>${tlist.appAuthor}</td>
									<td>[<a href='javascript:del_(${status.index})'>删除</a>]</td>
								</tr>
					         </c:forEach>
					         </tbody>
	     	    		</table>
	     			</td>
	     		</tr>
	     		<tr class="even">
			     	<td width="25%">专题图标：<font color="red">*</font></td>
			     	<td width="75%">
		       			<input type="file" name="pic" id="pic" onchange="previewPicture('pic');" style="width:200px">
		       			<img name="pic-preview" id="pic-preview" src="${pageContext.request.contextPath}/downloadPic.htm?picUrl=${vo.pic}" width="105" height="45" border="0" />
			  		</td>
		    	</tr>
				<tr class="odd2">
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

<script type="text/javascript">  
	$(document).ready(function(){  
    	$("#gridtbody").dragsort({ itemSelector: "tr", dragSelector: "tr", dragBetween: true,placeHolderTemplate: "<tr></tr>" });  
    });
</script>  