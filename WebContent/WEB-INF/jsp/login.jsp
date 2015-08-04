<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/WEB-INF/jsp/header.jsp"%>
<%@ page contentType="text/html; charset=utf-8"%>
<title>AppStore计费系统</title>
<link rel="stylesheet" type="text/css" media="all" href="${pageContext.request.contextPath}/css/login.css" />
<link rel="stylesheet" type="text/css" media="all" href="${pageContext.request.contextPath}/css/demo.css" />
<script type="text/javascript">
$(function(){

	$('.i-text').focus(function(){
		$(this).addClass('h-light');
	});

	$('.i-text').focusout(function(){
		$(this).removeClass('h-light');
	});

	$('#username').focus(function(){
	 	var value = $(this).val();
	 	if(value=='输入账号'){
	 		$(this).val('');
	 	}
	});

	$('#password').focus(function(){
	 	var value = $(this).val();
	 	if(value=='输入密码'){
	 		$(this).val('');
	 	}
	});

	$('#yzm').focus(function(){
	 	var value = $(this).val();
	 	if(value=='输入验证码'){
	 		$(this).val('');
	 	}
	});

	var loginCount = 0;

	$('.registerform').Validform({
		tiptype:function(msg, o, cssctl){
			var objtip=$('.error-box');
			cssctl(objtip, o.type);
			loginCount++;
			$('#loginCount').val(loginCount);
			if(msg == 'ok'){
				document.location.href = '${pageContext.request.contextPath}/main_out.htm';
	        }else if(msg == 'isexist'){
	     	   	objtip.text('用户名或密码错误！');
	     	   	document.getElementById('imgVarifyCode').src = document.getElementById('imgVarifyCode').src+'?time='+new Date().getTime();
	        }else if(msg == 'isactive'){
	     	   	objtip.text('帐号尚未激活！');
	     	   	document.getElementById('imgVarifyCode').src = document.getElementById('imgVarifyCode').src+'?time='+new Date().getTime();
	        }else if(msg == 'iscount'){
	        	objtip.text('输错验证码超过3次！');
	        	document.getElementById('imgVarifyCode').src = document.getElementById('imgVarifyCode').src+'?time='+new Date().getTime();
	        }else if(msg == 'isverify'){
	        	objtip.text('验证码错误！');
	        	$('#yzm').val('');
	        	document.getElementById('imgVarifyCode').src = document.getElementById('imgVarifyCode').src+'?time='+new Date().getTime();
	        }else{
	     	  	objtip.text(msg);
	        }
		},
		ajaxPost:true
	});

});
</script>
</head>
<body>
	<div class="header">
	  <h1 class="headerLogo"><a title="后台管理系统" href="javascript:void(0);"><img alt="logo" src="img/logo.gif"></a></h1>
		<div class="headerNav">
			<a href="http://wpay.joy7.cn" target="_blank">企业官网</a>
			<a href="http://wpay.joy7.cn/?p=14" target="_blank">联系我们</a>
			<a href="javascript:void(0);">开发团队</a>
			<a href="javascript:void(0);">意见反馈</a>
			<a href="javascript:void(0);">帮助</a>
		</div>
	</div>
	<div class="banner">
		<div class="login-aside">
	  		<div id="o-box-up"></div>
	  		<div id="o-box-down"  style="table-layout:fixed;">
	   			<form action="${pageContext.request.contextPath}/login_out.htm" class="registerform">
				   	<input type="hidden" name="loginCount" id="loginCount" value="0">
				   	<div class="error-box"></div>
				   	<div class="fm-item">
						<label for="logonId" class="form-label">MISS系统登陆：</label>
					   	<input type="text" value="" maxlength="100" id="username" name="lname" class="i-text" datatype="*1-18" nullmsg="请输入账号！" errormsg="账号范围在1~18位之间！">    
				       	<div class="ui-form-explain"></div>
				  	</div>
  					<div class="fm-item">
	   					<label for="logonId" class="form-label">登陆密码：</label>
	   					<input type="password" value="" maxlength="100" id="password" name="lps" class="i-text" datatype="*1-18" nullmsg="请输入密码！" errormsg="密码范围在1~18位之间！">    
       					<div class="ui-form-explain"></div>
  					</div>
  					<div class="fm-item pos-r">
	   					<label for="logonId" class="form-label">验证码</label>
	   					<input type="text" value="" maxlength="100" id="yzm" name="verifyCode" class="i-text" style="width:115px;" datatype="*1-4" nullmsg="请输入验证码！" errormsg="验证码范围在1~4位之间！">    
       					<div class="ui-form-explain">
       						<img alt="点击刷新验证码" id="imgVarifyCode" src="${pageContext.request.contextPath}/verifycode_out.htm" class="yzm-img" style="margin-left:10px;cursor: pointer;" onclick="this.src=this.src+'?'" />
       					</div>
  					</div>
					<div class="fm-item">
						<label for="logonId" class="form-label"></label>
						<input type="submit" value="" tabindex="4" id="send-btn" class="btn-login"> 
					 	<div class="ui-form-explain"></div>
	  				</div>
	  			</form>
	  		</div>
		</div>
		<div class="bd">
			<ul>
				<li style="background:url(img/theme-pic1.jpg) #CCE1F3 center 0 no-repeat;"></li>
				<li style="background:url(img/theme-pic2.jpg) #BCE0FF center 0 no-repeat;"></li>
			</ul>
		</div>
		<div class="hd"><ul></ul></div>
	</div>
	<script type="text/javascript">jQuery(".banner").slide({ titCell:".hd ul", mainCell:".bd ul", effect:"fold",  autoPlay:true, autoPage:true, trigger:"click" });</script>
	<div class="banner-shadow"></div>
	<div class="footer">
		<p>Copyright &copy; 2014.上海艾麒信息科技有限公司 All rights reserved.</p>
	</div>
</body>
</html>