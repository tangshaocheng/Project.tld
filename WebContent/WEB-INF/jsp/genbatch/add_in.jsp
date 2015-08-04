<html>
<head>
<%@ include file="/WEB-INF/jsp/header.jsp"%>
<%@ page contentType="text/html; charset=utf-8"%>
<title></title>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
<link href="${pageContext.request.contextPath}/css/layout.css" rel="stylesheet" type="text/css">
<script type="text/javascript">
function add_(){
	var empty = false;
	var exist = false;
	var name = new Array();
	var nameId = '';
	$('input[name=name]').each(function(){
    	if($.trim($(this).val())==''){
    		empty = true;
    		nameId = $(this).attr('id');
    		return false;
    	}
    	if($.inArray($(this).val(), name)>=0){
    		exist = true;
    		nameId = $(this).attr('id');
    		return false;
    	}
    	name.push($(this).val());
  	});
	if(empty){
		alert('合作商不能为空');
		$('#'+nameId).focus();
		return;
	}
	if(exist){
		alert('合作商不能重复');
		$('#'+nameId).focus();
		return;
	}

	ajaxPost('${pageContext.request.contextPath}/genbatch/add_in.htm',backAdd_);
}
function backAdd_(data){
	if(data[0]['info']=='ok'){
		parent.queryData();
		if(data[0]['batchs']=='') alert('添加批次号成功');
		else alert('添加批次号成功，重复数据：' + data[0]['batchs']);
		parent.closeMyPop();
	}else if(data[0]['info']=='exits'){
		alert('此批次号已经存在');
		return;
	}else{
		alert('添加批次号失败');
	}
}



	Date.prototype.format = function(format) {
		var o = {
			"M+" : this.getMonth() + 1, //month 
			"d+" : this.getDate(), //day 
			"h+" : this.getHours(), //hour 
			"m+" : this.getMinutes(), //minute 
			"s+" : this.getSeconds(), //second 
			"q+" : Math.floor((this.getMonth() + 3) / 3), //quarter 
			"S" : this.getMilliseconds()
		//millisecond 
		}

		if (/(y+)/.test(format)) {
			format = format.replace(RegExp.$1, (this.getFullYear() + "")
					.substr(4 - RegExp.$1.length));
		}

		for ( var k in o) {
			if (new RegExp("(" + k + ")").test(format)) {
				format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k]
						: ("00" + o[k]).substr(("" + o[k]).length));
			}
		}
		return format;
	}

	var index = 1;
	var maxBatchId = '${maxBatchId}';
	function addBatch() {
		maxId = parseInt(maxBatchId.split('_M')[1]) + 1;
		var now = new Date();
		var timestamp = now.format("yyyyMMddhhmmssS");
		maxBatchId = timestamp + '_M' + maxId;
		// 	$('tr[class=odd2]:last').after('<tr class="odd2" id="tr_name_'+index+'"><td width="35%"><input type="text" name="name" id="name_'+index+'" style="width:300px"/></td><td width="45%"><input type="text" name="batchId" id="batchId_'+index+'" value="'+maxBatchId+'" readonly style="width:300px"/></td><td width="20%">[<a onclick="delBatch(this);" style="color: red;cursor: pointer;">删除</a>]</td></tr>');
		$('tr[class=odd2]:last')
				.after(
						'<tr class="odd2" id="tr_name_'+index+'"><td width="35%"></td><td width="45%"><input type="text" name="batchId" id="batchId_'+index+'" value="'+maxBatchId+'" readonly style="width:300px"/></td><td width="35%"><input type="text" name="remark" id="remark_'+index+'" value=""  style="width:200px"/></td><td width="20%">[<a onclick="delBatch(this);" style="color: red;cursor: pointer;">删除</a>]</td></tr>');
		index++;
	}
	function delBatch(obj) {
		$(obj).parent().parent().remove();
	}
</script>
</head>

<body>
<div class="main">
	<div class="nav">添加批次号</div>
	<div class="selectthings">
		<table style="text-align: center;">
			<tr class="even">
	     		<td width="15%">合作商</td>
	     		<td width="45%">批次号</td>
	     		<td width="30%">备注</td>
	     		<td width="15%">操作&nbsp;&nbsp;[<a onclick="addBatch();" style="color: red;font-size: 15px;cursor: pointer;" title="增加">+</a>]</td>
			</tr>
			<tr class="odd2" id="tr_name_0">
				<td width="15%">
	     	    	<input type="text" name="name" id="name_0" style="width:100px"/>
	     		</td>
	     		<td width="45%">
	     			<input type="text" name="batchId" id="batchId_0" value="${maxBatchId}" style="width:300px"/>
	     		</td>
	     		<td width="35%">
	     			<input type="text" name="remark" id="remark_0" value="" style="width:200px"/>
	     		</td>
	     		<td width="15%">
	     			[<a style="color: red;cursor: pointer;">删除</a>]
	     		</td>
			</tr>
			<tr class="even">
		        <td colspan="3" align="center">
		        	<input type="button" value="确     定" onclick="add_()" class="btn"/>&nbsp;&nbsp;&nbsp;&nbsp;
		        	<input type="button" value="关     闭" onclick="parent.closeMyPop();" class="btn"/>
		 		</td>
			</tr>
		</table>
	</div>
</div>
</body>
</html>