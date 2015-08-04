<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/WEB-INF/jsp/header.jsp"%>
<%@ page contentType="text/html; charset=utf-8"%>
<title>AppStore计费系统</title>
<link href="${pageContext.request.contextPath}/css/new/axurerp_left.css" type="text/css" rel="stylesheet">
<script type="text/javascript">
$(function(){
	getMenuTree();
});
function getMenuTree() {
	ajaxPost("${pageContext.request.contextPath}/menutree_out.htm",buildTree);
}
function buildTree(data) {
	$("#browser").html(data[0]['tree']);
	$("div[class='u12']").hide();
	$("div[class='u10_img']").click(function(){
		var fatherid=$(this).attr("id");
		var hide=$(this).attr("hide");
		$("div[class='u12']").hide();
		$("div[class='u10_img']").each(function(){
			$(this).attr("hide","0");
			$(this).css("background-image","url(${pageContext.request.contextPath}/img/new/left_files/"+$(this).attr("id")+"_menu.png)");
		});
		if(hide=='1'){
			$(this).css("background-image","url(${pageContext.request.contextPath}/img/new/left_files/"+fatherid+"_menu.png)");
			$("div[fatherid='"+fatherid+"']").hide();
		}else{
			$(this).css("background-image","url(${pageContext.request.contextPath}/img/new/left_files/"+fatherid+"_menu_sel.png)");
			$(this).attr("hide","1");
			$("div[fatherid='"+fatherid+"']").show();
		}
	});
	$("img[class='raw_image']").click(function(){
		window.top.topFrame.funUrlViwe($(this).attr("title"));
		var id=$(this).attr("id");
		$("img[class='raw_image']").each(function(){
			$(this).attr("src","${pageContext.request.contextPath}/img/new/left_files/"+$(this).attr("id")+"_menu.png");
		});
		$(this).attr("src","${pageContext.request.contextPath}/img/new/left_files/"+id+"_menu_sel.png");
		$("div[fatherid='"+id+"']").show();
	});
}
function funUrlViwe(str){
	window.top.topFrame.funUrlViwe(str);
}
</script>
</head>
<body>
	<div id="main_container">
		<div id="u4" class="u4_container">
			<div id="u4_img" class="u4_normal detectCanvas"></div>
			<div id="u5" class="u5" style="visibility: hidden;">
				<div id="u5_rtf"></div>
			</div>
		</div>
		<span id="browser">

		</span>
		<div id="u42" class="u42">
			<div id="u42_rtf">
				<p style="text-align: left;">
					<span
						style="font-family: 宋体; font-size: 12px; font-weight: normal; font-style: normal; text-decoration: none; color: #CCCCCC;"
						id="time"> </span>
				</p>
			</div>
		</div>
		<DIV id="u43"
			style="border-style: dotted; border-color: red; visibility: hidden; position: absolute; left: 0px; top: 0px; width: 261px; height: 783px;"></div>
	</div>
</body>
</html>