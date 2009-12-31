<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<html>
<head>

<title></title>
<%@include file="/common/ec.jsp"%>
<%@include file="/common/extjs.jsp"%>
<%@include file="/common/meta.jsp"%>
</head>
<body>
<div id="winDealWithTaskDetail" >
<div class="x-window-header">处理任务</div>
<div id="dealWithTaskDetail">
	<s:form  action="doCommitTaskDetail.do" method="post" theme="simple" enctype="multipart/form-data" onsubmit="return onCheckForm()">
	<s:hidden name="model.id"/>
	
	<table align="left" width="700px">	
	<tr>
	<td>
	<fieldset style="width:450px;height:250px;">
    	<legend>处理结果</legend>
        <table width="440px" height="250px" align="center">
          <tr>
             <td align="right" width="80">填写人：</td>
             <td align="left" width="150">
             	<s:textfield id="name" name="model.inputer" cssStyle="width:150px"/>
             </td>
          </tr>
          <tr>
             <td align="right">处理人：</td>
	         <td align="left">
	            <s:textfield id="people" name="model.processor" cssStyle="width:150px"/>
	         </td>
	      </tr>
          <tr>
            <td align="right">处理过程：</td>
            <td align="left">
            	<s:textarea id="pass" name="model.process" cols="47" rows="5"></s:textarea>
            </td>
          </tr>
          <tr>
            <td align="right">处理依据：</td>
            <td align="left">
            	<s:textarea id="yiju" name="model.basis" cols="47" rows="3"></s:textarea>
            </td>
          </tr>
          <tr>
            <td align="right">处理结果：</td>
            <td align="left">
            	<s:textfield id="result" name="model.result" cssStyle="width:350px"></s:textfield>
            </td>
          </tr>
        </table> 
    </fieldset>
	</td>
	<td>
	<fieldset style="width:480px;height:250px;">
    	<legend>被处理单位信息</legend>
        <table width="470px" height="250px" align="center">
         <tr>
         	<td colspan="2">
         		<font color="red" style="font-size:12px">【建议按"↓"键显示并选择企业信息】</font>
         	</td>
         </tr>
          <tr>
             <td align="right" width="120">名称：</td>        
             <td align="left" width="420">
             	<s:textfield id="companyName" name="model.task.fsCase.corp.name" onblur="queryCompanyByCode()" cssStyle="width:350px"/>
             </td>
          </tr>
		  <tr>
            <td align="right">企业编号：</td>
            <td align="left">
            	<s:textfield id="companyCode" name="model.task.fsCase.corp.code" cssStyle="width:350px"/>
            </td>
          </tr>		            
          <tr>
            <td align="right">地址：</td>
            <td align="left">
            	<s:textfield id="companyAddress" name="model.task.fsCase.corp.address" cssStyle="width:350px"/>
            </td>
          </tr>
          <tr>
            <td align="right">法人：</td>
            <td align="left">
            	<s:textfield id="legalPerson" name="model.task.fsCase.corp.legalPerson" cssStyle="width:350px"/>
            </td>
          </tr>          
          <tr>
            <td align="right">生产许可证：</td>
            <td align="left">
            	<s:textfield id="produceLicenseCode" name="model.task.fsCase.corp.produceLicense" cssStyle="width:350px"/>
            </td>
          </tr>         
          <tr>
            <td align="right">卫生许可证：</td>
            <td align="left">
            	<s:textfield id="sanitationLicenseCode" name="model.task.fsCase.corp.sanitationLicense" cssStyle="width:350px"/>
            </td>
          </tr>
          <tr>
            <td align="right">经营范围：</td>
            <td align="left">
            	<s:textarea id="operateDetails" name="model.task.fsCase.corp.operateDetails" cols="48" rows="2"></s:textarea>
            </td>
          </tr>        
        </table> 
    </fieldset>
	</td>
	</tr>
	<tr>
	<td colspan="2">
	<table width="600px" style="margin-bottom:10px;">
		<tr>
			<td style="text-align:center;">
				<s:submit value="处理完毕" cssClass="button"/>
				<s:reset value="重置" cssClass="button"/>
		   </td>
		</tr>
	</table>
	</td>
	</tr>
	</table>
    
	</s:form>
</div>
</div>
<!-- script type="text/javascript"
	src="${ctx}/pages/fsCase/task/taskdetail/dealWithTaskDetail.js">	
</script-->
</body>
</html>