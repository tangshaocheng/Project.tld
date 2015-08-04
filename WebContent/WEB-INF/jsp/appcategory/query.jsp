<html>
<head>
<%@ include file="/WEB-INF/jsp/header.jsp"%>
<%@ page contentType="text/html; charset=utf-8"%>
<title></title>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
<link href="${pageContext.request.contextPath}/css/layout.css" rel="stylesheet" type="text/css">
<script type="text/javascript">
function ajaxQuery(){
	ajaxPost('${pageContext.request.contextPath}/appcategory/query.htm',backQuery);
}
function backQuery(data){
  var html='';
  $('#resultData').html('');
  var result=data[0]['resultList'];
  var name = $.trim($('#name').val());
  for(var i=0;i<result.length;i++){
	 var single=result[i];
	 var operation='';
	 var level=single['level'];
	 var fatherId=single['father_id'];
	 var displayStr='';
	 if(name=='') displayStr=level==3?" style='display: none;' name='"+fatherId+"'":"";
	 operation+="[<a href='javascript:view_("+single['id']+")'>预览</a>]&nbsp;&nbsp;";
	 operation+="[<a href='javascript:up_("+single['id']+")'>修改</a>]&nbsp;&nbsp;";
	 if(fatherId>0) operation+="[<a href='javascript:del_("+single['id']+")'>删除</a>]&nbsp;&nbsp;";
	 if(level==2&&name==''){
		operation+="<span style='color:blue;cursor: pointer;' onclick='javascript:zk_("+single['id']+",this)' status='1'>+ 展开</span>&nbsp;&nbsp;";
	 }
	 i%2==0?(html+='<tr class="even" '+displayStr+'>'):(html+='<tr class="odd2" '+displayStr+'>');
     html+="<td>"+single['seq']+"</td>";
     if(level==1){
    	 html+='<td>'+single['name']+'</td><td></td><td></td>';
     }else if(level==2){
    	 html+='<td></td><td>'+single['name']+'</td><td></td>';
     }else{
    	 html+='<td></td><td></td><td>'+single['name']+'</td>';
     }
     html+='<td>'+(single['pub_status']==1?'已发布':'关闭')+'</td>';
     html+='<td>'+single['remark']+'</td>';
     html+='<td>'+operation+'</td>';
     html+='</tr>';
  }
  $('#resultData').html(html);	
  ajaxPage(data);
}
function zk_(fatherId,obj){
	var status=$(obj).attr('status');
	if(status==1){
	    $('tr[name="'+fatherId+'"]').show();
	    $(obj).html('- 收起');
	    $(obj).attr('status','0');
	}else{
		$('tr[name="'+fatherId+'"]').hide();
		$(obj).html('+ 展开');
		$(obj).attr('status','1');
	}
}
function view_(){
	showDivDetail('${pageContext.request.contextPath}/appcategory/view.htm?categoryId='+arguments[0],750,500,document.body.scrollTop+10,document.body.scrollTop+10);
}
function add_(){
	showDivDetail('${pageContext.request.contextPath}/appcategory/toAdd.htm',750,500,10,10);
}
function up_(){
	showDivDetail('${pageContext.request.contextPath}/appcategory/toUp.htm?categoryId='+arguments[0],750,500,document.body.scrollTop+10,document.body.scrollLeft+10);
}
function del_(){
	if(confirm('确定删除此分类吗？'))
		ajaxPost('${pageContext.request.contextPath}/appcategory/del.htm',backDel_,{'categoryId':arguments[0]});
}
function backDel_(data){
	if(data[0]['info']=='ok'){
		alert('删除成功');
		ajaxQuery();
	}else if(data[0]['info']=='app'){
		alert('此分类和应用有关联,不允许删除');
	}else if(data[0]['info']=='sub'){
		alert('此分类已有子类,不允许删除');
	}else{
		alert('删除失败');
	}
}
$(document).ready(function (){
	$('#categoryId').change(function (){
		if($('#categoryId').val() != '') {
			ajaxPost('${pageContext.request.contextPath}/appcategory/getListByFatherId.htm',backCity,{'categoryId':$('#categoryId').val()});
		}else{
			$('#subCategoryId').empty();
			$('#subCategoryId').append('<option value="0" selected> -- 二级 -- </option>');
		}
	});
	ajaxPost('${pageContext.request.contextPath}/appcategory/getListByFatherId.htm',backCity,{'categoryId':$('#categoryId').val()});
});
function backCity(data){
	$('#subCategoryId').empty();
	$('#subCategoryId').append('<option value="0" selected> -- 二级 -- </option>');
	$.each(data,function(i,n){
		$('#subCategoryId').append('<option value='+n.id+'>'+n.name+'</option>');
    });
}
</script>
</head>
<body>
<div class="main">
<div class="nav">分类管理</div>
<div class="selectthings">
	<table>
		<tr>
        	<td width="100%">
		   		分类：<select name="categoryId" id="categoryId" style="width: 120px;">
		       			<option value="0"> -- 一级 -- </option>
		       			<c:forEach items="${categoryList}" var="category">
		          			<option value="${category.id}"<c:if test="${category.id==2}"> selected</c:if>>${category.name}</option>
		       			</c:forEach>
		   			</select>&nbsp;&nbsp;&nbsp;&nbsp;
		   			<select name="subCategoryId" id="subCategoryId" style="width: 120px;">
		        		<option value="0"> -- 二级 -- </option>
		   			</select>&nbsp;&nbsp;&nbsp;&nbsp;
		     	分类名：<input type="text" id="name" name="name" style="width:100px">&nbsp;&nbsp;&nbsp;&nbsp;
		    	发布状态：<select id="pubStatus" name="pubStatus" style="width: 100px">
		            		<option value="">全部</option>
			        		<option value="1">是</option>
			        		<option value="0">否</option>
			    		</select>&nbsp;&nbsp;&nbsp;&nbsp;
		  		<input type="button" value="查询" onclick="queryData()" class="btn">&nbsp;&nbsp;&nbsp;&nbsp;
		  		<input type="button" value="添加" onclick="add_()" class="btn">
   			</td>
		</tr>
	</table>
</div>
<div class="qdata">
    <table width="100%" class="datatable">
      <tbody>
         <tr>
           <th width="10%">序号</th>
           <th width="10%">一级分类</th>
           <th width="10%">二级分类</th>
           <th width="10%">三级分类</th>
           <th width="5%">发布状态</th>
           <th width="10%">描述</th>
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