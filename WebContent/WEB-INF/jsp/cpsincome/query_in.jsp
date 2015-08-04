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
	ajaxPost('${pageContext.request.contextPath}/cpsincome/query_in.htm',backQuery);
}
var rlist='';
function backQuery(data){
  	var result = data['rlist'];
  	rlist = data['rlist'];
  	var html='';
  	$('#resultData').html('');
	for(var i=0;i<result.length;i++){
	 	i%2==0?html+='<tr id="tr_'+result[i]['id']+'" class="even">':html+='<tr id="tr_'+result[i]['id']+'" class="odd2">';
	 	var cps_income = '';
	 	var cps_income_hidden = '-1';
	 	if(result[i]['pub_status']=='1' || result[i]['pub_status']=='-1'){
	 		cps_income = result[i]['cps_income'];
	 	}
     	html+='<td><input type="hidden" name="id" id="id_'+result[i]['id']+'" value="'+result[i]['id']+'">'+result[i]['nickname']+'</td>';
     	html+='<td><input type="hidden" name="language_id" value="'+result[i]['language']+'"><input type="hidden" name="batch_id" value="'+result[i]['batch_id']+'">'+result[i]['batch_id']+'</td>';
     	html+='<td><input type="hidden" name="app_id" value="'+result[i]['app_id']+'"><input type="hidden" name="user_id" value="'+result[i]['user_id']+'"><input type="hidden" name="down_num" value="'+result[i]['down_num']+'"><input type="hidden" name="total_num" value="'+result[i]['total_num']+'"><input type="hidden" name="cps_divide_ratio" value="'+result[i]['cps_divide_ratio']+'">'+result[i]['down_num']+'</td>';
     	html+='<td><input type="hidden" name="cps_income" id="cps_income_'+result[i]['id']+'" value="'+cps_income_hidden+'"><span id="cps_income_span_'+result[i]['id']+'">'+cps_income+'</span></td>';
     	html+='</tr>';
  	}
  	$('#addBtn').hide();
  	$('#resultData').html(html);
}
function add_(){
	if($.trim($('input[name=rincome]').val())==''){
		alert('请输入CPS收益');
		$('input[name=rincome]').focus();
		return;
	}
	if(confirm('确定输入吗？')){
		$('#addBtn').hide();
		ajaxPost('${pageContext.request.contextPath}/cpsincome/add_in.htm',backAdd_);
	}
}
function backAdd_(data){
	if(data[0]['info']=='ok'){
		queryData();
		alert('设置CPS收益成功');
	}else if(data[0]['info']=='isPub'){
		queryData();
		alert('收益已经发布');
	}else if(data[0]['info']=='exist'){
		queryData();
		alert('CPS收益已经设置');
	}else{
		alert('设置CPS收益失败');
	}
}
function cApp(){
	showDivDetail('${pageContext.request.contextPath}/app/choose_in.htm?type=1&language=1',750,500,10,10);
}
var indexApp=1;
function chooseApp(){
	var id = arguments[0];
	var name = arguments[1];
	var language = arguments[3];
	$('#appId').val(id);
	$('#appBtn').val(name);
	$('#language').val(language);
	queryData_();
	closeMyPop();
}
function queryData_(){
	var appId = $('#appId').val();
	var language = $('#language').val();
  	var rincome = $.trim($('#rincome').val());
	var batchId = $.trim($('#batchId').val());
	var nickname = $.trim($('#nickname').val());
	var isShow = false;
  	var result = eval(rlist);
  	for(var i=0;i<result.length;i++){
  	 	if(language == result[i]['language'] && (appId == '' || appId == result[i]['app_id']) && (batchId == '' || batchId == result[i]['batch_id']) && (nickname == '' || nickname == result[i]['nickname'])){
	 		isShow = true;
	 		$('#tr_'+result[i]['id']).show();
	 		$('#lastDate_span').html(result[i]['last_date']);
     	}else{
    		$('#tr_'+result[i]['id']).hide();
     	}
  	}
  	if(appId != '' && isShow){
  		$('#addBtn').show();
  	}else{
  		$('#addBtn').hide();
  	}
}
function checkCps(oInput){
	checkInputInt0(oInput);
	var appId = $('#appId').val();
	var rincome = oInput.value;
	if(rincome=='') oInput.style.backgroundColor='#FFF';
	else oInput.style.backgroundColor='#F68751';
	var result = eval(rlist);
  	for(var i=0;i<result.length;i++){
	 	var cps_income = '';
  	 	var cps_income_hidden = '-1';
		if(rincome != ''){
			cps_income = cps_income_hidden = toInt(rincome * result[i]['down_num'] / result[i]['total_num'] * result[i]['cps_divide_ratio'] / 100);
		}
		$('#cps_income_'+result[i]['id']).val(cps_income_hidden);
		$('#cps_income_span_'+result[i]['id']).html(cps_income);
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
<div class="nav">设置CPS收益</div>
<div class="selectthings">
	<table>
		<tr>
       		<td width="100%">
       			<div id="appList" style="float: left; background-color: #E5EAF2;">应用列表：<font color="red">*</font><input type="hidden" name="appId" id="appId"><input type="hidden" name="language" id="language">&nbsp;<input type="button" id="appBtn" value="请选择" class="btn" onclick="cApp()">&nbsp;</div>&nbsp;&nbsp;
       			上次结算日期：<span id="lastDate_span" style="color: #844200;font-size: 13px;font-weight: bold;"></span>&nbsp;&nbsp;至
       			昨天日期：<span style="color: #844200;font-size: 13px;font-weight: bold;">${yestDate}</span><input type="hidden" name="yestDate" id="yestDate" value="${yestDate}">&nbsp;&nbsp;
			          批次号：<input type="text" name="batchId" id="batchId" style="width:152px">&nbsp;&nbsp;
       			合作商：<input type="text" name="nickname" id="nickname" style="width:152px">&nbsp;&nbsp;
       			<input type="button" value="查询" onclick="queryData_()" class="btn">
   			</td>
		</tr>
    	<tr>
       		<td width="100%">
       			CPS收益：<input type="text" name="rincome" id="rincome" style="width:152px" onkeyup="checkCps(this);" onafterpaste="checkCps(this);" maxlength="8">&nbsp;分&nbsp;&nbsp;
				
   			</td>
		</tr>
   </table>
</div>
<div class="qdata">
    <table width="100%" class="datatable">
      <tbody>
         <tr>
           <th width="30%">合作商</th>
           <th width="30%">批次号</th>
           <th width="15%">下载量</th>
           <th width="15%">原始收益(分)</th>
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