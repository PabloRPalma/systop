<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="/common/taglibs.jsp"%>
<html>
<head>
<title></title>
<%@include file="/common/meta.jsp"%>
<style type="text/css">
	.inputBorder{
		border: 0px;
		border-bottom: 1px solid #099EBD;
	}
</style>
</head>
<body>
<div class="x-panel">
<div class="x-panel-header">监管员管理&nbsp;>&nbsp;监管员列表&nbsp;>&nbsp;监管员信息：</div>
<div align="center">
  <fieldset style="width: 600px; padding: 50px 10px 10px 10px;">
	<legend>监管员信息</legend>
		<table width="532" align="center" border="0" cellpadding="2" cellspacing="1">
		<tr>
			<td width="100" align="right">编　　号：</td>
			<td colspan="2" align="left">
		  		<s:textfield id="mobile" name="model.code" cssStyle="width:100px" cssClass="inputBorder" readonly="true"/>
		  	</td>
	      </tr>
		<tr>
			<td width="100" align="right">姓　　名：</td>
			<td colspan="2" align="left">
				<s:textfield id="name" name="model.name" cssStyle="width:100px" cssClass="inputBorder" readonly="true"/>					
			</td>
		    <td width="150" rowspan="8" id="photoOperSpan">
		    	<div align="left"> 
		    		<img alt="照片"  align="center" width="106" height="142" id="imgphoto" 
						src="<c:if test='${not empty model.photoUrl}'>${ctx}${model.photoUrl}</c:if>
							 <c:if test='${empty model.photoUrl}'> ${ctx}/images/noSupervisor.gif</c:if>"/>
		    	</div>
            </td>
		</tr>
		<tr>
			<td width="100" align="right">性　　别：</td>
			<td colspan="2" align="left">
				<s:textfield name="model.gender" cssStyle="width:100px" cssClass="inputBorder" readonly="true"/>
			</td>
	    </tr>
	    <tr>
		  <td align="right">出生日期：</td>
		  <td colspan="2" align="left">
		  	<input type="text" name="model.birthday2" value='<s:date name="model.birthday" format="yyyy-MM-dd"/>'
				  class="inputBorder" style="width:100px;height:16px" readonly="true"/></td>
		</tr>
		<tr>
			<td width="100" align="right">单　　位：</td>
			<td colspan="2" align="left"><s:textfield id="unit" name="model.unit" cssStyle="width:180px" cssClass="inputBorder" readonly="true"/></td>
	      </tr>
		<tr>
			<td width="100" align="right">职　　务：</td>
			<td colspan="2" align="left"><s:textfield id="duty" name="model.duty" cssStyle="width:180px" cssClass="inputBorder" readonly="true"/></td>
	      </tr>
		<tr>
			<td width="100" align="right">所属部门：</td>
			<td colspan="2" align="left">
				<s:textfield id="dept.name" name="model.dept.name" cssClass="inputBorder" readonly="true" cssStyle="width:180px"/>
			</td>
	      </tr>
	    <tr>
			<td width="100" align="right">是否负责人：</td>
			<td colspan="2" align="left">
				<s:if test="model.isLeader == 1">
					<s:textfield id="isLeader" name="model.isLeader" value="是" cssClass="inputBorder" readonly="true" cssStyle="width:180px"/>
				</s:if>
				<s:else>
					<s:textfield id="isLeader" name="model.isLeader" value="否" cssClass="inputBorder" readonly="true" cssStyle="width:180px"/>
				</s:else>
			</td>
	     </tr>
		<tr>
			<td width="100" align="right">移动电话：</td>
			<td colspan="3" align="left">
		  		<s:textfield id="mobile" name="model.mobile" cssStyle="width:180px" cssClass="inputBorder" readonly="true"/>			
		  	</td>
		 </tr>
		 <tr>
			<td width="100" align="right">固定电话：</td>
			<td colspan="3" align="left">
				<s:textfield id="phone" name="model.phone" cssStyle="width:180px" cssClass="inputBorder" readonly="true"/>			
			</td>
		</tr>
		<tr>
			<td width="100" align="right">&nbsp;&nbsp;&nbsp;&nbsp;监管区域：</td>
			<td align="left" colspan="3">
				<s:textfield id="superviseRegion" name="model.superviseRegion" cssStyle="width:380px" cssClass="inputBorder" readonly="true"/>
			</td>
		</tr>
	</table>
　</fieldset>
</div>
</div>
</body>
</html>