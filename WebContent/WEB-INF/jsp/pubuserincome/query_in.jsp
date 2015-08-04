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
function queryData(){
	ajaxPost('${pageContext.request.contextPath}/pubuserincome/getList_in.htm',backQuery);
}
var rlist='';
function backQuery(data){
  	var result = data['rlist'];
  	rlist = data['rlist'];
  	var html='';
  	$('#resultData').html('');
  	for(var i=0;i<result.length;i++){
	 	i%2==0?html+='<tr id="tr_'+result[i]['id']+'" class="even">':html+='<tr id="tr_'+result[i]['id']+'" class="odd2">';
	 	var total_income = toInt(result[i]['cpa_income']) + toInt(result[i]['cps_income']);
	 	var adjust_ratio = '1';
	 	var pub_income = toInt(result[i]['cpa_income']) + toInt(result[i]['cps_income']) * adjust_ratio;
     	html+='<td><input type="hidden" name="user_id" id="user_id_'+result[i]['id']+'" value="'+result[i]['user_id']+'">'+result[i]['nickname']+'</td>';
     	html+='<td><input type="hidden" name="batch_id" id="batch_id_'+result[i]['id']+'" value="'+result[i]['batch_id']+'">'+result[i]['batch_id']+'</td>';
     	html+='<td>'+toInt(result[i]['active_num'])+'</td>';
	 	html+='<td><input type="hidden" name="cpa_income" id="cpa_income_'+result[i]['id']+'" value="'+result[i]['cpa_income']+'">'+toInt(result[i]['cpa_income'])+'</td>';
	 	html+='<td><input type="hidden" name="cps_income" id="cps_income_'+result[i]['id']+'" value="'+result[i]['cps_income']+'">'+toInt(result[i]['cps_income'])+'</td>';
	 	html+='<td>'+total_income+'</td>';
	 	html+='<td><input type="hidden" name="id" id="id_'+result[i]['id']+'" value="'+result[i]['id']+'"><input type="text" name="adjust_ratio" id="adjst_ratio_'+result[i]['id']+'" value="'+adjust_ratio+'" style="width:100px" onkeyup="rangeInputFloat(this, \''+result[i]['id']+'\');" onafterpaste="rangeInputFloat(this, \''+result[i]['id']+'\');" maxLength="7"></td>';
	 	html+='<td><input type="hidden" name="pub_income" id="pub_income_'+result[i]['id']+'" value="'+pub_income+'"><span id="pub_income_span_'+result[i]['id']+'">'+pub_income+'</span></td>';
	 	html+='<td><input type="hidden" name="last_income" id="last_income_'+result[i]['id']+'" value="'+toInt(result[i]['last_income'])+'">'+toInt(result[i]['last_income'])+'</td>';
     	html+='</tr>';
  	}
  	if(result.length==0){
		$('#addBtn').hide();
  	}else{
  		$('#addBtn').show();
  	}
  	$('#resultData').html(html);
}
function add_(){
	if($.trim($('input[name=id]').val())==''){
		alert('没有可发布的收益');
		return;
	}
	var tip = '';
  	var result = eval(rlist);
	for(var i=0;i<result.length;i++){
		var pub_income = toInt($('#pub_income_'+result[i]['id']).val());
		var last_income = toInt($('#last_income_'+result[i]['id']).val()) * 3;
		if(pub_income > 0 && pub_income >= last_income ){
			tip = '发布收益大于上次发布收益3倍，';
			$('#adjst_ratio_' + result[i]['id']).focus();
			break;
		}
	}
	if(confirm(tip+'确定输入吗？')){
		$('#addBtn').hide();
		ajaxPost('${pageContext.request.contextPath}/pubuserincome/add_in.htm',backAdd_);
	}
}
function backAdd_(data){
	if(data[0]['info']=='ok'){
		queryData();
		alert('发布收益成功');
	}else if(data[0]['info']=='isPub'){
		queryData();
		alert('收益已经发布');
	}else if(data[0]['info']=='exist'){
		queryData();
		alert('原始收益已经改变');
	}else{
		alert('发布收益失败');
	}
}
function queryData_(){
	var batchId = $.trim($('#batchId').val());
	var nickname = $.trim($('#nickname').val());
	var isShow = false;
  	var result = eval(rlist);
  	for(var i=0;i<result.length;i++){
  	 	if((batchId == '' || batchId == result[i]['batch_id']) && (nickname == '' || nickname == result[i]['nickname'])){
	 		isShow = true;
	 		$('#tr_'+result[i]['id']).show();
     	}else{
    		$('#tr_'+result[i]['id']).hide();
     	}
  	}
  	if(isShow){
  		$('#addBtn').show();
  	}else{
  		$('#addBtn').hide();
  	}
}
function rangeInputFloat(oInput, id){
	checkInputFloat(oInput);
	var value = oInput.value;
	if(value == ''){
		oInput.value = 1;
	}else if(value >= 1000){
		oInput.value = 1000;
	}
	var value = oInput.value;
	if(value==1) oInput.style.backgroundColor='#FFF';
	else oInput.style.backgroundColor='#F68751';
	var point = 0;
	var index = value.indexOf('.');
	if(index != -1){
		point = value.substring(index + 1);
		if(point.length > 0 && point.length > 3){
			oInput.value = value.substring(0, index + 1) + value.substr(index + 1, 3);
		}
	}
  	var result = eval(rlist);
	for(var i=0;i<result.length;i++){
 		if(id == result[i]['id']){
		  	var adjust_ratio_ = $.trim($('#adjst_ratio_' + result[i]['id']).val());
		  	var adjust_ratio = adjust_ratio_ == '' ? '1' : adjust_ratio_;
			var pub_income = toInt(toInt(result[i]['cpa_income'] + toInt(result[i]['cps_income'])) * adjust_ratio);
			$('#pub_income_'+result[i]['id']).val(pub_income);
			$('#pub_income_span_'+result[i]['id']).html(pub_income);
		}
	}
}
function jqAutocomplete(nid){
	$('#'+nid).autocomplete({
		serviceUrl: '${pageContext.request.contextPath}/account/getName_in.htm',
		width: 150,
	    delimiter: /(,|;)\s*/,
	    onSelect: function(value, data){
			$('#selection').html('<img src="\/global\/flags\/small\/' + data + '.png" alt="" \/> ' + value);
    	},
	    deferRequestBy: 0,
	    params: {userType: 4},
	    noCache: true
	});
}
$(function (){
	jqAutocomplete('nickname');
	queryData();
});
</script>
</head>
<body>
<div class="main">
<div class="nav">发布收益</div>
<div class="selectthings">
    <input type="hidden" name="yestDate" id="yestDate" value="${yestDate}">&nbsp;&nbsp;
	<table>
    	<tr>
       		<td width="100%">
       			 批次号：<input type="text" name="batchId" id="batchId" style="width:152px">&nbsp;&nbsp;
       			合作商：<input type="text" name="nickname" id="nickname" style="width:152px">&nbsp;&nbsp;
				<input type="button" value="查询" onclick="queryData_()" class="btn">
   			</td>
		</tr>
   </table>
</div>
<div class="qdata">
    <table width="100%" class="datatable">
      <tbody>
         <tr>
           <th width="10%">合作商</th>
           <th width="20%">批次号</th>
           <th width="10%">激活量</th>
           <th width="10%">原始CPA收益(分)</th>
           <th width="10%">原始CPS收益(分)</th>
           <th width="10%">原始总收益(分)</th>
           <th width="10%">收益调整系数</th>
           <th width="10%">发布收益(分)</th>
           <th width="10%">上次发布收益(分)</th>
         </tr>
       </tbody>
       <tbody id="resultData">
       </tbody>
    </table>
</div>
<div>&nbsp;</div>
<div style="text-align: center;">
	<input type="button" id="addBtn" value="确定输入" onclick="add_()" class="btn">
</div>
</div>
</body>
</html>