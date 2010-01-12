<%@ page language="java" contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<html>
<head>
<title></title>
<%@include file="/common/meta.jsp"%>
<%@include file="/common/ec.jsp"%>
<script type="text/javascript">

//删除单条风险评估附件
function removeAttach(attachId){
  if(confirm("确认要删除本条评估附件信息吗？")){    
    location.href = "remove.do?model.id=" + attachId 
                    + "&assessmentId=" + document.getElementById("assessmentId").value;
  }
}

//上传表单检查
function checkUpload(){
  if(document.getElementById("attachment").value.trim().length == 0){
    alert("上传附件不能为空！");
    return false;
  }
  if(document.getElementById("creator").value.trim().length == 0){
    alert("完成人不能为空!");
    return false;
  } 
  try{  
    document.getElementById("uploadForm").submit();
  }catch(e){    
    alert("上传的附件无效！");
  }
}

//去掉字符串两端的空格
String.prototype.trim = function(){
  return this.replace(/^\s*/,"").replace(/\s*$/,"");
}

</script>
</head>
<body>
<div class="x-panel">

<div class="x-toolbar">
<s:form id="uploadForm" action="upload.do" method="post" enctype="multipart/form-data" onsubmit="return checkUpload()">
<s:hidden id="assessmentId" name="assessmentId"></s:hidden>
<table width="99%">
  <tr>
      <td align="left" width="2%">上传文件：</td>
      <td align="left" width="17%">
        <s:file id="attachment" name="attachment" cssClass="FileText" cssStyle="width:250px"/>
      </td>
      <td align="left" width="2%">
                 完成人：
      </td>
      <td width="8%" style="vertical-align: middle"><s:select id="creator"
		  name="model.creator" list="expertList" listKey="expertName" listValue="expertName"
		  headerKey="" headerValue="--请选择--" cssStyle="width:100px" /></td>	
	  <td align="center"><span style="color:red;font-weight:bold">
			<s:if test="errorMsg.size() > 0">
				<td align="left"><font color="red">提示信息：
				<s:iterator value="errorMsg">
					<s:property />
				</s:iterator></font></td>
			</s:if>
	  </span></td>    
      <td align="right">
        <s:submit value="上传附件" cssClass="button" />
      </td>      
  </tr>
 </table>
</s:form>
</div>
<div class="x-panel-body">
<div style="margin-left: -3px;" align="center">
<ec:table
  items="items" var="item" retrieveRowsCallback="limit" sortRowsCallback="limit" 
  action="index.do" 
  useAjax="false"
  doPreload="false" 
  pageSizeList="5" 
  editable="false"
  sortable="true" 
  rowsDisplayed="5" 
  generateScript="true"
  resizeColWidth="false" 
  classic="false" 
  width="100%" 
  height="160px"
  minHeight="160"
  toolbarContent="navigation|pagejump|pagesize|refresh|extend|status">
 <ec:row>
    <ec:column width="30" property="_num" title="No." value="${GLOBALROWCOUNT}" sortable="false" style="text-align:center"/>
    <ec:column width="600" property="title" title="标题">
      <a href="${ctx}${item.path}" target="_blank"><font color="blue">${item.title}</font></a>
    </ec:column>
    <ec:column width="150" property="creator" title="完成人" style="text-align:center"/>    
    <ec:column width="70" property="_0" title="操作"
      style="text-align:center" sortable="false">
      <a href="#" onclick="removeAttach(${item.id})">删除
      </a>
    </ec:column>        
  </ec:row>
</ec:table>
</div>
</div>
</div>
</body>
</html>