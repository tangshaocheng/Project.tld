$(document).ready(function (){
	$("#_mainType").bind("click",function (){
		showDivDetail(contextPath+"/main/toQueryMainTypeForChoose.htm",600,500,20,50);
	});
	$("#_mainCfg").bind("click",function (){
		showDivDetail(contextPath+"/main/toQueryMainCfgForChoose.htm",600,500,20,50);
	});
	$("#_SMS").bind("click",function (){
		showDivDetail(contextPath+"/sms/toQuerySmsContentForChoose.htm",600,500,20,50);
	});
})
function chooseMainType_(serial){
	var ss=serial.split(",");
	var html="";
	var spanIndex=$("#_chooseMainType > span").length;
	var serialValue=$("input[name='hidden_chooseMainType']").val();
	if(serialValue!="")serialValue+=",";
	for(var i=0;i<ss.length;i++){
		if($("#spanMainType"+ss[i]).attr("id")==undefined){//加
			html+="<span style='width:160px' id='spanMainType"+ss[i]+"'>["+ss[i]+"]</span>";
			serialValue+=ss[i]+",";
			spanIndex++;
			if(spanIndex%3==0)html+="<br/>";
		}
	}
	if(serialValue.endWith(","))serialValue=serialValue.substring(0,serialValue.length-1);
	$("input[name='hidden_chooseMainType']").val(serialValue);
	$("#_chooseMainType").html($("#_chooseMainType").html()+html);
	closeMyPop();
}
function chooseMainCfg_(serial){
	var ss=serial.split(",");
	var html="";
	var spanIndex=$("#_chooseMainCfg > span").length;
	var cfgVal="";
	for(var i=0;i<ss.length;i++){
			html+="<span style='width:300px' id='spanMainCfg"+ss[i]+"'>["+ss[i]+"]</span>";
			cfgVal+=ss[i]+",";
			spanIndex++;
			if(spanIndex%3==0)html+="<br/>";
	}
	if(cfgVal.endWith(","))cfgVal=cfgVal.substring(0,cfgVal.length-1);
	$("input[name='hidden_mainCfg']").val(cfgVal);
	$("#_chooseMainCfg").html(html);
	closeMyPop();
}
$(document).ready(function (){
	if($("#provinceChoose").attr("id")!=null){
		ajaxPost(contextPath+"/province/getAjaxProvinceAll.htm",backProvince);
	} 
})
function backProvince(data){
	$("#provinceChoose").append("<option value=''"+" selected>"+"全部"+"</option>");
	    $.each(data,function(i,n){
	    $("#provinceChoose").append("<option value="+n.id+">"+n.name+"</option>");
   });
	    
}
function __chooseSpBiz(){
	showDivDetail(contextPath+"/sp/toQuerySpBizForChoose.htm?1=1&type="+arguments[0],600,500,20,50);
}
function __chooseSpBizMulti(){
	var spBiz = arguments[0].split(":");
	var id = spBiz[0];
	var value = spBiz[1];
	$("#__spBizId").val(id);
	$("#spBizHtml").html(value);
	closeMyPop();
}
function __backChooseSpBiz(){
	$("#__spBizName").val(arguments[0]);
	$("#__spBizId").val(arguments[1]);
	$("#__spBizProvince").val(arguments[2]);
	closeMyPop();
}

