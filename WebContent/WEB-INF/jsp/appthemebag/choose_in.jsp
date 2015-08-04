<html>
<head>
<%@ include file="/WEB-INF/jsp/header.jsp"%>
<%@ page contentType="text/html; charset=utf-8"%>
<title></title>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
<link href="${pageContext.request.contextPath}/css/layout.css" rel="stylesheet" type="text/css">
<!-- 
<script src="${pageContext.request.contextPath}/js/querySinger.js" type="text/javascript"></script> -->
<script type="text/javascript">
function ajaxQuery(){
	ajaxPost('${pageContext.request.contextPath}/appthemebag/query_in.htm',backQuery);
}
function backQuery(data){
   var html='';
  $('#resultData').html('');
  var result=data[0]['resultList'];
  for(var i=0;i<result.length;i++){
	 i%2==0?html+="<tr class='even'>":html+="<tr class='odd2'>";
	 html+="<td><input type='checkbox' name='id' value='"+result[i]['id']+"' alt='"+result[i]['name']+"' title='"+result[i]['bag_type']+"' onclick='chooseSing();'></td>";
	 html+='<td>'+result[i]['id']+'</td>';
     html+='<td>'+result[i]['name']+'</td>';
     html+='<td>'+(result[i]['bag_type']==1?'有标题':'无标题')+'</td>';
     html+='<td>'+formateDate(result[i]['ctime']['time'],'YYYY-MM-DD HH:mm:ss')+'</td>';
     html+='</tr>';
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
var cbagType='';
function chooseSing(){
	if('${param.type}'!=1) return;
	var length=0;
	$('input[type="checkbox"][name="id"]').each(function (){
		if($(this).attr('checked')){
			var index=cindex.indexOf(','+$(this).attr('value')+',');
			if(index<0) {
				cindex+=$(this).attr('value')+',';
				cvalue+=$(this).attr('alt')+',';
				cbagType+=$(this).attr('title')+',';
			}
			length++;
		}
	});
	if(cindex==''){
		alert('请至少选择一个');
		return false;
	}
	parent.chooseThemeBag(cindex.substring(0,cindex.length-1), cvalue.substring(0,cvalue.length-1), cbagType.substring(0,cbagType.length-1));
}
function chooseMuti(){
	var length=0;
	$('input[name="id"]').each(function (){
		if($(this).attr('checked')){
			var index=cindex.indexOf(','+$(this).attr('value')+',');
			if(index<0) {
				cindex+=$(this).attr('value')+',';
				cvalue+=$(this).attr('alt')+',';
				cbagType+=$(this).attr('title')+',';
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
	parent.chooseThemeBag(cindex.substring(0,cindex.length-1), cvalue.substring(0,cvalue.length-1), cbagType.substring(0,cbagType.length-1));
}
</script>
</head>
<body>
<div class="main">
<div class="nav">专题包选择</div>
<div class="selectthings">
	<table>
     <tr>
       <td width="100%">
                          专题包ID：<input type="text" name="id" style="width:50px" onkeyup="this.value=this.value.replace(/[^0-9]/g,'')">    
		     专题包名称：<input type="text" name="name" style="width:100px">   
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
           <th width="10%"><c:if test="${param.type ne 1}"><input type="checkbox" name="all" onclick="selectAll(this)">全选</c:if></th>
           <th width="10%">专题包ID</th>
           <th width="20%">专题包名称</th>
           <th width="10%">专题包类型</th>
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