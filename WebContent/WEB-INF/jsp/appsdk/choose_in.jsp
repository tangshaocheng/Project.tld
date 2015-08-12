<html>
<head>
<%@ include file="/WEB-INF/jsp/header.jsp"%>
<%@ page contentType="text/html; charset=utf-8"%>
<title></title>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
<link href="${pageContext.request.contextPath}/css/layout.css" rel="stylesheet" type="text/css">
<script src="${pageContext.request.contextPath}/js/querySinger.js" type="text/javascript"></script>
<script type="text/javascript">
function ajaxQuery(){
	ajaxPost('${pageContext.request.contextPath}/genbatch/query_in.htm',backQuery);
}
function backQuery(data){
   var html='';
  $('#resultData').html('');
  var result=data[0]['resultList'];
  for(var i=0;i<result.length;i++){
     		i%2==0?html+="<tr class='even'>":html+="<tr class='odd2'>";
     		 html+="<td><input type='checkbox' name='id' value='"+result[i]['name']+"' alt='"+result[i]['batch_id']+"' title='"+result[i]['remark']+"' onclick='chooseSingle();'></td>";
			html += '<td>' + result[i]['name'] + '</td>';
			html += '<td>' + result[i]['batch_id'] + '</td>';
			html += '<td>' + result[i]['remark'] + '</td>';
			html+='<td>'+formateDate(result[i]['ctime']['time'],'YYYY-MM-DD HH:mm:ss')+'</td>';
			html += '</tr>';
     
  }
  $('#resultData').html(html);	
  ajaxPage(data);
}
function selectAll(obj){
	$('input[type="checkbox"][name="id"]').each(function (){
		$(this).attr('checked', obj.checked);
	});
}
var cindex='';
var cvalue='';
var cremark='';
function chooseSing(){
	if('${param.type}'!=1) return;
	var length=0;
	$('input[type="checkbox"][name="id"]').each(function (){
		if($(this).attr('checked')){
			var index=cindex.indexOf(','+$(this).attr('value')+',');
			if(index<0) {
				cindex+=$(this).attr('value')+',';
				cvalue+=$(this).attr('alt')+',';
				cremark+=$(this).attr('title')+',';
			}
			length++;
		}
	});
	if(cindex==''){
		alert('请至少选择一个');
		return false;
	}
	parent.chooseBatch(cindex.substring(0,cindex.length-1), cvalue.substring(0,cvalue.length-1), cremark.substring(0,cremark.length-1));
}
function chooseMuti(){
	var length=0;
	$('input[name="id"]').each(function (){
		if($(this).attr('checked')){
			var index=cindex.indexOf(','+$(this).attr('value')+',');
			if(index<0) {
				cindex+=$(this).attr('value')+',';
				cvalue+=$(this).attr('alt')+',';
				cremark+=$(this).attr('title')+',';
			}
			length++;
		}
	});
	if(cindex==''){
		alert('请至少选择一个');
		return false;
	}
	if('${param.type}'==1){
		if(cindex.split(',').length>2) {
			alert('只允许选择一个');
			return;
		}
	}
	parent.chooseBatch(cindex.substring(0,cindex.length-1), cvalue.substring(0,cvalue.length-1), cremark.substring(0,cremark.length-1));
}
</script>
</head>
<body>
<div class="main">
<div class="nav">批次选择</div>
<div class="selectthings">
	<table>
     <tr>
       <td width="100%">
                          批次号：<input type="text" name="batchId" style="width:150px" onkeyup="this.value=this.value.replace(/[^0-9]/g,'')">    
		     合作商：<input type="text" name="name" style="width:100px">   
	      <input type="button" value="查询" onclick="queryData()" class="btn">&nbsp;
	      <input type="button" value="确认所选" onclick="chooseMuti()" class="btn"> 
        </td>
     </tr>
   </table>
</div>
<div class="qdata">
    <table width="100%" class="datatable">
      <tbody>
         <tr>
           <th width="10%"></th>
           <th width="10%">合作商</th>
           <th width="20%">批次号</th>
           <th width="20%">备注</th>
           <th width="10%">创建时间</th>
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