<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp" %>
<html>
<head>
<%@include file="/common/meta.jsp" %>
<%@include file="/common/extSkin.jsp" %>
<title>河北省文化厅公共服务平台</title>
<link href="${ctx}\tmpRes\index\css.css" rel="stylesheet" type="text/css" />
<script src="${ctx}\tmpRes\Scripts\AC_RunActiveContent.js" type="text/javascript"></script>
</head>
<body>
<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td><script type="text/javascript">
AC_FL_RunContent( 'codebase','http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=9,0,28,0','width','100%','height','123','src','${ctx}/tmpRes/index/images/top1','quality','high','pluginspage','http://www.adobe.com/shockwave/download/download.cgi?P1_Prod_Version=ShockwaveFlash','movie','${ctx}/tmpRes/index/images/top1' ); //end AC code
</script>
        <noscript>
          <object classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000" codebase="http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=9,0,28,0" width="100%" height="123">
          <param name="movie" value="${ctx}\tmpRes\index\images\top1.swf" />
          <param name="quality" value="high" />
          <embed src="${ctx}\tmpRes\index\images/top1.swf" quality="high" pluginspage="http://www.adobe.com/shockwave/download/download.cgi?P1_Prod_Version=ShockwaveFlash" type="application/x-shockwave-flash" width="100%" height="123"></embed>
        </object>
        </noscript></td>
  </tr>
      </table>
<br>
<br>
<br>
<br>
<br>
<br>

                       <table width="90%" border="0" cellspacing="1" cellpadding="3" bgcolor="#999999" align="center">
    <tr bgcolor="#F7F7F7"> 
      <td height="28" colspan="2" align="center" bgcolor="#FFFFCC" class="textm"><strong>详 细 信 息 </strong></td>
    </tr>
    <tr bgcolor="#F7F7F7"> 
      <td width="103" height="25" align="center"><strong>标　　题</strong></td>

      <td width="760" height="25">&nbsp; <s:property value="model.title" /> </td>
    </tr>
    <tr bgcolor="#F7F7F7"> 
     <td align="center" height="25"><strong>内　　容</strong></td>
      <td width="760" height="25" style="padding:8px">${model.content}</td>
    </tr>
    <tr bgcolor="#F7F7F7">
      <td align="center" height="25"><strong>发布人</strong></td>
      <td height="25" style="padding:8px"><s:property value="model.author" /></td>
    </tr>
        <tr bgcolor="#F7F7F7">
      <td align="center" height="25"><strong>发布时间</strong></td>
      <td height="25">&nbsp;<s:property value="model.creatDate" /></td>
    </tr>  
     <tr bgcolor="#F7F7F7">
      <td align="center" height="25"><strong>有效天数</strong></td>
      <td height="25" style="padding:8px"><s:property value="model.outTime" /></td>
    </tr>  <tr bgcolor="#F7F7F7"> 
      <td colspan="2" align="center" height="28"><input type="button" class="button" value="关闭" onclick="javascript:window.close(1)"></td>
    </tr>
</table>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>

<table width="97%" border="0" align="center" cellpadding="0" cellspacing="0">
<tr>
    <td height="89" colspan="2" align="center" background="${ctx}/tmpRes/index/images/index_94.jpg"  style="line-height:19px; padding-top:4px
   " scope="row"><a href="#" class="normal">本站声明</a> 　| 　<a href="#" class="normal">广告服务</a>　|　<a href="#" class="normal">ＲＳＳ信息</a>　|　<a href="#" class="normal">关于我们</a>　| <a href="${ctx}/login.jsp" class="normal">网站管理</a> | <br />
      版权所有：河北省文化厅　冀ICP备ＸＸＸ号<br />
    开发制作单位：河北新龙科技　</td>
  </tr>
</table>
</body>
</html>
