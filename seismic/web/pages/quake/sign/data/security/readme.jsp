<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@include file="/common/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Applet无法导出曲线图的解决办法</title>
<style type="text/css">
div{border:solid 1px #97B7E7;padding:10px}
p{text-indent:2em;font-size:14px}
span{color:#444;display:block;padding-left:1em}
.pmt{font-family:黑体}
h3{font-family:幼圆}
h4{font-family:幼圆}
img{margin-left:2em}
</style>
</head>
<body>
<div>
<h3>解决Applet曲线图无法导出文件</h3>
<h4>1.现象</h4>
<p>当数据量超过20000之后，系统会自动启用Applet（Java小应用程序）显示曲线图。此时数据导出往往无法实现，现象是右键点击曲线图，在弹出菜单中选择“另存为”没有响应。</p>
<h4>2.原因</h4>
<p>出于安全考虑，Java运行环境（JRE）不允许访问本地文件系统。</p>
<h4>3.解决方法</h4>
<p>1）找到JRE的安装位置</p>
<p>从开始菜单中打开控制面板，找到“Java（TM）控制面板”，选择“Java”选项卡，点击“查看按钮”，弹出的对话框中可以找到JRE的位置。如下图：</p>
<img src="${ctx}/images/java_policy.jpg" />
<p>2）编辑java.policy文件</p>
<p>在JRE目录下找到<em>lib\security\java.policy</em>文件，将这个文件备份。然后，用“写字板”打开这个文件，找到<span>permission java.lang.RuntimePermission "stopThread";</span>
这一行，在这一行的下面加入一行：<span>permission java.lang.RuntimePermission "modifyThread";</span>（包括分号，下同）找到<span>// allows anyone to listen on un-privileged ports</span>一行，在它下面加入两行：<span>permission java.io.FilePermission "&lt;&lt;ALL FILES&gt;&gt;", "read,write,delete";</span><span>permission java.util.PropertyPermission "user.dir", "read";</span>保存这个文件。</p>
<p><b class="pmt">提示：</b>你可以单击这个连接<a href="${ctx}/pages/quake/sign/data/security/java_policy.zip">下载已经修改好的<em>java.policy</em>文件</a>，将解压出来的<em>java.policy</em>文件复制到<em>lib\security</em>目录替换相同的文件。</p>
<p>3）重新启动浏览器</p>
<p>启动浏览器，然后重新浏览前兆数据，试试能否保存文件。</p>
<p>4）安全性的增强</p>
<p>上述操作使得Applet可以读写任何本地文件，如果您认为这影响了安全性，请将上文中<em>&lt;&lt;ALL FILES&gt;&gt;</em>改为一个固定的文件，以后将图片保存或者覆盖这个文件即可：<span>permission java.io.FilePermission "C:\lines\line.png", "read,write,delete";</span>上述文档中，代码中的标点符号均为半角。
</p>
</div>
</body>
</html>