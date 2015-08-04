<html>
<head>
<%@ include file="/WEB-INF/jsp/header.jsp"%>
<%@ page contentType="text/html; charset=utf-8"%>
<title></title>
<link href="${pageContext.request.contextPath}/css/layout.css" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath}/css/themes/base/jquery-ui-10.css" rel="stylesheet" type="text/css">
<script type="text/javascript">
function ajaxQuery(){
	ajaxPost('${pageContext.request.contextPath}/appstatistics/query_in.htm', backQuery);
}
function backQuery(data){
	var html = '';
	$('#resultData').html('');
   	var result = data[0]['resultList'];
  	for(var i=0; i<result.length; i++){
	 	i%2==0?html+='<tr class="even">':html+='<tr class="odd2">';
	 	html+='<td style="font-size: 8pt;"><xmp>'+(result[i]['cdate']==null?'':result[i]['cdate'])+'</xmp></td>';
	 	html+='<td style="font-size: 8pt;"><xmp>'+(result[i]['app_name']==null?'':result[i]['app_name'])+'</xmp></td>';
	 	html+='<td style="font-size: 8pt;"><xmp>'+(result[i]['count']==null?'':result[i]['count'])+'</xmp></td>';
	 	html+='<td style="font-size: 8pt;"><xmp>'+(result[i]['pname']==null?'':result[i]['pname'])+'</xmp></td>';
	 	html+='<td style="font-size: 8pt;"><xmp>'+(result[i]['batch_id']==null?'':result[i]['batch_id'])+'</xmp></td>';
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
<div class="nav">应用数据统计</div>
<div class="selectthings">
<table>
  	<tr>
		<td width="100%">
			应用：<input type="text" name="appName" style="width:150px">&nbsp;&nbsp;
	 		合作商：<input type="text" name="pname" style="width:150px">&nbsp;&nbsp;
	  		批次号：<input type="text" name="batchId" style="width:200px">&nbsp;&nbsp;
	  		日期：<input type="text" name="bdate" id="bdate" style="width:100px">&nbsp;至&nbsp;<input type="text" name="edate" id="edate" style="width:100px">&nbsp;&nbsp;
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
           <th width="20%">应用</th>
           <th width="20%">下载数</th>
           <th width="20%">合作商</th>
           <th width="20%">批次号</th>
         </tr>
       </tbody>
        <tbody id="resultData">
       </tbody>
    </table>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/ajaxPage.js"></script>
</div>
</div>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-ui.min.js"></script>
</body>
</html>