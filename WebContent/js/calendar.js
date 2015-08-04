/**
 * 
 * 日期格式选择，可以只选择年和月，添加判断参数timeformat
 * 
 */
var months = new Array("一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月"); 
var daysInMonth = new Array(31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31); 
var days = new Array("星期日","星期一", "星期二", "星期三", "星期四", "星期五", "星期六"); 
var today; 
document.writeln("<div id='Calendar'></div>");
function getDays(month, year){ 
    //闰年计算
    if (1 == month) 
        return ((0 == year % 4) && (0 != (year % 100))) || (0 == year % 400) ? 29 : 28; 
    else 
        return daysInMonth[month]; 
} 

function getToday(){ 
    //得到今天的年,月,日 
    this.now = new Date(); 
    this.year = this.now.getFullYear(); 
    this.month = this.now.getMonth(); 
    this.day = this.now.getDate(); 
}
function newCalendar(){ 
	var hyear=document.getElementById("Year");
	var hmonth=document.getElementById("Month");
    var parseYear = parseInt(hyear.options[hyear.selectedIndex].value); 
    var newCal = new Date(parseYear, hmonth.selectedIndex, 1); 
    var day = -1; 
    var startDay = newCal.getDay(); 
    var daily = 0; 
    if ((today.year == newCal.getFullYear()) &&(today.month == newCal.getMonth())) 
        day = today.day; 
    if(timeformat!='yyyyMMdd'){
    	document.getElementById("Calendar").style.visibility="";
    	return;
    }
    var dateDivs = document.getElementById("dateDiv").getElementsByTagName("div");
    var intDaysInMonth =getDays(newCal.getMonth(), newCal.getFullYear());
    //alert("intDaysInMonth:"+intDaysInMonth);
    for (var intWeek = 1; intWeek < dateDivs.length;intWeek++){
          lis = dateDivs[intWeek].getElementsByTagName("li");
        for (var intDay = 0;intDay < lis.length;intDay++){ 
            var cell = lis[intDay];
            if ((intDay == startDay) && (0 == daily)) daily = 1;      
            if(day==daily){
                cell.style.backgroundColor='#8dcff4';
            }
            if ((daily > 0) && (daily <= intDaysInMonth)){
                if(daily*1<10)daily="0"+daily;
                cell.innerHTML = "<a  href=javascript:p('"+hyear.value + '-' + hmonth.value + '-' + daily+"')>&nbsp;&nbsp;&nbsp;&nbsp;"+daily+"&nbsp;&nbsp;&nbsp;&nbsp;</a>"; 
                daily++; 
            } 
            else 
               cell.innerText = ""; 
        }
    }
    document.getElementById("Calendar").style.visibility="";
}
var targName="";
var timeformat="yyyyMMdd";
function p(v) {
	if(""!=v)document.getElementById(targName).value=v;
	document.getElementById("Calendar").style.visibility="hidden";
	$("select").each(function(index) {
		$(this).css("display", "");
	});
}
function pyyyyMM(){
	var hyear=document.getElementById("Year");
	var hmonth=document.getElementById("Month");
	document.getElementById(targName).value=hyear.options[hyear.selectedIndex].value+"-"+hmonth.options[hmonth.selectedIndex].value;
	document.getElementById("Calendar").style.visibility="hidden";
	select_hide(false);
}
function clearCalendar(){
	document.getElementById("Calendar").style.visibility="hidden";
	document.getElementById(targName).value="";
	select_hide(false);
}

/**
 * 隐藏select标签
 */
