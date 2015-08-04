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
function ajaxQuery(){
	ajaxPost('${pageContext.request.contextPath}/partnersincomeconf/query_in.htm',backQuery);
}
function backQuery(data){
	var html='';
  	$('#resultData').html('');
  	var result=data[0]['resultList'];
  	for(var i=0;i<result.length;i++){
	 	i%2==0?html+="<tr class='even'>":html+="<tr class='odd2'>";
	 	var operation="[<a href='javascript:up_("+result[i]['id']+","+result[i]['user_id']+")'>修改</a>]&nbsp;&nbsp;&nbsp;&nbsp;";
     	html+='<td>'+result[i]['nickname']+'</td>';
     	html+='<td>'+toInt(result[i]['cpa_price_nextday'])+'</td>';
     	html+='<td>'+toInt(result[i]['cps_divide_ratio_nextday'])+'</td>';
	 	html+='<td>'+operation+'</td>';
     	html+='</tr>';
  	}
  	$('#resultData').html(html);
  	ajaxPage(data);
}
function up_(){
	showDivDetail('${pageContext.request.contextPath}/partnersincomeconf/toUp_in.htm?id='+arguments[0]+'&userId='+arguments[1],750,300,10,10);
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
});
</script>
</head>
<body>
<div class="main">
<div class="nav">合作商收益设置</div>
<div class="selectthings">
	<table>
    	<tr>
       		<td width="100%">
       			合作商：<input type="text" name="nickname" id="nickname" style="width:152px">&nbsp;&nbsp;
				<input type="button" value="查询" onclick="queryData()" class="btn">
   			</td>
		</tr>
   </table>
</div>
<div class="qdata">
    <table width="100%" class="datatable">
      <tbody>
         <tr>
           <th width="30%">合作商</th>
           <th width="30%">CPA单价(分)</th>
           <th width="30%">CPS分成比(%)</th>
           <th width="10%">操作</th>
         </tr>
       </tbody>
       <tbody id="resultData">
       </tbody>
    </table>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/ajaxPage.js"></script>
</div>
</div>
</body>
</html>