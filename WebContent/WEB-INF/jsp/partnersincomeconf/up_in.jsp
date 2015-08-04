<html>
<head>
<%@ include file="/WEB-INF/jsp/header.jsp"%>
<%@ page contentType="text/html; charset=utf-8"%>
<title></title>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
<link href="${pageContext.request.contextPath}/css/layout.css" rel="stylesheet" type="text/css">
<script type="text/javascript">
function add_(){
	if($.trim($('input[name=nickname]').val()) == ''){
		alert('合作商不能为空');
		$('input[name=nickname]').focus();
		return;
	}
	var cpaPriceNextDay = parseFloat($('input[name=cpaPriceNextDay]').val());
	if(cpaPriceNextDay > 400){
		alert('CPA单价不能大于400');
		$('input[name=cpaPriceNextDay]').focus();
		return;
	}
	var cpsDivideRatioNextDay = parseFloat($('input[name=cpsDivideRatioNextDay]').val());
	if(cpsDivideRatioNextDay > 100){
		alert('CPS分成比不能大于100');
		$('input[name=cpsDivideRatioNextDay]').focus();
		return;
	}

	ajaxPost('${pageContext.request.contextPath}/partnersincomeconf/up_in.htm',backAdd_);
}
function backAdd_(data){
	if(data[0]['info']=='ok'){
		parent.queryData();
		alert('设置合作商收益成功');
		parent.closeMyPop();
	}else{
		alert('设置合作商收益失败');
	}
}
function rangeInputInt(oInput, range){
	oInput.style.backgroundColor='#D94600';
	checkInputInt(oInput);
	var value = oInput.value;
	if(value == ''){
		oInput.value = 0;
	}else if(value >= range){
		oInput.value = range;
	}
}
$(function (){
	var cpaPriceNextDay = toInt('${vo.cpaPriceNextDay}');
	var cpsDivideRatioNextDay = toInt('${vo.cpsDivideRatioNextDay}');
	$('input[name=cpaPriceNextDay]').val(cpaPriceNextDay);
	$('input[name=cpsDivideRatioNextDay]').val(cpsDivideRatioNextDay);
});
</script>
</head>

<body>
<div class="main">
	<div class="nav">修改合作商收益</div>
	<div class="selectthings">
		<input type="hidden" name="id" id="id" value="${vo.id}">
		<input type="hidden" name="userId" id="userId" value="${vo.userId}">
		<table>
			<tr class="odd2">
				<th width="25%">合作商</th>
				<td width="75%">
					<input type="text" name="nickname" value="${vo.nickname}" readonly style="width:300px" />
				</td>
	     	</tr>
	     	<tr class="odd2">
	     		<th width="25%">CPA单价(分)</th>
	     		<td width="75%">
	     	    	<input type="text" name="cpaPriceNextDay" style="width:300px" onkeyup="rangeInputInt(this, 400);" onafterpaste="rangeInputInt(this, 400);" />
	     		</td>
	     	</tr>
	     	<tr class="odd2">
	     		<th width="25%">CPS分成比(%)</th>
	     		<td width="75%">
	     	    	<input type="text" name="cpsDivideRatioNextDay" style="width:300px" onkeyup="rangeInputInt(this, 100);" onafterpaste="rangeInputInt(this, 100);" />
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