function select_hide(isSelectHide){
	if(isSelectHide){
		$("select").each(function(index) {
			$(this).css("display", "none");
		});
	}else{
		$("select").each(function(index) {
			$(this).css("display", "");
		});
	}
}
function showCalendar(){
	select_hide(true);
    var intLoop,intWeeks,intDays;
    var DivContent;
    var year,month,day;
    var thisyear; //真正的今年年份
    targName=arguments[0];
    if(null!=arguments[1])timeformat=arguments[1];
    else timeformat="yyyyMMdd";
    thisyear=new getToday();
    thisyear=thisyear.year;
    today = new getToday();
    //下面开始输出日历表格
    DivContent="<div id='yearDiv' style='width:100%;text-align:center;padding-top:10px;'>"
    //年
    DivContent+="<select name='Year' id='Year' onChange='newCalendar()' >";
    for (intLoop = thisyear -5; intLoop < (thisyear + 20); intLoop++) 
        DivContent+="<option value= " + intLoop + " " + (today.year == intLoop ? "Selected" : "") + ">" + intLoop + "</option>"; 
    DivContent+="</select>";
    //月
    DivContent+="<select name='Month' id='Month' onChange='newCalendar()' >";
    for (intLoop = 0; intLoop < months.length; intLoop++){
       var monthValue="";
       if(intLoop<9){
           monthValue="0"+(intLoop + 1);
       }else{
           monthValue=(intLoop + 1);
       }
    
        DivContent+="<option value= " + monthValue + " " + (today.month == intLoop ? "Selected" : "") + ">" + months[intLoop] + "</option>"; 
    }
    DivContent+="</select>";
    DivContent+="&nbsp;&nbsp;&nbsp;&nbsp;[<a href=javascript:clearCalendar()>清空</a>]&nbsp;&nbsp;&nbsp;&nbsp;";
    if(timeformat!="yyyyMMdd") DivContent+="[<a href=javascript:pyyyyMM()>确定</a>]";
    DivContent+="&nbsp;&nbsp;&nbsp;&nbsp;[<a href=javascript:p('')>关闭</a>]";
    DivContent+="</div>";
    //星期
    if(timeformat=="yyyyMMdd"){
    	DivContent+="<div id='weekDiv' style='padding-top:10px;width:100%;text-align:center;text-decoration:none;list-style:none;'>";
        for (intLoop = 0; intLoop < days.length; intLoop++) 
            DivContent+="<li style='width:46px;float:left;text-align:center;'>" + days[intLoop] + "</li>"; 
        DivContent+="</div>";
    }
    //天
    if(timeformat=="yyyyMMdd"){
	    DivContent+="<div id='dateDiv' style='list-style:none;width:350px;'>"; 
	    for (intWeeks = 0; intWeeks < 8; intWeeks++){ 
	        DivContent+="<div style='list-style:none;width:350px;'>"; 
	        for (intDays = 0; intDays < days.length; intDays++) 
	               DivContent+="<li style='width:46px;float:left;text-align:center;list-style:none;'></li>"; 
	        DivContent+="</div>"; 
	    }
    }
    DivContent+="</div>"; 
    var calendar=document.getElementById("Calendar");
    calendar.innerHTML=DivContent;
    calendar.style.visibility='hidden';
    newCalendar();
    calendar.style.position="absolute";
   calendar.style.zIndex="1";
   calendar.style.background="#FFF";
   calendar.style.filter="alpha(opacity=100)";
   calendar.style.border="1px solid #8dcff4";
   calendar.style.mozOpacity="1";
   calendar.style.width="350px";
   if(timeformat=="yyyyMMdd")calendar.style.height="200px";
   else calendar.style.height="50px";
   calendar.style.textAlign="center";
   var tt=document.getElementById(targName);
   var ttop  = tt.offsetTop;  
   var thei  = tt.clientHeight;
   var tleft = tt.offsetLeft;
   var ttyp  = tt.type;
   while (tt = tt.offsetParent){ttop+=tt.offsetTop; tleft+=tt.offsetLeft;} 
   calendar.style.top=(ttyp=="image")? ttop+thei : ttop+thei+6;
   if(arguments[2]==null||arguments[2]==undefined) 
      calendar.style.left=tleft+1;
   else if(arguments[2]=="right")
      calendar.style.left=tleft+1+50;
}