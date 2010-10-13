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
	if($('#minM').val() == '') {
	  $('#minM').val('1');
	}
	if($('#maxItems').val() == '') {
	  $('#maxItems').val('10');
	}
});


</script>
<title></title>
</head>
<body>
<div class="x-panel">
    	<div class="x-panel-header">设定邮件订阅属性</div>
    	
    	<div class="x-panel-body">
			<s:form action="save" theme="simple" id="save" validate="true" method="POST">
				<s:hidden name="model.id"/>
		   		<s:hidden name="model.version"/>
		   		<%@ include file="/common/messages.jsp"%>
		   		<fieldset style="margin-left:60px;margin-right:60px;margin-top:20px;">
			    <legend>设定邮件订阅属性</legend>
			    
				<table width="68%">
						<tr>
							<td colspan="2"><%@ include file="/common/messages.jsp"%></td>
						</tr>
                        <tr>
                             <td width="50%">测震数据发送频率（<font color="red">*</font>）：</td>
                             <td style="text-align:left;">
                             <s:select list="freqs" name="model.freqSeismic"
                             id="freqSeismic" cssStyle="width:180px;"></s:select>
                             </td>
                        </tr>
                        <tr>
                             <td>前兆数据发送频率（<font color="red">*</font>）：</td>
                             <td style="text-align:left;">
		                         <s:select list="freqs" name="model.freqSign"
                             id="freqSign" cssStyle="width:180px;"></s:select>
                             </td>
                        </tr>
                        
                        <tr>
                             <td>最小震级（<font color="red">*</font>）：</td>
                             <td style="text-align:left;">
                             <s:textfield size="30" id="minM" name="model.minM" cssClass="required number"/>
                             </td>
                        </tr>
                        <tr>
                             <td>每个用户最多订阅的测项分量数目（<font color="red">*</font>）：</td>
                             <td style="text-align:left;">
                             <s:textfield size="30" id="maxItems" name="model.maxItems" cssClass="required digits"/>
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
                <img src="${ctx}/images/exticons/icon-info.gif" style="margin-left:60px;"/>带<font color="red">*</font>的为必填项。
    	</div>
  </div>
</body>
</html>