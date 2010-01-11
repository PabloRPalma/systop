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
<body >
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
<div class="x-panel-header">编辑专家信息</div>
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
		  <table width="562" align="left" border="0" cellspacing="2">
		  <tr>
            <td colspan="1"></td>
          </tr>
          <tr>
            <td width="112" align="right">专家名称：</td>
            <td width="139" align="left">
            	<s:textfield id="name" name="model.name" cssClass="required"/>
            	<font color="red">*</font>
            </td>
            <td align="right" width="90">手&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;机：</td>
            <td width="213" align="left">
            	<s:textfield id="mobile" name="model.mobile" cssClass="required"/><font color="red">&nbsp;*</font>
            </td>
          </tr>
          <tr>
            <td width="112" align="right">专家级别：</td>
            <td width="139" align="left">
                <s:select id="expertLevel" list="%{expertLevels}" name="model.level" headerKey="" headerValue="--请选择--"
	              cssStyle="width:133px;"/>	
            </td>
            <td align="right" width="90">专家类别：</td>
            <td width="213" align="left">
               <s:select id="categoryId" name="model.expertCategory.id" 
                 list="expertCateList" listKey="ecId" listValue="ecName"
						headerKey="" headerValue="--请选择--" cssStyle="width:133px" cssClass="required"/>
			   <font color="red">*</font>		
            </td>
          </tr>            
          <tr>
            <td width="112" align="right">办公电话：</td>
            <td width="139" align="left">
            	<s:textfield id="officePhone" name="model.officePhone" />
            </td>
            <td align="right" width="90">家庭电话：</td>
            <td width="213" align="left">
             	<s:textfield id="homePhone" name="model.homePhone" />
            </td>
          </tr> 
          <tr>
            <td width="112" align="right">电子邮件：</td>
            <td width="139" align="left">
            	<s:textfield id="email" name="model.email" />
            </td>
            <td align="right" width="90">出生日期：</td>
            <td width="213" align="left">
            	<input type="text" name="model.birthDate" value='<s:date name="model.birthDate" format="yyyy-MM-dd"/>'
				  onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" class="Wdate" style="width:127px;height:16px" readonly="readonly"/>
            </td>            
          </tr> 
          <tr>
            <td width="112" align="right">毕业院校：</td>
            <td align="left" colspan="3">
            	<s:textfield id="graduateSchool" name="model.graduateSchool"  cssStyle="width:360px" />
             </td>
          </tr> 
          <tr>
            <td width="112" align="right">学&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;历：</td>
            <td width="139" align="left">
            	<s:textfield id="degree" name="model.degree" />
            </td>
            <td align="right" width="90">职&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;称：</td>
            <td width="213" align="left">
             	<s:textfield id="title" name="model.title" />
            </td>
          </tr> 
          <tr>
            <td width="112" align="right">单&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;位：</td>
            <td width="139" align="left">
            	<s:textfield id="units" name="model.units" />
            </td>
            <td align="right" width="90">职&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;务：</td>
            <td width="213" align="left">
             	<s:textfield id="position" name="model.position" />
            </td>
          </tr> 
          <tr>
            <td width="112" align="right">研究方向：</td>
            <td align="left" colspan="3">
            	<s:textfield id="research" name="model.research"  cssStyle="width:360px" />
             </td>
          </tr>  
          <tr>
            <td width="112" align="right">备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注：</td>
            <td align="left" colspan="3">
            	<s:textfield id="remark" name="model.remark"  cssStyle="width:360px" />
             </td>
          </tr>  
          <tr>
            <td width="112" align="right">专家简介：</td>
            <td align="left" colspan="3">
            	<s:textarea id="summery" name="model.summery" cssStyle="width:360px; height:90px"/>
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
				<tr>
		            <td align="left" id="photoOperSpan2" style="border-top: 1px dotted #099EBD; padding-top: 10px">
			            <c:choose>
							<c:when test="${empty model.photoPath}">
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
    if(confirm("确定要删除该专家的照片吗?")){           
    	var enId = $("#expertId")[0].value;
        $.ajax({
    		url: '${ctx}/expert/deletePhoto.do',
    		type: 'post',
    		dataType: 'json',
    		data: {expertId : enId},
    		success: function(rst, textStatus){
    	  		if(rst.result == "success"){
    	  		  document.getElementById("photoOperSpan1").innerHTML=
    	            "<img alt='专家照片' align='center' width='130' height='142' id='imgphoto' src='${ctx}/images/noSupervisor.gif'/><br/>";
    	          document.getElementById("photoOperSpan2").innerHTML=
    	           "<input type='file' id='photo' name='photo' class='FileText' onchange='changeImg()'/>";
    	  	  	}else{
    	  	  	  alert("删除专家照片失败，请与管理员联系！");
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
</script>
<script type="text/javascript">
	$(document).ready(function() {
	$("#save").validate();
});
</script>
</body>
</html>