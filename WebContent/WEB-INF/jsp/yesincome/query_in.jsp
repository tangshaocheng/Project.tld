<html>
<head>
<%@ include file="/WEB-INF/jsp/header.jsp"%>
<%@ page contentType="text/html; charset=utf-8"%>
<title></title>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
<link href="${pageContext.request.contextPath}/css/layout.css" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath}/css/themes/base/jquery-ui-10.css" rel="stylesheet" type="text/css">
<script type="text/javascript">
function queryData(){
	ajaxPost('${pageContext.request.contextPath}/yesincome/query_in.htm',backQuery);
}
function backQuery(data){
  var html='';
  $('#resultData').html('');
  var result = data;
  for(var i=0;i<result.length;i++){
	 i%2==0?html+="<tr class='even'>":html+="<tr class='odd2'>";
     html+='<td>'+result[i]['cdate']+'</td>';
     html+='<td>'+result[i]['nickname']+'</td>';
     html+='<td>'+result[i]['batch_id']+'</td>';
     html+='<td>'+result[i]['income']+'</td>';
     html+='</tr>';
  }
  $('#resultData').html(html);
}
function add_(){
	if($.trim($('input[name=rincome]').val())==''){
		alert('请输入昨天收入');
		$('input[name=rincome]').focus();
		return;
	}

	ajaxPost('${pageContext.request.contextPath}/yesincome/add_in.htm',backAdd_);
}
function backAdd_(data){
	if(data[0]['info']=='ok'){
		queryData();
		alert('昨日收益设置成功');
	}else if(data[0]['info']=='exist'){
		alert('昨日收益已经设置，不能重复设置');
	}else{
		alert('昨日收益设置失败');
	}
}
/**
 * 日期联动
 */
function cDate(){
	var hdates = $('#bdate, #edate').datepicker({
		dateFormat: 'yy-mm-dd',
		defaultDate: '-1d',
		maxDate: '-1d',
		onSelect: function(selectedDate){
			var option = this.id == 'bdate' ? 'minDate' : 'maxDate',
			instance = $(this).data('datepicker'),
			date = $.datepicker.parseDate(instance.settings.dateFormat || $.datepicker._defaults.dateFormat, selectedDate, instance.settings);
			var dateStr = date.getFullYear()+'-'+(date.getMonth()+1)+'-'+date.getDate();
			var curDate = new Date(new Date().getTime()-1*86400000);
			var curDateStr = curDate.getFullYear()+'-'+(curDate.getMonth()+1)+'-'+curDate.getDate();
			curDate = new Date(curDate.getFullYear(), (curDate.getMonth()), curDate.getDate());
			if(this.id == 'bdate'){
				$('#edate').datepicker('option', 'minDate', selectedDate);
				var formatedDate = new Date(date.getTime()+31*86400000);
	        	if(formatedDate<curDate){
	        		$('#edate').datepicker('option', 'maxDate', formatedDate);
				}else{
					$('#edate').datepicker('option', 'maxDate', curDate);
				}
			}
			if(this.id == 'edate'){
				var formatedDate = new Date(date.getTime()-31*86400000);
				$('#bdate').datepicker('option', 'minDate', formatedDate);
	        	$('#bdate').datepicker('option', 'maxDate', selectedDate);
			}
		}
	});
}
/**
 * init
 */
$(document).ready(function(){
	cDate();
	queryData();
});
</script>
</head>
<body>
<div class="main">
<div class="nav">设置昨日收益</div>
<div class="selectthings">
	<table>
		<tr>
       		<td width="100%">
       			昨天日期：<span style="color: #844200;font-size: 13px;font-weight: bold;">${yDate}</span>&nbsp;&nbsp;
       			昨天收入：<input type="text" name="rincome" style="width:150px" onkeyup="value=(value=='0'?'0':value.replace(/[^\d|^\.]|_/ig,''))" onafterpaste="this.value=this.value.replace(/\D/g,'')">&nbsp;元&nbsp;&nbsp;
			    <input type="button" value="确定输入" onclick="add_()" class="btn">
   			</td>
		</tr>
    	<tr>
       		<td width="100%">
       			批次号：<input type="text" name="batchId" style="width:200px">&nbsp;&nbsp;
       			日期：<input type="text" name="bdate" id="bdate" style="width:140px">&nbsp;至&nbsp;<input type="text" name="edate" id="edate" style="width:140px">&nbsp;&nbsp;
				<input type="button" value="查询" onclick="queryData()" class="btn">
   			</td>
		</tr>
   </table>
</div>
<div class="qdata">
    <table width="100%" class="datatable">
      <tbody>
         <tr>
           <th width="20%">日期</th>
           <th width="30%">关联用户</th>
           <th width="30%">批次号</th>
           <th width="20%">昨日收益</th>
         </tr>
       </tbody>
        <tbody id="resultData">
       </tbody>
    </table>
</div>
</div>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-ui.min.js"></script>
</body>
</html>