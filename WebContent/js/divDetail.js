document.write("<style>.tipdiv {font-family: verdana, arial, helvetica; font-size:11px;font-style:normal;font-weight:normal;color:#006AD5;background-color:gray;border:#006AD5 1px solid;padding:1px;}</style>");
document.write("<div id='myPopTip' class='tipdiv' style='position:absolute;display:none;z-index:222'></div>");
document.write("<iframe id='myDivCover' name='divCover' style='filter:alpha(opacity=75);opacity:0;position:absolute;display:none;width:100%;height:100%;_width:expression(parseInt(document.body.scrollWidth));_height:expression(document.body.scrollHeight);left:0;top:0;background-color:#EFEFF1;z-index:1;overflow: hidden;'></iframe>");
function showDivDetail(url,width,height,top,left,depiction){
	var strHTML="";
	if(depiction==undefined||depiction==null )depiction="";
	strHTML += "<table border=0 cellspacing=0 cellpadding=0  >";
	strHTML += "  <tr id='dragTr' style='cursor:move;'>";
	strHTML += "    <td id=popTipTitle>&nbsp;<b>"+depiction+"</b></td>";
	strHTML += "    <td align=right ><a href='javascript:closeMyPop()'>关闭</a></td>";
	strHTML += "  </tr>";
	strHTML += "  <tr>";
	strHTML += "   <td colspan=2 id=popTipContent><iframe src='"+url+"' frameborder=0 width='" + width + "' height='" + height + "'  id=winPopTip style='overflow-x:hidden;'></iframe></td>";
	strHTML += "  </tr>";
	strHTML += "</table>";	
	$("#myPopTip").html(strHTML);
	$("#myPopTip").css("height",height);
	$("#myPopTip").css("width",width);
	//定位
	if(top==null||left==null){
		$("#myPopTip").css("top",100+$(document).scrollTop());
		$("#myPopTip").css("left",100+$(document).scrollLeft());
	}else{
		$("#myPopTip").css("top",top);
		$("#myPopTip").css("left",left);
	}
	$("#myPopTip").css("display","");
	$("#myDivCover").css("display","");
	drag('dragTr')
}
function closeMyPop(){
	$("#myPopTip").css("display","none");
	$("#myDivCover").css("display","none");
}

var dragX=0;
var dragY=0;
var bMove=false;
function drag(objId){
	$('#'+objId).bind('mousedown',function (event){
		if(event&&event.button==1){
			bMove=true;
			event.target.setCapture();//只用于ＩＥ，不兼容FF
			var myPopTip = document.getElementById("myPopTip");
			var styleLeft=myPopTip.style.left.substring(0,myPopTip.style.left.indexOf("px"));
			var styleTop=myPopTip.style.top.substring(0,myPopTip.style.top.indexOf("px"));
			dragX=event.pageX*1-styleLeft*1;
			dragY=event.pageY*1-styleTop*1;
		}
	})
	$('#'+objId).bind('mousemove',function (event){
		if(event&&bMove&&event.button==1){
			var objStyle=document.getElementById("myPopTip").style;
			objStyle.left=(event.pageX-dragX);
			objStyle.top=(event.pageY-dragY);
		}
	})
	$('#'+objId).bind('mouseup',function (event){
			bMove=false;
			event.target.releaseCapture();//只用于ＩＥ，不兼容FF
			var objStyle=document.getElementById("myPopTip").style;
			var styleTop=objStyle.top.substring(0,objStyle.top.indexOf("px"));
			var styleLeft=objStyle.left.substring(0,objStyle.left.indexOf("px"));
			if(styleTop<0)objStyle.top=100;
			if(styleLeft<0)objStyle.left=100;
	})
}