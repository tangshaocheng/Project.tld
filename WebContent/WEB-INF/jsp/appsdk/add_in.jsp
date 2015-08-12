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
		alert('SDK名称不能为空');
		$('#name').focus();
		return;
	}
	if($('#activeTime').val()==''){
		alert('激活时间不能为空');
		$('#activeTime').focus();
		return;
	}

	ajaxPost('${pageContext.request.contextPath}/appsdk/add_in',backAdd_);
}
function backAdd_(data){
	if(data[0]['info']=='ok'){
		parent.queryData();
		alert('添加成功');
		parent.closeMyPop();
	}else if(data[0]['info']=='exits'){
		alert('此sdk已经存在');
		return;
	}else{
		alert('添加失败');
	}
}

function cType(){
	var type=$('#bagType').val();
	if(type==1) $('#name').attr('readonly',false);
	else if(type==0) $('#name').attr('readonly',true);
}
function cBatch(){
	showDivDetail('${pageContext.request.contextPath}/appsdk/choose_in.htm?type=2',700,500,10,10);
}
var indexApp=1;
function chooseBatch(){
	var name = arguments[0].split(',');
	var batchId = arguments[1].split(',');
	var remark = arguments[2].split(',');
	var currentHtml = $('#gridtbody').html();
	var html = "";
	for(var i=0;i<name.length;i++){
		var flag = true;
		$('input[alt="themeId"]').each(function(){
	    	if($(this).val()==name[i]) flag = false;
	  	});
	  	if(!flag) continue;
		var operation="[<a href='javascript:del_("+indexApp+")'>删除</a>]";
		html+="<tr class='even' id='tr_td_"+indexApp+"'>";
			html+='<td>'+name[i]+'</td>';
			html+='<td>'+batchId[i]+"<input type='' name='batchId' alt='themeId' value='"+batchId[i]+"'></td>";
			html+='<td>'+remark[i]+'</td>';
			html+='<td>'+operation+'</td>';
			html+='</tr>';
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
	<div class="nav">添加SDK</div>
	<div class="selectthings">
		<table>
			<tr class="even">
	     		<td width="25%">SDK名称：<font color="red">*</font></td>
	     		<td width="75%">
	     	    	<input type="text" name="name" id="name" style="width:300px"/>
	     		</td>
			</tr>
			<tr class="even">
	     		<td width="25%">SDK类型：<font color="red">*</font></td>
	     		<td width="75%">
	     			<select name="bagType" id="bagType" style="width:300px">
	     				<option value="1">开启</option>
	     				<option value="0">关闭</option>
	     			</select>
	     		</td>
			</tr>
			<tr class="even">
	     		<td width="25%">国家：<font color="red">*</font></td>
	     		<td width="75%">
	     			<select name="country" id="country" style="width:300px">
	     				<option value="1">中国</option>
	     				<option value="0">外国</option>
	     			</select>
	     		</td>
			</tr>
			<tr class="even">
	     		<td width="25%">激活时间：<font color="red">*</font></td>
	     		<td width="75%">
	     	    	<input type="text" name="activeTime" id="activeTime" style="width:300px"/>
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
<script type="text/javascript">  
	$(document).ready(function(){  
    	$("#gridtbody").dragsort({ itemSelector: "tr", dragSelector: "tr", dragBetween: true,placeHolderTemplate: "<tr></tr>" });  
    });
</script> 