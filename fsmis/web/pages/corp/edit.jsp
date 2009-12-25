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
<script type="text/javascript">
Ext.onReady(function(){
    var tabs = new Ext.TabPanel({
        renderTo: 'tabs',
        anchor : '100% 100%',
        height : 380,
        activeTab: 0,
        frame:false,
        defaults:{autoHeight: false},
        items:[
            {contentEl:'basic', title: '基本信息'},
            {contentEl:'reward', title: '企业诚信'}
        ]
    });
});
</script>
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
<div class="x-panel">
<div class="x-panel-header">编辑企业信息</div>
<div class="x-toolbar">
    <table width="100%">
      <tr>
        <td align="right">
          <a href="${ctx}/corp/index.do">
             <img src="${ctx}/images/icons/building_go.png" class="icon" border="0">企业列表&nbsp;&nbsp;
          </a>
        </td>
      </tr>
    </table>
</div>
<s:form id="companyForm" action="save.do" method="post" theme="simple" validate="true" enctype="multipart/form-data">
<div id="tabs">
<div id="basic" class="x-hide-display">
	<s:hidden id="corpId" name="model.id" />
	<table id="mytable" height="340">
	  <tr>
		<td width="520">
		  <table width="562" align="left" border="0" cellspacing="2">
		  <tr>
            <td height="5" colspan="4"></td>
          </tr>
          <tr>
            <td width="112" align="right">企业名称：</td>
            <td align="left" colspan="3">
            	<s:textfield id="name" name="model.name" cssClass="required" cssStyle="width:365px" />
                <font color="red">&nbsp;*</font> 
            </td>
          </tr>
          <tr>
            <td width="112" align="right">地　址：</td>
            <td align="left" colspan="3">
            	<s:textfield id="address" name="model.address" cssClass="required" cssStyle="width:365px" />
                <font color="red">&nbsp;*</font> </td>
          </tr>
          <tr>
            <td width="112" align="right">法　人：</td>
            <td align="left" colspan="3">
            	<s:textfield id="legalPerson" name="model.legalPerson" cssStyle="width:365px" />
            </td>
          </tr>
          <tr>
            <td width="112" align="right">营业执照：</td>
            <td width="129" align="left">
            	<s:textfield id="businessLicense" name="model.businessLicense" />
            </td>
            <td align="right" width="90">有效期：</td>
            <td width="213" align="left">
            	<input type="text" name="model.businessLicenseDate" value='<s:date name="model.businessLicenseDate" format="yyyy-MM-dd"/>'
				  onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" class="Wdate" style="width:115px;height:16px" readonly="readonly"/>
            </td>
          </tr>
          <tr>
            <td width="112" align="right">生产许可证：</td>
            <td width="129" align="left">
            	<s:textfield id="produceLicense" name="model.produceLicense"/>
            </td>
            <td align="right" width="90">有效期：</td>
            <td width="213" align="left">
            	<input type="text" name="model.produceLicenseDate" value='<s:date name="model.produceLicenseDate" format="yyyy-MM-dd"/>'
				  onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" class="Wdate" style="width:115px;height:16px" readonly="readonly"/>
            </td>
          </tr>
          <tr>
            <td width="112" align="right">卫生许可证：</td>
            <td width="129" align="left">
            	<s:textfield id="sanitationLicense" name="model.sanitationLicense" />
            </td>
            <td align="right" width="90">有效期：</td>
            <td width="213" align="left">
            	<input type="text" name="model.sanitationLicenseDate" value='<s:date name="model.sanitationLicenseDate" format="yyyy-MM-dd"/>'
				  onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" class="Wdate" style="width:115px;height:16px" readonly="readonly"/>
            </td>
          </tr>
          <tr>
            <td width="112" align="right">企业编号：</td>
            <td width="129" align="left">
            	<s:textfield id="code" name="model.code" />
            </td>
            <td width="90" align="right">部　门：</td>
            <td width="213" align="left">
            	<div id="comboxWithTree" class="required" style="float: left;margin-left:0px;" ></div>
				<s:hidden name="model.dept.id" id="deptId"></s:hidden>
            </td>
          </tr>
          <tr>
            <td width="112" align="right">固　话：</td>
            <td width="129" align="left">
            	<s:textfield id="phone" name="model.phone"/>
            </td>
            <td width="90" align="right">手　机：</td>
            <td width="213" align="left">
            	<s:textfield id="mobile" name="model.mobile" cssStyle="width:135px"/>
            </td>
          </tr>
          <tr>
            <td width="112" align="right">诚信等级：</td>
            <td width="129" align="left">
            	<s:select id="integrityGrade" name="model.integrityGrade" list="{'A','B','C','D'}" headerKey="" headerValue="诚信等级..."/>
            </td>
            <td width="90" align="right">邮　编：</td>
            <td width="213" align="left">
            	<s:textfield id="zip" name="model.zip" cssStyle="width:135px"/>
            </td>
          </tr>
          <tr>
            <td width="112" align="right">经营范围：</td>
            <td align="left" colspan="3">
            	<s:textfield id="operateDetails" name="model.operateDetails" cssStyle="width:362px;"/>
            </td>
          </tr>
          <tr>
            <td width="112" align="right">备　注：</td>
            <td align="left" colspan="3">
            	<s:textfield id="remark" name="model.remark" cssStyle="width:362px;"/>
            </td>
          </tr>
          <tr>
            <td width="112" align="right">企业简介：</td>
            <td align="left" colspan="3">
            	<s:textarea id="descn" name="model.descn" cssStyle="width:362px; height:70px"/>
            </td>
          </tr>
        </table></td>
		<td width="300" valign="top" style="border-left: 1px dotted #099EBD;">
			<table width="300" border="0" >
				<tr>
					<td id="photoOperSpan1">
					<img alt="照片" align="middle" width="300" height="225"	id="imgphoto"
						src="<c:if test='${not empty model.photoUrl}'>${ctx}${model.photoUrl}</c:if>
							 <c:if test='${empty model.photoUrl}'> ${ctx}/images/corp/com_nophoto_big.gif</c:if>" />
					</td>
				</tr>
				<tr>
		            <td align="center"  id="photoOperSpan2" style="border-top: 1px dotted #099EBD; padding-top: 10px">
			            <c:choose>
							<c:when test="${empty model.photoUrl}">
								<s:file id="photo" name="photo" cssClass="FileText"
									onchange="changeImg()"></s:file>
							</c:when>
							<c:otherwise>
								<input name="button" type="button"
									onClick="deletePhoto()" value="删除照片" />
							</c:otherwise>
						</c:choose>
		            </td>
		        </tr>
			</table>
		</td>
	  </tr>
	</table>
