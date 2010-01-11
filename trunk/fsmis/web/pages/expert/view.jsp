<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<html>
<head>
<title></title>
<%@include file="/common/extjs.jsp" %>
<%@include file="/common/meta.jsp"%>
<%@include file="/common/validator.jsp"%>
<script type="text/javascript" src="${ctx}/scripts/fckeditor/fckeditor.js"></script>
<link href="${ctx}/styles/treeSelect.css" type='text/css' rel='stylesheet'>
<style type="text/css">
#mytable {
	border: 0px solid #A6C9E2;
	margin-left: 0px;
	margin-top: 0px;
	width: 100%;
	border-collapse: collapse;
}

#mytable td {
	border: 0px solid #A6C9E2;
	height: 28;
}
</style>
</head>
<body onLoad="preFckEditor()">
<script type="text/javascript">
Ext.onReady(function(){
    var tabs = new Ext.TabPanel({
        renderTo: 'tabs',
        anchor : '100% 100%',
        height : 370,
        activeTab: 0,
        frame:false,
        defaults:{autoHeight: false},
        items:[
            {contentEl:'basic', title: '基本信息'}
        ]
    });
});
</script>
<div class="x-panel">
<div class="x-panel-header">浏览专家信息</div>
<div class="x-toolbar">
    <table width="100%">
      <tr>
        <td align="right">
          <a href="${ctx}/expert/index.do">
             <img src="${ctx}/images/icons/building_go.png" class="icon" border="0">专家列表&nbsp;&nbsp;
          </a>
        </td>
      </tr>
    </table>
</div>
<div><%@ include file="/common/messages.jsp"%></div>
<s:form  action="save.do" id="save" method="post" theme="simple" validate="true" enctype="multipart/form-data">
<div id="tabs">
<div id="basic" class="x-hide-display">
	<s:hidden id="expertId" name="model.id" />
	<table id="mytable" height="340">
	    <tr>
		<td width="520">
		  <table width="562" align="left" border="0" cellpadding="0" cellspacing="0">
		  <tr>
            <td colspan="1"></td>
          </tr>
          <tr>
            <td width="112" align="right">专家名称：</td>
            <td width="139" align="left">
            	<s:textfield id="name" name="model.name" cssStyle="border:0" readonly="true"/>
            </td>
            <td align="right" width="90">手&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;机：</td>
            <td width="213" align="left">
            	<s:textfield id="mobile" name="model.mobile" cssStyle="border:0" readonly="true"/>
            </td>
          </tr>
          <tr>
            <td width="112" align="right">专家级别：</td>
            <td width="139" align="left">
                <s:textfield name="model.level" cssStyle="border:0" readonly="true" />
            </td>
            <td align="right" width="90">专家类别：</td>
            <td width="213" align="left">
                <s:textfield name="model.expertCategory.name" cssStyle="border:0" readonly="true" />	
            </td>
          </tr>          
          <tr>
            <td width="112" align="right">办公电话：</td>
            <td width="139" align="left">
            	<s:textfield id="officePhone" name="model.officePhone" cssStyle="border:0" readonly="true"/>
            </td>
            <td align="right" width="90">家庭电话：</td>
            <td width="213" align="left">
             	<s:textfield id="homePhone" name="model.homePhone" cssStyle="border:0" readonly="true"/>
            </td>
          </tr> 
          <tr>
            <td width="112" align="right">电子邮件：</td>
            <td width="139" align="left">
            	<s:textfield id="email" name="model.email" cssStyle="border:0" readonly="true"/>
            </td>
            <td align="right" width="90">出生日期：</td>
            <td width="213" align="left" style="vertical-align: middle">
            	<s:textfield id="birthDate" name="model.birthDate" cssStyle="border:0" readonly="true"/>            
            </td>            
          </tr> 
          <tr>
            <td width="112" align="right">毕业院校：</td>
            <td align="left" colspan="3">
            	<s:textfield id="graduateSchool" name="model.graduateSchool"  cssStyle="border:0" readonly="true"/>
             </td>
          </tr>   
          <tr>
            <td width="112" align="right">学&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;历：</td>
            <td width="139" align="left">
            	<s:textfield id="degree" name="model.degree" cssStyle="border:0" readonly="true"/>
            </td>
            <td align="right" width="90">职&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;称：</td>
            <td width="213" align="left">
             	<s:textfield id="title" name="model.title" cssStyle="border:0" readonly="true"/>
            </td>
          </tr> 
          <tr>
            <td width="112" align="right">单&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;位：</td>
            <td width="139" align="left">
            	<s:textfield id="units" name="model.units" cssStyle="border:0" readonly="true" />
            </td>
            <td align="right" width="90">职&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;务：</td>
            <td width="213" align="left">
             	<s:textfield id="position" name="model.position" cssStyle="border:0" readonly="true" />
            </td>
          </tr> 
          <tr>
            <td width="112" align="right">研究方向：</td>
            <td align="left" colspan="3">
            	<s:textfield id="research" name="model.research"  cssStyle="border:0" cssClass="inputBorder" readonly="true" />
             </td>
          </tr>  
          <tr>
            <td width="112" align="right">备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注：</td>
            <td align="left" colspan="3">
            	<s:textfield id="remark" name="model.remark"  cssStyle="border:0" cssClass="inputBorder" readonly="true" />
             </td>
          </tr>  
          <tr>
            <td width="112" align="right">专家简介：</td>
            <td align="left" colspan="3">
	          	<div style="width:360px;height:90px;word-break:break-all;">
		          	${model.summery}
	          	</div>            
            
            </td>
          </tr>                                                                                      
        </table></td>
		<td width="300" valign="top" style="border-left: 1px dotted #099EBD;">
			<table width="300" border="0" >
				<tr>
					<td id="photoOperSpan1">
					<img alt="照片" align="middle" width="130" height="142"	id="imgphoto"
						src="<c:if test='${not empty model.photoPath}'>${ctx}${model.photoPath}</c:if>
							 <c:if test='${empty model.photoPath}'> ${ctx}/images/noSupervisor.gif</c:if>" />
					</td>
				</tr>
			</table>
		</td>        
	  </tr>
	</table>
</div>
</div>
</s:form>
</div>
</body>
</html>