$(document).ready(function (){
	$("#_Serial").bind("click",function (){
		showDivDetail(contextPath+"/sp/toQueryCustomerForChoose.htm?single=false",600,500,20,50);
	});
	$("#_SingleSerial").bind("click",function (){
		showDivDetail(contextPath+"/serial/toQuerySerialForChoose.htm?single=true",600,500,20,50);
	});
	$("#_AddSerial").bind("click",function (){
		showDivDetail(contextPath+"/serial/toQuerySerialForChoose.htm?single=false&type=add",600,500,20,50);
	});
	$("#_Province").bind("click",function (){
		showDivDetail(contextPath+"/province/toQueryProvinceForChoose.htm",500,500,20,50);
	});
	$("#_Operator").bind("click",function (){
		showDivDetail(contextPath+"/operator/toQueryOperatorForChoose.htm",600,500,20,50);
	});
	$("#_Svn").bind("click",function (){
		showDivDetail(contextPath+"/svn/toQuerySvnForChoose.htm?single=false",600,500,20,50);
	});
	$("#_SingleSvn").bind("click",function (){
		showDivDetail(contextPath+"/svn/toQuerySvnForChoose.htm?single=true",600,500,20,50);
	});
});
function chooseSingleSerial_(serial){
	$("#_SingleSerial").val(serial);
	$("#serial").val(serial);
	closeMyPop();
}
function chooseSerial_(serial){
	var ss=serial.split(",");
	var html="";
	var spanIndex=$("#_chooseSerial > span").length;
	var serialValue="";
	if(serial != "") {
		$("#_chooseSerial").html("");
		for(var i=0;i<ss.length;i++){
			if($("#spanSerial"+ss[i]).attr("id")==undefined){//加
				html+="<span style='width:160px' id='spanSerial"+ss[i]+"'>["+ss[i]+"]</span>";
				serialValue+=ss[i]+",";
				spanIndex++;
				if(spanIndex%3==0)html+="<br/>";
			}
		}
	}
	if(serialValue.endWith(","))serialValue=serialValue.substring(0,serialValue.length-1);
	$("input[name='hidden_chooseSerial']").val(serialValue);
	$("#_chooseSerial").html(html);
	closeMyPop();
}
function chooseSvn_(svn){
	var ss=svn.split(",");
	var html="";
	var spanIndex=$("#_chooseSvn > span").length;
	var svnValue=$("input[name='hidden_chooseSvn']").val();
	if(svnValue!="")svnValue+=",";
	if(svn != "") {
		for(var i=0;i<ss.length;i++){
			if($("#spanSvn"+ss[i]).attr("id")==undefined){//加
				html+="<span style='width:160px' id='spanSvn"+ss[i]+"'>["+ss[i]+"]</span>";
				svnValue+=ss[i]+",";
				spanIndex++;
				if(spanIndex%3==0)html+="<br/>";
			}
		}
	}
	if(svnValue.endWith(","))svnValue=svnValue.substring(0,svnValue.length-1);
	$("input[name='hidden_chooseSvn']").val(svnValue);
	$("#_chooseSvn").html($("#_chooseSvn").html()+html);
	closeMyPop();
}
function chooseSingleSvn_(svn){
	$("#_SingleSvn").val(svn);
	$("#svn").val(svn);
	closeMyPop();
}
function chooseProvince_(serial){
	var ss=serial.split(",");
	var html="";
	var spanIndex=$("#_chooseProvince > span").length;
	var provinceValue=$("input[name='hidden_chooseProvince']").val();
	if(provinceValue!="")provinceValue+=",";
	if(serial != "") {
		for(var i=0;i<ss.length;i++){
			var province=ss[i].split(";");
			if($("#spanProvince"+province[0]).attr("id")==undefined){//加
				html+="<span style='width:100px' id='spanProvince"+province[0]+"'>["+province[1]+"]</span>";
				spanIndex++;
				if(spanIndex%5==0)html+="<br/>";
				provinceValue+=province[0]+",";
				//$("input[name='hidden_chooseProvince']").val($("input[name='hidden_chooseProvince']").val()+province[0]+",");
			}
		}
		if(provinceValue.endWith(","))provinceValue=provinceValue.substring(0,provinceValue.length-1);
		$("input[name='hidden_chooseProvince']").val(provinceValue);
		$("#_chooseProvince").html($("#_chooseProvince").html()+html);
	}
	closeMyPop();
}
function chooseFeePoint_(serial){
	var ss=serial.split(",");
	var html="";
	var spanIndex=$("#_chooseFeePoint > span").length;
	var provinceValue=$("input[name='hidden_chooseFeePoint']").val();
	if(provinceValue!="")provinceValue+=",";
	if(serial != "") {
		for(var i=0;i<ss.length;i++){
			var province=ss[i].split(";");
			if($("#spanFeePoint"+province[0]).attr("id")==undefined){//加
				html+="<span style='width:100px' id='spanFeePoint"+province[0]+"'>["+province[1]+"]</span>";
				spanIndex++;
				if(spanIndex%5==0)html+="<br/>";
				provinceValue+=province[0]+",";
				//$("input[name='hidden_chooseProvince']").val($("input[name='hidden_chooseProvince']").val()+province[0]+",");
			}
		}
		if(provinceValue.endWith(","))provinceValue=provinceValue.substring(0,provinceValue.length-1);
		$("input[name='hidden_chooseFeePoint']").val(provinceValue);
		$("#_chooseFeePoint").html($("#_chooseFeePoint").html()+html);
	}
	closeMyPop();
}

