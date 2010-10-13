<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="/common/taglibs.jsp" %>
<html>
<head>
<%@include file="/common/meta.jsp" %>
<title></title>
<style type="text/css">
.input{
	border:0px;
	border-bottom:1px solid #C0C0C0;
	width:450px;
}
.textarea{
	width:450px;
	height:60px;
	border:1px solid #C0C0C0;
}
.tdb{
	border-bottom:1px solid #C0C0C0;
	padding:5px 5px 3px 5px;
}
</style>
</head>
<body>
<div class="x-panel">
<div class="x-panel-header">咨询管理</div>
<div><%@ include file="/common/messages.jsp"%></div>
<s:form action="save" method="post" theme="simple" validate="true" namespace="/admin/advisory" >
	<s:hidden id="model.id" name="model.id"/>
	<table width="600px" align="center"> 
	     <tr>
			<td align="center">
			<fieldset style="margin:10px;">
              <legend>咨询信息</legend>
                <table>
                  <tr>
                     <td align="right" width="150">咨询标题：</td>
                     <td align="left" width="450" class="tdb">
                     	${model.title}&nbsp;
                     </td>
                  </tr>
                  <tr>
                    <td align="right">提交日期：</td>
                    <td align="left" class="tdb">
                    	<s:date name="model.creatDate" format="yyyy-MM-dd"/>&nbsp;
                    </td>
                  </tr>
                  <tr>
                    <td align="right">提交人：</td>
                    <td align="left" class="tdb">
                    	${model.name}&nbsp;
                    </td>
                  </tr>
                  <tr>
                    <td align="right">联系电话：</td>
                    <td align="left" class="tdb">
                    	${model.phone}&nbsp;
                    </td>
                  </tr>
                  <tr>
                    <td align="right">邮政编码：</td>
                    <td align="left" class="tdb">
                   		${model.posCode}&nbsp;
                    </td>
                  </tr>
                  <tr>
                    <td align="right">地　　址：</td>
                    <td align="left" class="tdb">
                    	${model.address}&nbsp;
                  </tr>
                  <tr>
                    <td align="right">咨询内容：</td>
                    <td align="left" class="tdb">
						${model.content}&nbsp;
                    <td>
                  </tr>
                  <tr>
                    <td align="right">回复内容：</td>
                    <td align="left">
                    	<s:textarea id="model.reContent" name="model.reContent" cols="55" rows="5"/>
                    <td>
                  </tr>
                </table> 
              </fieldset>
              <table width="100%" style="margin-bottom:10px;">
				<tr>
					<td style="text-align:center;">
						<s:submit value="保存" cssClass="button"/>
						<s:reset value="重置" cssClass="button"/>
                    </td>
              	</tr>
              </table>
			</td>
		</tr>
	</table>
	</s:form>
</div>
</body>
</html>