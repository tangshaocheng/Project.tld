<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/WEB-INF/jsp/header.jsp"%>
<%@ page contentType="text/html; charset=utf-8"%>
<title>上海艾麒乐七助手计费系统</title>
<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/new_layout.css" />
<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/themes/base/jquery-ui-10.css" />
<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/font-awesome.min.css" />
<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/table.css" />
<script type="text/javascript">
function ajaxQuery(){
	ajaxPost('${pageContext.request.contextPath}/yesincome/query_out.htm', backQuery);
}
function backQuery(data){
	var html = '';
	$('#resultData').html('');
   	var result = data[0]['resultList'];
  	for(var i=0; i<result.length; i++){
	 	i%2==0?html+='<tr class="even">':html+='<tr class="odd2">';
	 	html+='<td style="font-size: 8pt;"><xmp>'+(result[i]['cdate']==null?'':result[i]['cdate'])+'</xmp></td>';
	 	html+='<td style="font-size: 8pt;"><xmp>'+(result[i]['batch_id']==null?'':result[i]['batch_id'])+'</xmp></td>';
	 	html+='<td style="font-size: 8pt;"><xmp>'+(result[i]['income']==null?'':result[i]['income'])+'</xmp></td>';
     	html+='</tr>';
	}
  	$('#resultData').html(html);	
  	ajaxPage(data);
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
});
</script>
</head>
<body>
	<div class="main">
		<form id="postForm" name="postForm" method="post" target="_self">
			<br/>
			<div class="formRow">
				<table width="100%" id="datatable" class="ui-widget-content" style="text-align: center;">
				   	<tr class="ui-widget-header" style="height: 35px; font-size: 13px; margin: 0 auto; font: '黑体';">
					   	<td>批次号</td>
					   	<td>
					   		<input type="text" name="batchId" id="batchId" style="width:200px" maxlength="120">
					   	</td>
					   	<td>日期</td>
					   	<td>
					   		<input type="text" name="bdate" id="bdate" style="width:200px" maxlength="120">
					   	</td>
					   	<td>至</td>
					   	<td>
					   		<input type="text" name="edate" id="edate" style="width:200px" maxlength="120">
					   	</td>
					   	<td>
					   		<input type="button" value="查&nbsp;询" onclick="queryData()" class="buttonPrev" style="width: 60px;">
					   	</td>
					</tr>
				</table>
			</div>
			<br>
			<div class="formRow">
			    <table width="100%" id="datatable" class="ui-widget-content" style="text-align: center;">
			  		<tr class="ui-widget-header" style="height: 35px; font-size: 13px; margin: 0 auto; font: '黑体';">
			        	<th width="30%">日期</th>
			           	<th width="30%">批次号</th>
			           	<th width="30%">昨日收益</th>
			    	</tr>
			   		<tbody id="resultData"></tbody>
				</table>
				<script type="text/javascript" src="${pageContext.request.contextPath}/js/ajaxPage.js"></script>
			</div>
		</form>
	</div>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-ui.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/My97DatePicker/WdatePicker.js"></script>
</body>
</html>