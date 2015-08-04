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
	ajaxPost('${pageContext.request.contextPath}/sales/query_in.htm',backQuery);
}
function backQuery(data){
   var html='';
  $('#resultData').html('');
  var result=data[0]['resultList'];
  for(var i=0;i<result.length;i++){
	 i%2==0?html+="<tr class='even'>":html+="<tr class='odd2'>";
	 var operation="[<a href='javascript:del_("+result[i]['id']+","+result[i]['user_id']+")'>删除</a>]";
     html+='<td>'+result[i]['nickname']+'</td>';
     html+='<td>'+result[i]['divide_rate']+'</td>';
     html+='<td>'+result[i]['batch_id']+'</td>';
     html+='<td>'+operation+'</td>';
     html+='</tr>';
  }
  $('#resultData').html(html);	
  ajaxPage(data);
}
function add_(){
	showDivDetail('${pageContext.request.contextPath}/sales/toAdd_in.htm',750,600,10,10);
}
function del_(){
	if(confirm('确定删除此销售设置吗？'))
		ajaxPost('${pageContext.request.contextPath}/sales/del_in.htm',backDel_,{'id':arguments[0], 'userId':arguments[1]});
}
function backDel_(data){
	if(data[0]['info']=='ok'){
		ajaxQuery();
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
	    params: {userType: 3},
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
<div class="nav">销售设置</div>
<div class="selectthings">
	<table>
    	<tr>
       		<td width="100%">
       			姓名：<input type="text" name="nickname" id="nickname" style="width:200px">&nbsp;&nbsp;
       			批次号：<input type="text" name="batchId" style="width:200px">&nbsp;&nbsp;
				<input type="button" value="查询" onclick="queryData()" class="btn">&nbsp;&nbsp;
			    <input type="button" value="添加" onclick="add_()" class="btn">
   			</td>
		</tr>
   </table>
</div>
<div class="qdata">
    <table width="100%" class="datatable">
      <tbody>
         <tr>
           <th width="25%">姓名</th>
           <th width="25%">提成</th>
           <th width="40%">批次号</th>
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