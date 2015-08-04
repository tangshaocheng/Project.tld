<html>
<head>
<%@ include file="/WEB-INF/jsp/header.jsp"%>
<%@ page contentType="text/html; charset=utf-8"%>
<title></title>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
<link href="${pageContext.request.contextPath}/css/layout.css" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath}/js/jquery.autocomplete-1.1.3/styles.css" rel="stylesheet" type="text/css">
<script src="${pageContext.request.contextPath}/js/jquery.autocomplete-1.1.3/jquery.autocomplete-min.js" type="text/javascript"></script>
<script type="text/javascript">
function add_(){
	var empty = false;
	var exist = false;
	var repeat = new Array();
	var nickname = '';
	$('input[name=nickname]').each(function(){
    	if($.trim($(this).val())==''){
    		empty = true;
    		nickname = $(this).attr('id');
    		return false;
    	}
    	var batchId = $('#batchId_'+$(this).attr('id').split('_')[1]+' option:selected');
    	if($.inArray($(this).val()+'|'+batchId.val(), repeat)>=0){
    		exist = true;
    		nickname = $(this).attr('id');
    		return false;
    	}
    	repeat.push($(this).val()+'|'+batchId.val());
  	});
	if(empty){
		alert('姓名不能为空');
		$('#'+nickname).focus();
		return;
	}
	if(exist){
		alert('同一姓名批次号不能重复添加');
		$('#'+nickname).focus();
		return;
	}

	ajaxPost('${pageContext.request.contextPath}/sales/add_in.htm',backAdd_);
}
function backAdd_(data){
	if(data[0]['info']=='ok'){
		parent.queryData();
		if(data[0]['batchs']=='') alert('添加销售人员成功');
		else alert('添加销售人员成功，重复数据：' + data[0]['batchs']);
		parent.closeMyPop();
	}else if(data[0]['info']=='exits'){
		alert('此销售人员已经存在');
		return;
	}else{
		alert('添加销售人员失败');
	}
}
var index=1;
function addBatch(){
	$('tr[class=odd2]:last').after('<tr class="odd2" id="tr_name_'+index+'"><td width="20%"><input type="text" name="nickname" id="nickname_'+index+'" style="width:150px"/></td><td width="20%"><select name="divideRate" id="divideRate_'+index+'"><option value="10%">10%</option><option value="20%">20%</option><option value="30%">30%</option><option value="40%">40%</option><option value="50%">50%</option><option value="60%">60%</option><option value="70%">70%</option><option value="80%">80%</option><option value="90%">90%</option></select></td><td width="40%"><select name="batchId" id="batchId_'+index+'"><c:forEach items="${v}" var="batch"><option value="${batch.batchId}">${batch.batchId}</option></c:forEach></select></td><td width="20%">[<a onclick="delBatch(this);" style="color: red;cursor: pointer;">删除</a>]</td></tr>');
	jqAutocomplete('nickname_'+index);
	index++;
}
function delBatch(obj){
	$(obj).parent().parent().remove();
}
function jqAutocomplete(nid){
	$('#'+nid).autocomplete({
		serviceUrl: '${pageContext.request.contextPath}/sales/getName_in.htm',
		width: 150,
	    delimiter: /(,|;)\s*/,
	    onSelect: function(value, data){
			$('#selection').html('<img src="\/global\/flags\/small\/' + data + '.png" alt="" \/> ' + value);
    	},
	    deferRequestBy: 0,
	    params: {country: 'Yes'},
	    noCache: true
	});
}
$(function (){
	jqAutocomplete('nickname_0');
});
</script>
</head>

<body>
<div class="main">
	<div class="nav">添加销售人员</div>
	<div class="selectthings">
		<table style="text-align: center;">
			<tr class="even">
	     		<td width="20%">姓名</td>
	     		<td width="20%">提成</td>
	     		<td width="40%">批次号</td>
	     		<td width="20%">操作&nbsp;&nbsp;[<a onclick="addBatch();" style="color: red;font-size: 15px;cursor: pointer;" title="增加">+</a>]</td>
			</tr>
			<tr class="odd2" id="tr_name_0">
				<td width="20%">
	     	    	<input type="text" name="nickname" id="nickname_0" style="width:150px"/>
	     		</td>
	     		<td width="20%">
	     	    	<select name="divideRate" id="divideRate_0">
	     	    		<option value="10%">10%</option>
	     	    		<option value="20%">20%</option>
	     	    		<option value="30%">30%</option>
	     	    		<option value="40%">40%</option>
	     	    		<option value="50%">50%</option>
	     	    		<option value="60%">60%</option>
	     	    		<option value="70%">70%</option>
	     	    		<option value="80%">80%</option>
	     	    		<option value="90%">90%</option>
	     	    	</select>
	     		</td>
	     		<td width="40%">
	     			<select name="batchId" id="batchId_0">
	     	    		<c:forEach items="${v}" var="batch">
	     	    			<option value="${batch.batchId}">${batch.batchId}</option>
	     	    		</c:forEach>
	     	    	</select>
	     		</td>
	     		<td width="20%">
	     			[<a style="color: red;cursor: pointer;">删除</a>]
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