function chooseFeePointForFee_(serial){
	var ss=serial.split(",");
	var html="";
	var spanIndex=$("#_chooseFeePoint > span").length;
	var provinceValue="";
	if(serial != "") {
		for(var i=0;i<ss.length;i++){
			var province=ss[i].split(";");
			if(true){//加
				html+="<span style='width:100px' id='spanFeePoint"+province[0]+"'>["+province[1]+"]</span>";
				spanIndex++;
				if(spanIndex%5==0)html+="<br/>";
				provinceValue+=province[0]+",";
				//$("input[name='hidden_chooseProvince']").val($("input[name='hidden_chooseProvince']").val()+province[0]+",");
			}
		}
		if(provinceValue.endWith(","))provinceValue=provinceValue.substring(0,provinceValue.length-1);
		$("input[name='hidden_chooseFeePoint']").val(provinceValue);
		$("#_chooseFeePoint").html(html);
		var feePointArray=$("input[name='hidden_chooseFeePoint']").val().split(",");
		var fee="";
		for(var j=0;j<feePointArray.length;j++) {
			fee +=feePointArray[j].split("-")[0]+"*"+feePointArray[j].split("-")[1]+",";
		}
		if(fee != "") {
			fee = fee.substring(0, fee.length-1);
		}
		var content = "{\"T\":2,\"U\":[\""+$("#url").val()+"\"],\"F\":\""+fee+"\"}";
		$("#content").val(content);
		$("#size").val(mbStringLength(content));
	}
	closeMyPop();
}

function chooseSmsContent_(cindex, value){
	$("input[name='hidden_chooseSms']").val(cindex);
	$("#_chooseSms").html(value);
	closeMyPop();
}


function chooseOperator_(serial){
	var ss=serial.split(",");
	var html="";
	var spanIndex=$("#_chooseOperator > span").length;
	var provinceValue=$("input[name='hidden_chooseOperator']").val();
	if(provinceValue!="")provinceValue+=",";
	if(serial != "") {
		for(var i=0;i<ss.length;i++){
			var province=ss[i].split(";");
			if($("#spanOperator"+province[0]).attr("id")==undefined){//加
				html+="<span style='width:100px' id='spanOperator"+province[0]+"'>["+province[1]+"]</span>";
				spanIndex++;
				if(spanIndex%5==0)html+="<br/>";
				provinceValue+=province[0]+",";
				//$("input[name='hidden_chooseProvince']").val($("input[name='hidden_chooseProvince']").val()+province[0]+",");
			}
		}
		if(provinceValue.endWith(","))provinceValue=provinceValue.substring(0,provinceValue.length-1);
		$("input[name='hidden_chooseOperator']").val(provinceValue);
		$("#_chooseOperator").html($("#_chooseOperator").html()+html);
	}
	closeMyPop();
}


