<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<script type="text/javascript" src="${ctx}/dwr/interface/ArticleAction.js"></script>
<script type="text/javascript" src="${ctx}/js/dojo/dojo/dojo.js" djConfig="isDebug:true, parseOnLoad: true"></script>
<script type="text/javascript" src="${ctx}/js/util.js"></script>
<script type="text/javascript"> 

  //增加文件输入框 
  function addFileInput(){
    var oFileInputTable = document.getElementById("fileUpload");
    var fileIndex = oFileInputTable.childNodes.length + 1;
    var oTR  = document.createElement("TR");
    var oTD1 = document.createElement("TD");
    var oTD2 = document.createElement("TD");

    oTR.setAttribute("id","file_"+fileIndex);    
    oTD1.setAttribute("style","text-align:right;");
    oTD2.setAttribute("style","text-align:left;");
    oTD2.innerHTML = '<input type="file" name="attachments" cssClass="FileText" style="width:300px"/>&nbsp;<A href="javascript:deleteFileInput('+fileIndex+');"><img src="${ctx}/images/icons/delete.gif"></A>';
        
    oTR.appendChild(oTD1);
    oTR.appendChild(oTD2);
    oFileInputTable.appendChild(oTR);
  }

  //删除文件输入框
  function deleteFileInput(childId) {
    var oTR = document.getElementById("file_"+childId);
  	var oFileInputTable = document.getElementById("fileUpload");;
  	oFileInputTable.removeChild(oTR);
  }
</script>
<script type="text/javascript">
  function deleteAttachment(attachmentId) {
  	if (confirm("确定要删除吗？")) {
    	deleteDiv(attachmentId);
        ArticleAction.removeAttachments(attachmentId,
  	function (msg) {
  	  if (msg == 'success') {
  	    alert('删除成功！');
  	  } else if(msg == 'error') {
  	    alert('文章下存在附件不能删除。');
  	  }
  	}
  );
}
}
	function deleteDiv(attachmentId) {
		document.getElementById('attachments'+attachmentId).style.display='none'; 
		document.getElementById('attachments'+attachmentId).innerHTML=""; 
		document.getElementById('attachments'+attachmentId).removeNode(); 
	}
</script>

<table align="center" width="100%" bgcolor="#EBEBEB">
	<tr>
		<td bgcolor="#EBEBEB"width="200" align="right" style="padding: 10 5 2 5;">
			<strong>文章附件</strong>
		</td>
		<td bgcolor="#EBEBEB" style="padding: 10 5 2 5;">
			<a href="#" onclick="javascript:addFileInput();">
			<img src="${ctx}/images/icons/file_add.gif">增加附件&nbsp;
		</a>
	</tr>
	<tbody id="fileUpload"></tbody>
	<tr><td>&nbsp;</td><td>&nbsp;</td></tr>
	<tr>
		<td background="#EBEBEB" align="right" valign="top" style="border-top: 1px solid black; padding: 5 5 2 5;">
			<strong>已有文章附件</strong>
		</td>
		<td background="#EBEBEB" style="border-top: 1px solid black; padding: 5 5 2 5;">
			<c:forEach var="att" items="${model.attachmentses}">
	  	  	  <div id="attachments${att.id }">
	  	  	  <a href="${ctx}${att.path}" target="_blank">
	  	      <img src="${ctx}/images/icons/attachment.gif" />&nbsp;<font size="2">${att.name}</font>
	  	     </a>&nbsp;
	  	     <a href="#" onclick="javascript:deleteAttachment(${att.id });"><img src="${ctx}/images/icons/delete.gif"/></a>
	  	     <br><br></div>
	  	    </c:forEach>&nbsp;
	  	</td>
	</tr>
</table>
