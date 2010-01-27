<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<html>
<head>
<title></title>
<%@include file="/common/meta.jsp"%>
<%@include file="/common/extjs.jsp"%>
<%@include file="/common/autocomplete.jsp"%>
</head>
<body>
<div id="winDealWithTaskDetail" class="x-panel">
<div class="x-panel-header"></div>
<div id="dealWithTaskDetail" class="x-toolbar"></div>
<div>
	<s:form  id="frmDealWithTaskDetail" action="doDealWithTaskDetail.do" method="post" theme="simple" enctype="multipart/form-data" onsubmit="return onCheckForm()">
	<s:hidden name="model.id"/>
	<s:hidden name="isMultipleCase"/>
	<s:hidden name="modelId" />
	<table align="center" width="700px">	
	<tr>
	<td>
	<fieldset style="width:400px;height:450px;">
    	<legend>处理结果</legend>
        <table width="400px" align="center">
        <tr><td>&nbsp;</td><td>&nbsp;</td></tr>
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
            	<s:textarea id="pass" name="model.process" cols="40" rows="6"></s:textarea>
            </td>
          </tr>
          <tr>
            <td align="right">处理依据：</td>
            <td align="left">
            	<s:textarea id="yiju" name="model.basis" cols="40" rows="6"></s:textarea>
            </td>
          </tr>
          <tr>
            <td align="right">处理结果：</td>
            <td align="left">
            	<s:textarea id="result" name="model.result" rows="8" cssStyle="width:300px"></s:textarea>
            </td>
          </tr>
        </table> 
    </fieldset>
	</td>
	<td>
	<fieldset style="width:390px;height:450px;">
    	<legend>被处理单位信息</legend>
        <table width="390px"  align="center">
        <tr><td>&nbsp;</td><td>&nbsp;</td></tr>
         <tr>
         	<td colspan="2">
         		<font color="red" style="font-size:12px">【建议按"↓"键显示并选择企业信息】</font>
         	</td>
         </tr>
          <tr height="40">
             <td align="right" width="80">名称：</td>        
             <td align="left" width="270">
             	<s:hidden name="model.task.fsCase.corp.id" id="corpId"></s:hidden>
             	<s:textfield id="companyName" name="model.task.fsCase.corp.name" onblur="queryCompanyByCode()"  cssStyle="width:270px"/>
             </td>
          </tr>
		  <tr height="40">
            <td align="right">企业编号：</td>
            <td align="left">
            	<s:textfield id="companyCode" name="model.task.fsCase.corp.code" cssStyle="width:270px"/>
            </td>
          </tr>		            
          <tr height="40">
            <td align="right">地址：</td>
            <td align="left">
            	<s:textfield id="companyAddress" name="model.task.fsCase.corp.address" cssStyle="width:270px"/>
            </td>
          </tr>
          <tr height="40">
            <td align="right">法人：</td>
            <td align="left">
            	<s:textfield id="legalPerson" name="model.task.fsCase.corp.legalPerson" cssStyle="width:270px"/>
            </td>
          </tr>          
          <tr height="40">
            <td align="right">生产许可证：</td>
            <td align="left">
            	<s:textfield id="produceLicense" name="model.task.fsCase.corp.produceLicense" cssStyle="width:270px"/>
            </td>
          </tr>         
          <tr height="40">
            <td align="right">卫生许可证：</td>
            <td align="left">
            	<s:textfield id="sanitationLicense" name="model.task.fsCase.corp.sanitationLicense" cssStyle="width:270px"/>
            </td>
          </tr>
          <tr>
            <td align="right">经营范围：</td>
            <td align="left">
            	<s:textarea id="operateDetails" name="model.task.fsCase.corp.operateDetails" cols="36" rows="6"></s:textarea>            	
            </td>
          </tr>        
        </table> 
    </fieldset>
	</td>
	</tr>
	<tr>
	<td colspan="2">
	<table width="790px" style="margin-bottom:10px;">
		<tr>
			<td style="text-align:center;">
				<input type="button" id="btnSubmit" class="button" value="处理完毕"></input>
				<s:reset value="重    置" cssClass="button"/>
		   </td>
		</tr>
	</table>
	</td>
	</tr>
	</table>
    
	</s:form>
</div>
</div>
<script type="text/javascript">
$(function() {
	   $('#btnSubmit').click(function() {
	       $('#frmDealWithTaskDetail').submit();
	   });
	});
	   
</script>
<script type="text/javascript">
$().ready(function(){
	$.ajax({
		url:'${ctx}/taskdetail/getCorps.do',
		type:'post',
		dataType:'json',
		success:function(corps,textStatus){
			if(corps.length > 0){
			$('#companyName').autocomplete(corps,{
				mustMatch : true,
				matchContains:true,
				minChars:0,
				width :'200px',
				scrollHeight:200
			});
			}
		},
		failure: function() {
	         alert('错误');
	    }
	
	});
	  $("#companyName").result(function(event,data,formatter){
			queryCompanyByCode();
	  }); 
});
//根据企业编号查询企业信息
function queryCompanyByCode(){
  var companyInfo = $("#companyName").val();
  var code = companyInfo.substring(0,companyInfo.indexOf(":"));
	$.ajax({
		url:'${ctx}/taskdetail/getCorpByCode.do',
		type:'post',
		dataType:'json',
		data:{corpId:code},
		success:function(corp,textStatus){
		  if(corp){            
	          $("#companyName").val(corp.name);              
	          $("#companyCode").val(corp.code);
	          $("#companyAddress").val(corp.address);
	          $("#legalPerson").val(corp.legalPerson);
	          $("#produceLicense").val(corp.produceLicense);
	          $("#sanitationLicense").val(corp.sanitationLicense);
	          $("#corpId").val(corp.id);
	          $("#operateDetails").val(corp.operateDetails);
	       }  
	},
		failure: function() {
    		alert('错误');
	}
	
	});
}
</script>
</body>
</html>