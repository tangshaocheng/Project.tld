<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/WEB-INF/jsp/header.jsp"%>
<%@ page contentType="text/html; charset=utf-8"%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title></title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/jquery.treeview.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/jscreen.css" />
<link rel="stylesheet"  href="${pageContext.request.contextPath}/css/bill.css"  type="text/css">
<script src="${pageContext.request.contextPath}/js/jquery.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/js/jquery.treeview.js" type="text/javascript"></script>
<script>
  $(function() {
      $("#browser").treeview();
      $("a").click(function(){
    	  $("a").css("color","#0000FF");
    	  $(this).css("color","#FF0000");
      });
  });
  
  $(document).ready(function (){
	  $("li").each(function (){
		 $(this).bind("click",aclick);
	  })   
  })
  function aclick(){
	 $("li").each(function(){
		 $(this).attr("class","closed");
	 })
	  //$(this).attr("class","selected");
		 
  }
</script>
</head>
<body>
<table width="208" border="0" cellpadding="0" cellspacing="0" class="kuangnew3">
  <tr>
    <td class="title-text1"></td>
  </tr>
  <tr>
    <td>
    <table width="191" border="0" cellpadding="0" cellspacing="0">
      <tr><td valign="middle" ><table width="191" border="0" cellpadding="0" cellspacing="0">
    <tr>
      <td background="${pageContext.request.contextPath}/img/bj_1.gif" style="height:5px;"><img src="${pageContext.request.contextPath}/img/bj_1.gif" width="1" height="5" /></td>
    </tr>
    <tr>
    <td>
    <ul id="browser" class="filetree">
           <li class="open"><span class="memu-b-2">SP接入配置管理</span>
           		<ul>
                 <li class="closed"><span class="file"><a href="${pageContext.request.contextPath}/sp/toQuerySpBase.htm" target="mainFrame" class="navdimenu" >SP基本信息配置</a></span>
                </li>
                <li class="closed"><span class="file"><a href="${pageContext.request.contextPath}/sp/toQuerySpLongNum.htm" target="mainFrame" class="navdimenu" >SP长号码管理</a></span>
                </li>
                </li>
                <li class="closed"><span class="file"><a href="${pageContext.request.contextPath}/sp/toQuerySpKey.htm" target="mainFrame" class="navdimenu" >SP参数对应关系配置</a></span>
                </li>
                <li class="closed"><span class="file"><a href="${pageContext.request.contextPath}/sp/toQuerySpValue.htm" target="mainFrame" class="navdimenu" >SP值对应关系配置</a></span>
                </li>
                <li class="closed"><span class="file"><a href="${pageContext.request.contextPath}/sp/toQuerySpTypeManagement.htm" target="mainFrame" class="navdimenu" >通道类型配置管理</a></span>
                </li>
                <li class="closed"><span class="file"><a href="${pageContext.request.contextPath}/cplog/toCPLogNotify.htm" target="mainFrame" class="navdimenu" >CP日志补传</a></span>
                </li>
               </ul>
           </li>   
           <li class="open"><span class="memu-b-2">系统管理</span>
             <ul>
               <li class="closed"><span class="file"><a href="${pageContext.request.contextPath}/sys/toListDictType.htm" target="mainFrame" class="navdimenu" >参数配置</a></span>
             </ul>
           </li>
    </ul>
    </td>
    </tr>
    <tr>
      <td valign="middle" class="leftul">&nbsp;</td>
    </tr>
  </table>
        </td>
  </tr>
    </table></td>
  </tr>

</table>
</body>
</html>