<html>
<head>
<%@ include file="/WEB-INF/jsp/header.jsp"%>
<%@ page contentType="text/html; charset=utf-8"%>
<title></title>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
<link href="${pageContext.request.contextPath}/css/layout.css" rel="stylesheet" type="text/css">
<script type="text/javascript">
function ajaxQuery(){
	ajaxPost('${pageContext.request.contextPath}/account/query_in.htm',backQuery);
}
function backQuery(data){
   var html='';
  $('#resultData').html('');
  var result=data[0]['resultList'];
  for(var i=0;i<result.length;i++){
	 i%2==0?html+="<tr class='even'>":html+="<tr class='odd2'>";
	 var operation="[<a href='javascript:up_("+result[i]['user_id']+","+result[i]['user_type']+")'>修改</a>]&nbsp;&nbsp;&nbsp;&nbsp;";
     var userType='';
     if(result[i]['user_type']==3) userType='个人';
     if(result[i]['user_type']==4) userType='公司';
     html+='<td>'+userType+'</td>';
     html+='<td>'+result[i]['lname']+'</td>';
     html+='<td>'+result[i]['nickname']+'</td>';
     html+='<td>'+operation+'</td>';
     html+='</tr>';
  }
  $('#resultData').html(html);	
  ajaxPage(data);
}
function add_(){
	showDivDetail('${pageContext.request.contextPath}/account/toAdd_in.htm',750,600,10,10);
}
function up_(){
	showDivDetail('${pageContext.request.contextPath}/account/toUp_in.htm?userId='+arguments[0]+'&userType='+arguments[1],750,500,10,10);
}
</script>
</head>
<body>
<div class="main">
<div class="nav">账号管理</div>
<div class="selectthings">
	<table>
    	<tr>
       		<td width="100%">
       			账号：<input type="text" name="lname" style="width:150px">&nbsp;&nbsp;
       			昵称：<input type="text" name="nickname" style="width:150px">&nbsp;&nbsp;
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
           <th width="20%">账号类型</th>
           <th width="30%">账号</th>
           <th width="30%">关联用户</th>
           <th width="20%">操作</th>
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