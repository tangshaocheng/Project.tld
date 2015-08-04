<html>
<head>
<%@ include file="/WEB-INF/jsp/header.jsp"%>
<%@ page contentType="text/html; charset=utf-8"%>
<title></title>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
<link href="${pageContext.request.contextPath}/css/layout.css" rel="stylesheet" type="text/css">
<script src="${pageContext.request.contextPath}/js/jquery.dragsort-0.4.min.js"  type="text/javascript"></script>
<script type="text/javascript">
function ajaxQuery(){
	ajaxPost('${pageContext.request.contextPath}/appthemebagsortsx/query_in.htm',backQuery);
}
var indexApp=0;
function backQuery(data){
   var html='';
  $('#resultData').html('');
  //var result=data[0]['resultList'];
  var result=data['resultList'];
  for(var i=0;i<result.length;i++){
	 i%2==0?html+="<tr class='even' id='tr_td0_"+i+"'>":html+="<tr class='odd2' id='tr_td0_"+i+"'>";
     var operation="[<a href='javascript:del_("+result[i]['id']+")'>删除</a>]";
     var bagType='';
     if(result[i]['theme_bag_type']==1) bagType='有标题';
     if(result[i]['theme_bag_type']==0) bagType='无标题';
     //html+='<td><input type="text" name="sort" value="'+result[i]['sort']+'" onkeyup="value=(value==0?\'\':value.replace(/[^\\d]|_/ig,\'\'))"></td>';
     html+='<td>'+result[i]['theme_bag_id']+'<input type="hidden" name="themeBagId" alt="themeBagId" value="'+result[i]['theme_bag_id']+'"></td>';
     html+='<td>'+result[i]['theme_bag_name']+'</td>';
     html+='<td>'+bagType+'</td>';
     html+='<td>'+formateDate(result[i]['ctime']['time'],'YYYY-MM-DD HH:mm:ss')+'</td>';
     html+='<td>'+operation+'</td>';
     html+='</tr>';
     indexApp++;
  }
  $('#resultData').html(html);
  //ajaxPage(data);
}
function add_(){
	showDivDetail('${pageContext.request.contextPath}/appthemebagsortsx/toAdd_in.htm',750,600,10,10);
}
function up_(){
	showDivDetail('${pageContext.request.contextPath}/appthemebagsortsx/toUp_in.htm?id='+arguments[0],750,500,10,10);
}
function del_(){
	if(confirm('确定删除此专题吗？'))
		ajaxPost('${pageContext.request.contextPath}/appthemebagsortsx/del_in.htm',backDel_,{'id':arguments[0]});
}
function backDel_(data){
	if(data[0]['info']=='ok'){
		ajaxQuery();
	}
}
function cThemeBag(){
	showDivDetail('${pageContext.request.contextPath}/appthemebag/choose_in.htm?type=2',700,500,10,10);
}
function chooseThemeBag(){
	var ids = arguments[0].split(',');
	var names = arguments[1].split(',');
	var bagTypes = arguments[2].split(',');
		var currentHtml = $('#resultData').html();
	var html = "";
	for(var i=0;i<ids.length;i++){
		var flag = true;
		$('input[alt="themeBagId"]').each(function(){
	    	if($(this).val()==ids[i]) flag = false;
	  	});
	  	if(!flag) continue;
	    var bagType='';
	    if(bagTypes[i]==1) bagType='有标题';
	    if(bagTypes[i]==0) bagType='无标题';
	    var operation="[<a href='javascript:delete_("+indexApp+")'>删除</a>]";
		html+="<tr class='even' id='tr_td_"+indexApp+"'>";
	    	//html+='<td><input type="text" name="sort" value="" onkeyup="value=(value==0?\'\':value.replace(/[^\\d]|_/ig,\'\'))"></td>';
	    	html+='<td>'+ids[i]+"<input type='hidden' name='themeBagId' alt='themeBagId' value='"+ids[i]+"'></td>";
			html+='<td>'+names[i]+'</td>';
	    	html+='<td>'+bagType+'</td>';
	    	html+='<td></td>';
	    	html+='<td>'+operation+'</td>';
	    	html+='</tr>';
	    //$('#resultData').append(html);
		indexApp++;
	}
	$('#resultData').html(html + "" + currentHtml);
	closeMyPop();
}
function delete_(){
	$('#tr_td_'+arguments[0]).remove();
}
function sort_(){
  	var flag = true;
	var arrs = new Array();
	$("input[name='sort']").each(function(i,j){
    	if($(this).val()!='') arrs[i] = $(this).val();
    	else{
    		flag = false;
    		$(this).focus();
			return false;
    	}
  	});
  	if(!flag){
  		alert('序列号不能为空');
  		return;
  	}
  	for(var i=0;i<arrs.length;i++){
  		for(var j=i;j<arrs.length-1;j++){
			if(arrs[i]==arrs[j+1]){
				flag = false;
				break;
			}
		}
  	}
	if(!flag){
		alert('序列号不能重复');
  		return;
	}
	ajaxPost('${pageContext.request.contextPath}/appthemebagsortsx/sort_in.htm',backSort_);
}
function backSort_(data){
	if(data[0]['info']=='ok'){
		ajaxQuery();
	}
}
</script>
</head>
<body>
<div class="main">
<div class="nav">三星首页排版管理</div>
<div class="selectthings">
	<table>
    	<tr>
       		<td width="100%">
       			专题包ID：<input type="text" name="themeBagid" style="width:100px">&nbsp;&nbsp;
       			专题包名称：<input type="text" name="themeBagname" style="width:100px">&nbsp;&nbsp;
       			专题包类型：<select name="themeBagType" style="width:100px"><option value="">全部</option><option value="1">有标题</option><option value="0">无标题</option></select>&nbsp;&nbsp;
				<input type="button" value="查询" onclick="ajaxQuery()" class="btn">&nbsp;&nbsp;
			    <input type="button" value="添加专题包" onclick="cThemeBag()" class="btn">&nbsp;&nbsp;
			    <input type="button" value="保存排序" onclick="sort_()" class="btn">
   			</td>
		</tr>
   </table>
</div>
<div class="qdata">
    <table width="100%" class="datatable" id="listTable">
      <tbody>
         <tr>
           <!-- 
           <th width="8%">排序</th> -->
           <th width="5%">专题包ID</th>
           <th width="10%">专题包名称</th>
           <th width="5%">专题包类型</th>
           <th width="8%">创建时间</th>
           <th width="5%">操作</th>
         </tr>
       </tbody>
        <tbody id="resultData">
       </tbody>
    </table>
	<!-- 
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/ajaxPage.js"></script>
	 -->
</div>
</div>  
</body>
</html>
<script type="text/javascript">  
	$(document).ready(function(){
		ajaxQuery();
    	$("#resultData").dragsort({ itemSelector: "tr", dragSelector: "tr", dragBetween: true,placeHolderTemplate: "<tr></tr>" });  
    });
</script>  