</div>
<div id="reward" class="x-hide-display">
  <table id="mytable" height="380">
	<tr>
	  <td align="left" style="vertical-align: top;" width="100%">
		<textarea id="integrityRecord" name="model.integrityRecord" cols="30" rows="8">${model.integrityRecord}</textarea>
	  </td>
	</tr>
  </table>
</div>
</div>
<table width="100%" style="margin-bottom: 0px;">
		<tr>
			<td height="5"></td>
		</tr>
		<tr>
			<td style="text-align: center;">
				<s:submit value="保存" cssClass="button"/>&nbsp;&nbsp;
				<s:reset value="重置" cssClass="button"/>
			</td>
		</tr>
</table>
</s:form>
</div>
<script type="text/javascript">
  //删除照片
  function deletePhoto(){
    if(confirm("确定要删除该企业的照片吗?")){           
    	var enId = $("#corpId")[0].value;
        $.ajax({
    		url: '${ctx}/corp/deletePhoto.do',
    		type: 'post',
    		dataType: 'json',
    		data: {corpId : enId},
    		success: function(rst, textStatus){
    	  		if(rst.result == "success"){
    	  		  document.getElementById("photoOperSpan1").innerHTML=
    	            "<img alt='企业照片' align='center' width='300' height='255' id='imgphoto' src='${ctx}/images/corp/com_nophoto_big.gif'/><br/>";
    	          document.getElementById("photoOperSpan2").innerHTML=
    	           "<input type='file' id='photo' name='photo' class='FileText' onchange='changeImg()'/>";
    	  	  	}else{
    	  	  	  alert("删除企业照片失败，请与管理员联系！");
    	  	  	}
    		}
    	  });
    }
  }
  
  function changeImg(){
  	var img = document.getElementById("photo");
  	var imgphoto = document.getElementById("imgphoto");
  	imgphoto.src = img.value;
  }
  
  //文本编辑组件
  function preFckEditor(){
	var fckEditor = new FCKeditor( 'integrityRecord' ) ;
    fckEditor.BasePath = "${ctx}/scripts/fckeditor/";
    fckEditor.ToolbarSet = 'BasicA';
    fckEditor.Height = 360;
    fckEditor.ReplaceTextarea();
}
</script>
<script type="text/javascript" src="${ctx}/pages/admin/dept/edit.js"></script>
<script type="text/javascript">
Ext.onReady(function() {
	var dtree = new DeptTree({
		url : '/admin/dept/deptTree.do',
		parent : '<stc:loginUserDept showPath="false" propertyName="id" showTopDept="true"></stc:loginUserDept>&noLowerDept=1',
		initValue : '${model.dept.name}',
		el : 'comboxWithTree',
		innerTree :'inner-tree',
		onclick : function(nodeId) {
		  Ext.get('deptId').dom.value = nodeId;
		}
	});
	dtree.init();
});
</script>
<script type="text/javascript">
	$(document).ready(function() {
	$("#companyForm").validate();
});
</script>
</body>
</html>