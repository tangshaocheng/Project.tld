<html>
<head>
<%@ include file="/WEB-INF/jsp/header.jsp"%>
<%@ page contentType="text/html; charset=utf-8"%>
<title></title>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
<link href="${pageContext.request.contextPath}/css/layout.css" rel="stylesheet" type="text/css">
<script type="text/javascript">
function add_(){
	if($('#name').val()==''){
		alert('专题包名称不能为空');
		$('#name').focus();
		return;
	}
	if($('input[name="subId"]').length==0){
		alert('元素列表不能为空');
		return;
	}

	ajaxPost('${pageContext.request.contextPath}/appthemebagsortsx/up_in.htm',backAdd_);
}
function backAdd_(data){
	if(data[0]['info']=='ok'){
		parent.queryData();
		alert('修改专题包成功');
		parent.closeMyPop();
	}else if(data[0]['info']=='exits'){
		alert('此专题包已经存在');
		return;
	}else{
		alert('修改专题包失败');
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
function cType(){
	var type=$('#bagType').val();
	if(type==1) $('#name').attr('readonly',false);
	else if(type==0) $('#name').attr('readonly',true);
}
function cApp(){
	showDivDetail('${pageContext.request.contextPath}/app/choose_in.htm?type=2',700,500,10,10);
}
var indexApp=1;
function chooseApp(){
	var ids = arguments[0].split(',');
	var names = arguments[1].split(',');
	var authors = arguments[2].split(',');
	for(var i=0;i<ids.length;i++){
		var flag = true;
		$('input[alt="appId"]').each(function(){
	    	if($(this).val()==ids[i]) flag = false;
	  	});
	  	if(!flag) continue;
		var operation="[<a href='javascript:del_("+indexApp+")'>删除</a>]";
		var html="<tr class='even' id='tr_td_"+indexApp+"'>";
			html+="<td><input type='text' name='sort' value=''></td>";
			html+="<td>应用<input type='hidden' name='subType' value='1'></td>";
			html+='<td>'+ids[i]+"<input type='hidden' name='subId' alt='appId' value='"+ids[i]+"'></td>";
			html+='<td>'+names[i]+'</td>';
			html+='<td>'+authors[i]+'</td>';
			html+='<td>'+operation+'</td>';
			html+='</tr>';
		$('#listTable').append(html);
		indexApp++;
	}
	closeMyPop();
}
function cTheme(){
	showDivDetail('${pageContext.request.contextPath}/apptheme/choose_in.htm?type=2',700,500,10,10);
}
function chooseTheme(){
	var ids = arguments[0].split(',');
	var names = arguments[1].split(',');
	for(var i=0;i<ids.length;i++){
		var flag = true;
		$('input[alt="themeId"]').each(function(){
	    	if($(this).val()==ids[i]) flag = false;
	  	});
	  	if(!flag) continue;
		var operation="[<a href='javascript:del_("+indexApp+")'>删除</a>]";
		var html="<tr class='even' id='tr_td_"+indexApp+"'>";
			html+="<td><input type='text' name='sort' value=''></td>";
			html+="<td>专题<input type='hidden' name='subType' value='2'></td>";
			html+='<td>'+ids[i]+"<input type='hidden' name='subId' alt='themeId' value='"+ids[i]+"'></td>";
			html+='<td>'+names[i]+'</td>';
			html+='<td></td>';
			html+='<td>'+operation+'</td>';
			html+='</tr>';
		$('#listTable').append(html);
		indexApp++;
	}
	closeMyPop();
}
function del_(){
	$('#tr_td_'+arguments[0]).remove();
}
</script>
</head>

<body>
<div class="main">
	<div class="nav">修改专题包</div>
	<div class="selectthings">
		<table>
			<tr class="even">
	     		<td width="25%">专题包名称：<font color="red">*</font></td>
	     		<td width="75%">
	     	    	<input type="text" name="name" id="name" value="${vo.name}" style="width:300px"/>
	     		</td>
			</tr>
			<tr class="even">
	     		<td width="25%">专题包类型：<font color="red">*</font></td>
	     		<td width="75%">
	     			<select name="bagType" id="bagType" style="width:300px">
	     				<option value="1"<c:if test="${vo.bagType==1}"> selected</c:if>>有标题</option>
	     				<option value="0"<c:if test="${vo.bagType==0}"> selected</c:if>>无标题</option>
	     			</select>
	     		</td>
			</tr>
			<tr class="even">
		     	<td width="100%" colspan="2">
			     	<span style="width: 175px;">元素列表：<font color="red">*</font></span>
			     	<input type="button" value="选择应用" class="btn" onclick="cApp()">
			     	<span style="width: 103px;"></span>
			     	<input type="button" value="选择专题" class="btn" onclick="cTheme()">
		     	</td>
			</tr>
			<tr class="odd2">
     			<td width="100%" colspan="2">
     	    		<table id="listTable">
     	    			<tr id="tr_th_app">
				   			<th width="5%">排序</th>
				   			<th width="5%">元素类型</th>
				           	<th width="8%">应用ID/专题ID</th>
				           	<th width="10%">应用名称/专题名称</th>
				           	<th width="10%">作者</th>
				           	<th width="5%">操作</th>
				         </tr>
				         <c:forEach items="${vo.list}" var="tlist" varStatus="status">
				         	<tr class="even" id="tr_td_${status.index}">
								<td><input type="text" name="sort" value="${tlist.sort}"></td>
								<td><c:if test="${tlist.subType eq 1}">应用</c:if><c:if test="${tlist.subType eq 2}">专题</c:if><input type="hidden" name="subType" value="${tlist.subType}"></td>
								<td>${tlist.subId}<input type="hidden" name="appId" value="${tlist.subId}"></td>
								<td>${tlist.subName}</td>
								<td>${tlist.subAuthor}</td>
								<td>[<a href='javascript:del_(${status.index})'>删除</a>]</td>
							</tr>
				         </c:forEach>
     	    		</table>
     			</td>
     		</tr>
			<tr class="odd2">
		        <td colspan="2" align="center">
		        	<input type="button" value="确     定" onclick="add_()" class="btn"/>&nbsp;&nbsp;&nbsp;&nbsp;
		        	<input type="button" value="关     闭" onclick="parent.closeMyPop();" class="btn"/>
		 		</td>
			</tr>
		</table>
	</div>
</div>
</body>
</html>