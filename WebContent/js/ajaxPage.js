//当前页
document.write("<input type='hidden' name='currentPageNum' id='currentPageNum' value='1'/>");
//每页条数
document.write("<input type='hidden' name='everyPageNum' id='everyPageNum' value='20'/>");
//共有页面数
document.write("<input type='hidden' name='totalPageNum' id='totalPageNum' value='1'/>");
document.write("<tbody id='pageT'><tr><td width='100%' align='right'>");
document.write("<span >共有[<a id='ajaxallpage'></a>]页&nbsp;&nbsp;")
document.write("<a id='ajaxallnum'></a>条数据&nbsp;&nbsp;")
document.write("当前第[<a id='ajaxcurrpage'></a>]页</span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
document.write("<span id='ajaxfpage'></span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
document.write("<span id='ajaxnpage'></span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
document.write("<span id='ajaxppage'></span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
document.write("<span id='ajaxepage'></span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
document.write("</td></tr></tbody>");
$(document).ready(function (){
	ajaxQuery();     
})

function queryData2(pn){
	$('#currentPageNum').val(pn); 
	 ajaxQuery();
}

function queryData(){
	$('#currentPageNum').val("1"); 
	 ajaxQuery();
}
function ajaxPage(data){
    var allPage=data[0]['totalPageNum']*1;
    var crrPage=data[0]['currentPageNum']*1;
    
    $("#totalPageNum").val(allPage);
    $("#ajaxallpage").html(allPage);
    $("#ajaxcurrpage").html(crrPage);
    $("#ajaxallnum").html(data[0]['totalNum']);
    if(crrPage>1&&allPage>1){
    	$("#ajaxfpage").html("<a href='#' onclick=ajaxPageJump('f')>首页</a>");
        $("#ajaxppage").html("<a href='#' onclick=ajaxPageJump('-')>上页</a>");
    }else{
    	$("#ajaxfpage").html("首页");
    	$("#ajaxppage").html("上页");
    }
   if(crrPage<allPage&&allPage>1){
	   $("#ajaxnpage").html("<a href='#' onclick=ajaxPageJump('+')>下页</a>");
	   $("#ajaxepage").html("<a href='#' onclick=ajaxPageJump('e')>末页</a>");
	}else{
		 $("#ajaxnpage").html("下页");
		 $("#ajaxepage").html("末页");
	}
   if(allPage<=1){
	   $("#ajaxfpage").html("");
	   $("#ajaxppage").html("");
	   $("#ajaxnpage").html("");
	   $("#ajaxepage").html("");
   }
}
function ajaxPageJump(){
    if(arguments[0]=='f'){
        $('#currentPageNum').val("1");     
    }
    if(arguments[0]=='e'){
        $('#currentPageNum').val($("#totalPageNum").val());     
    }
    if(arguments[0]=='-'){
        $('#currentPageNum').val($("#currentPageNum").val()*1-1);     
    }
    if(arguments[0]=='+'){
        $('#currentPageNum').val($("#currentPageNum").val()*1+1);     
    }
    ajaxQuery();
}