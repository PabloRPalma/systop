<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@include file="/common/meta.jsp" %>
<%@include file="/common/validator.jsp" %>
<script type="text/javascript">
$(document).ready(function() {
	$("#save").validate();
	//初始化前兆schema缺省值
	if($('#qzSchema').val() == '') {
	  $('#qzSchema').val('QZDATA');
	}
	
	 if($('#czType').val()=='cz_mysql') {
	   $('#czSchema').attr('readonly', 'readonly');
	   $('#czSchema').val($('#czIns').val());
	 }
	//初始化测震schema缺省值
	$('#czType').change(function() { //如果是oracle，则缺省值为CZDATA
	   if($('#czType').val()=='cz_oracle') {
	       $('#czSchema').attr('readonly', '');
	       if($('#czSchema').val() == '') {
	           $('#czSchema').val('CZDATA');
	       }
	       if($('#czPort').val() != '1521') {
	         $('#czPort').val('1521');
	       }
	   }
	   if($('#czType').val()=='cz_mysql') {//如果mysql,则只读
	       $('#czSchema').attr('readonly', 'readonly');
	       if($('#czPort').val() != '3306') {
	         $('#czPort').val('3306');
	       }
	   }
	});
	
	$('#czIns').blur(function() {//如果mysql，则等于实例值
	   if($('#czType').val()=='cz_mysql') {
	       $('#czSchema').val($('#czIns').val());
	   }
	});
	
	$('#czSchema').focus(function(){//如果oralce，则确保是CZDATA
	  if($('#czSchema').val()=='' && $('#czType').val()=='cz_oracle') {
	    $('#czSchema').val('CZDATA');
	  }
	});
	//其他缺省值
	if($('#qzPort').val() == '') {
	   $('#qzPort').val('1521');
	}
});


</script>
<title></title>
</head>
<body>
<div class="x-panel">
    	<div class="x-panel-header">数据源管理</div>
    	
    	<div class="x-panel-body">
			<s:form action="save" theme="simple" id="save" validate="true" method="POST">
		   		<s:hidden name="model.id"/>
		   		<s:hidden name="model.version"/>
		   		<div style="margin:30px;text-align:center;">
		   		    <%@ include file="/common/messages.jsp"%>
		   		</div>
		   		<fieldset style="margin:30px;">
			    <legend>设定测震数据源</legend>
			    
				<table width="100%">
						
                        <tr>
                             <td width="50%" style="text-align:right;">测震数据库类型：</td>
                             <td style="text-align:left;">
                             <s:select list="czTypes" name="model.czType"
                             id="czType" cssStyle="width:180px;"></s:select>
                             </td>
                        </tr>
                        <tr>
                             <td  style="text-align:right;">测震数据库服务器地址（IP,<font color="red">*</font>）：</td>
                             <td style="text-align:left;">
		                         <s:textfield size="30" name="model.czHost" cssClass="required"/>
                             </td>
                        </tr>
                        
                        <tr>
                             <td  style="text-align:right;">测震数据库名/实例名（<font color="red">*</font>）：</td>
                             <td style="text-align:left;">
                             <s:textfield size="30" name="model.czInstance" cssClass="required" id="czIns"/>
                             </td>
                        </tr>
                        <tr>
                             <td  style="text-align:right;">测震数据库端口（<font color="red">*</font>）：</td>
                             <td style="text-align:left;">
                             <s:textfield size="30" id="czPort" name="model.czPort" cssClass="required digits"/>
                             </td>
                        </tr>
                        <tr>
                             <td  style="text-align:right;">测震数据库只读用户名（<font color="red">*</font>）：</td>
                             <td style="text-align:left;">
                             <s:textfield size="30" name="model.czUser"/>
                             </td>
                        </tr>
                        <tr>
                             <td  style="text-align:right;">测震数据库只读密码（<font color="red">*</font>）：</td>
                             <td style="text-align:left;">
                             <s:textfield size="30" name="model.czPwd" cssClass="required"/>
                             </td>
                        </tr>
                        <tr>
                             <td  style="text-align:right;">测震数据库SCHEMA（<font color="red">*</font>仅限ORACLE）：</td>
                             <td style="text-align:left;">
                             <s:textfield size="30" name="model.czSchema" id="czSchema" cssClass="required"/>
                             </td>
                        </tr>
                        <!--  
                        <tr>
                             <td  style="text-align:right;">测震归档事件波形数据存储路径：（<font color="red">*</font>）</td>
                             <td style="text-align:left;">
                             <s:textfield size="30" name="model.czPath" cssClass="required"/>
                             </td>
                        </tr>
                        -->
                        
                </table>
                </fieldset>
                <fieldset style="margin:30px;">
			    <legend>设定前兆数据源</legend>
			    <table width="100%">
						
                        <tr>
                             <td width="50%" style="text-align:right;">前兆数据库服务器地址（IP，<font color="red">*</font>）：</td>
                             <td style="text-align:left;">
                             <s:textfield size="30" name="model.qzHost" cssClass="required"/>
                             </td>
                        </tr>
                        <tr>
                             <td  style="text-align:right;">前兆数据库名/实例名（<font color="red">*</font>）：</td>
                             <td style="text-align:left;">
                             <s:textfield size="30" name="model.qzInstance" cssClass="required"/>
                             </td>
                        </tr>
                        <tr>
                             <td  style="text-align:right;">前兆数据库端口（<font color="red">*</font>）：</td>
                             <td style="text-align:left;">
                             <s:textfield size="30" id="qzPort" name="model.qzPort" cssClass="required"/>
                             </td>
                        </tr>
                        <tr>
                             <td  style="text-align:right;">前兆数据库只读用户名（<font color="red">*</font>）：</td>
                             <td style="text-align:left;">
                             <s:textfield size="30" name="model.qzUser" cssClass="required"/>
                             </td>
                        </tr>
                        <tr>
                             <td  style="text-align:right;">前兆数据库只读密码（<font color="red">*</font>）：</td>
                             <td style="text-align:left;">
                             <s:textfield size="30" name="model.qzPwd" cssClass="required"/>
                             </td>
                        </tr>
                        <tr>
                             <td  style="text-align:right;">前兆数据库SCHEMA（<font color="red">*</font>）：</td>
                             <td style="text-align:left;">
                             <s:textfield size="30" name="model.qzSchema" id="qzSchema" cssClass="required"/>
                             </td>
                        </tr>
                        
                </table>
			    </fieldset>
                <table width="100%" style="margin-bottom:10px;">
                       <tr>
                        <td style="text-align:center;">
                            <s:submit value="保存" cssClass="button"></s:submit>
                            <s:reset value="重置" cssClass="button"></s:reset></td>
                        </tr>
                </table>
                
                </s:form>
                <img src="${ctx}/images/exticons/icon-info.gif"/>带<font color="red">*</font>的为必填项。
    	</div>
  </div>
</body>
</html>