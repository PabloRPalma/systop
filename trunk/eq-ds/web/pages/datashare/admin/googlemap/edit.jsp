<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link type="text/css" href="${ctx}/styles/style.css" rel="stylesheet" />
<link type="text/css" href="${ctx}/scripts/jquery/layout/layout.css" rel="stylesheet" />

<title></title>
</head>
<body>
<div class="x-panel">
    	<div class="x-panel-header">Google Map注册密钥配置</div>
    	<div class="x-panel-body">    		
			<s:form action="save" theme="simple" id="save" validate="true" method="POST">
		   		<s:hidden name="model.id"/>
		   		<div style="margin:30px;text-align:center;">
		   		    <%@ include file="/common/messages.jsp"%>
		   		</div>
		   		<fieldset style="margin:30px;">
			    <legend>设定密钥</legend>
				<table width="100%">
                     <tr>
                        <td style="text-align:right;">Google Map注册密钥：（<font color="red">*</font>）</td>
                        <td style="text-align:left;">
                        	<s:textfield size="100" name="model.googleMapId" cssClass="required"/>
                        </td>
                     </tr>
                </table>
                </fieldset>
                <table width="100%" style="margin-bottom:10px;">
                       <tr id="canEx">
                          <td style="text-align:center;">
                            <s:submit value="保存" cssClass="button"></s:submit>&nbsp;&nbsp;
                            <s:reset value="重置" cssClass="button"></s:reset>&nbsp;&nbsp;
                          </td>
                        </tr>
                </table>
             </s:form>
    		<div>
    			<fieldset style="margin:20px;lineheight:24px;">
    				<legend><img src="${ctx}/images/exticons/icon-question.gif" align="middle"/>&nbsp;配置说明</legend>
    				&nbsp;&nbsp;&nbsp;&nbsp;使用Google Map，需要给使用Google Map API的页面所在的域名注册地图API密钥，
    				申请密钥的链接地址：<a href="http://code.google.com/intl/zh-CN/apis/maps/signup.html" target="_blank">
    				http://code.google.com/intl/zh-CN/apis/maps/signup.html</a>。
					打开申请密钥页面后，滚动到页面下方，如图所示：<br/>
					<img src="${ctx}/pages/datashare/admin/googlemap/help.gif"/><br/>
					将系统所在域名填写到“我的网址”对应的文本框中(如：http://www.earth.com)，点“生成API密钥”按钮，页面会提示登录Google账户，
					输入用户名密码登录后将看到如下结果：<br/>
					<img src="${ctx}/pages/datashare/admin/googlemap/key.gif"/><br/> 
					复制生成的key码填入此页面文本框中保存即可。(此配置涉及震中及台站分布图正常显示)
    			</fieldset>
    		</div>    	
    	</div>
  </div>
</body>
</html>