<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/WEB-INF/jsp/header.jsp"%>
<%@ page contentType="text/html; charset=utf-8"%>
<title>AppStore计费系统</title>
<link href="${pageContext.request.contextPath}/css/new/axurerp_top.css" type="text/css" rel="stylesheet">
<script type="text/javascript">
function logout() {
	document.form1.submit();
}
function funUrlViwe(str){
	$("#urlName").html(str);
}
</script>
</head>
<body>
	<div id="main_container">

		<div id="u0" class="u0_container">
			<div id="u0_img" class="u0_normal detectCanvas"></div>
		</div>
		
		<div id="u0_right" class="u0_right_container">
			<div id="u0_right_img" class="u0_right_normal">
            </div>
		</div>
		
		<div id="u2" class="u2_container">
			<div id="u2_img" class="u2_normal detectCanvas"></div>
		</div>
		<div id="u4" class="u4_container">
			<div id="u4_img" class="u4_normal" onclick="javascript:logout();">
				<span
					style="font-family: 宋体; font-size: 12px; color: #FFFFFF; "
					onclick="javascript:logout();"> 退出 </span>
				<form name="form1" action="${pageContext.request.contextPath}/outlogin_out.htm" target="_top" method="post" style="display: none;"></form>
            </div>
		</div>
		<div id="u6" class="u6">
			<div id="u6_rtf">
				<span style="font-family: 微软雅黑; font-size: 18px; font-weight: normal; font-style: normal; text-decoration: none; color: #FFFFFF;">
				  ${energyUserBms.lname}
				</span>
			</div>
		</div>
		<div id="u7" class="u7">
			<div id="u7_rtf">
				<span style="font-family: 微软雅黑; font-size: 28px; font-weight: normal; font-style: normal; text-decoration: none; color: #FFFFFF;">
					上海艾麒乐七助手计费系统
				</span>
			</div>
		</div>
		<div id="u9" class="u9">
			<div id="u9_rtf">
				<span style="font-family: 宋体; font-size: 12px; font-weight: normal; font-style: normal; text-decoration: none; color: #CCCCCC;"
				id="time"> </span>
			</div>
		</div>
		<div id="u10" class="u10">
			<div id="u10_rtf">
				<span style="font-family: 宋体; font-size: 12px; font-weight: normal; font-style: normal; text-decoration: none; color: #999999;">首页</span><span
						style="font-family: 宋体; font-size: 12px; font-weight: normal; font-style: normal; text-decoration: none; color: #CCCCCC;">
						/ <span id="urlName"></span>
				</span>
			</div>
		</div>
		<div id="u11" class="u11">
			<div id="u11_rtf">
				<span style="font-family: 宋体; font-size: 12px; font-weight: normal; font-style: normal; text-decoration: none; color: #FFFFFF;">注册用户:<span
						id="simMobileCount"></span>
				</span>
			</div>
		</div>
		<DIV id="u12"
			style="border-style: dotted; border-color: red; visibility: hidden; position: absolute; left: 0px; top: 0px; width: 1280px; height: 99px;"></div>
	</div>
</body>
<script type="text/javascript">
	function showDT() {
		var currentDT = new Date();
		var y, m, date, day, hs, ms, ss, theDateStr;
		y = currentDT.getFullYear(); //四位整数表示的年份  
		m = currentDT.getMonth()+1; //月  
		date = currentDT.getDate(); //日  
		day = currentDT.getDay(); //星期  
		hs = currentDT.getHours() < 10 ? "0" + currentDT.getHours() : currentDT.getHours(); //时  
		ms = currentDT.getMinutes() < 10 ? "0" + currentDT.getMinutes(): currentDT.getMinutes(); //分  
		ss = currentDT.getSeconds() < 10 ? "0" + currentDT.getSeconds(): currentDT.getSeconds(); //秒  
		theDateStr = "今天是 " + y + "年" + m + "月" + date + "日 星期"+ ('日一二三四五六'.charAt(day)) + " " + hs + ":" + ms + ":" + ss;
		document.getElementById("time").innerHTML = theDateStr;
		// setTimeout 在执行时,是在载入后延迟指定时间后,去执行一次表达式,仅执行一次  
		window.setTimeout(showDT, 1000);
	}
	showDT();
</script>
</html>
