<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp" %>
<html>
<head>
<%@include file="/common/ec.jsp"%>
<%@include file="/common/meta.jsp" %>
<%@include file="/common/dwr.jsp" %>
<script type="text/javascript" src="${ctx}/dwr/interface/TemplateDwrAction.js"></script>
<title></title>
<script type="text/javascript">
function onRemove() {
    var sels = document.getElementsByName("selFileList");
    var checked = false;;
    for(i = 0; i < sels.length; i++) {
        if(sels[i].checked) {
           checked = true;
           break;
        }
    } 
    if(!checked) {
        alert('请至少选择一个文件或目录。');
        return;
    }
    if(confirm("您确定删除文件或目录吗？")) {
    	var files = new Array();
	    var j=0;
	    for (var i = 0; i < sels.length; i ++) {
	    	if (sels[i].checked) {
	    		files[j] = sels[i].value;
	    		j++;
	    	}
	    }
    	TemplateDwrAction.delFiles(files,
    		function (msg) {
    			if (msg != "true") {
    				alert(msg);
    			}
	    		ECSideUtil.reload('ec');
    		}
    	
    	);
    } 
}

/*增加文件输入框 -上传文件使用*/
function addFileInput(){
	var oFileInputTable = document.getElementById("fileUpload");
	var fileIndex = oFileInputTable.childNodes.length + 1;
	var oTR  = document.createElement("TR");
	var oTD2 = document.createElement("TD");
	
	oTR.setAttribute("id","file_"+fileIndex);    
	oTD2.setAttribute("style","text-align:left;");
	oTD2.innerHTML = '<s:file name="res" cssStyle="width:250" theme="simple" cssClass="FileText"></s:file>&nbsp;<A href="javascript:deleteFileInput('+fileIndex+');"><img src="${ctx}/images/icons/delete.gif"></A>';
	    
	oTR.appendChild(oTD2);
	oFileInputTable.appendChild(oTR);
}

/**删除文件输入框-上传文件使用*/
function deleteFileInput(childId) {
	var oTR = document.getElementById("file_"+childId);
	var oFileInputTable = document.getElementById("fileUpload");;
	oFileInputTable.removeChild(oTR);
}

/*判断文件夹是否存在*/
function dirExists(dirName) {
	alert(dirName);
	TemplateDwrAction.dirExists(dirName, 
		function (isExists) {
			if (isExists) {
				$("msg").innerHTML = '<font color="green">目录已存在</font>';
			} else {
				$("msg").innerHTML = '<font color="red">目录不存在</font>';
			}
		}
	);
}

/* 创建文件夹 */
function mkDir() {
	var dirName = document.getElementById("createFolder").value;
	if (dirName == null || dirName == ""){
		return alert("输入要创建的目录名称");
	}
	var parentName = "${viewPath}";
	TemplateDwrAction.mkdir(dirName, parentName,
		function (msg) {
			if (msg == "success") {
				alert("目录创建成功");
				window.location.href="filesList.do?respath=" + parentName.substring(1);
			} else {
				alert(msg);;
			}
		}
	);
}

/* 刷新功能 */
function refresh() {
	window.location.href="filesList.do?respath=" + $("resFilePath").value;
}
</script>
</head>
<body>
<table width="100%" border="0" cellpadding="0" cellspacing="0">
	<tr bgcolor="#EBEBEB">
		<td colspan="2" style="padding: 10 5 5 5; border-bottom: 1px white solid;">
			<s:if test="pathNotExists">
				<img src="${ctx}/images/icons/warning.gif">
				<font color="red">未找到指定的文件或目录...</font>	
			</s:if>
			<s:else>
				<img src="${ctx}/images/icons/folder_go.gif">
				<font color="green">当前目录：<b>${viewPath}</b></font>
			</s:else>		
			<input type="text" id="createFolder" name="createFolder" style="margin-left: 50px">
			<a href="#" onclick="mkDir()">
				<img src="${ctx}/images/icons/folder_add.gif">创建目录
			</a>
			<s:if test="#attr.parentFile.name != null">
				<a href="filesList.do?respath=${parentFile.name}">
				<img src="${ctx}/images/icons/arrow_turn_left.gif"/>返回上级</a>			
			</s:if>
			<s:else>
				<a href="filesList.do">
				<img src="${ctx}/images/icons/arrow_turn_left.gif"/>返回根目录</a>	
			</s:else>
			<a href="#" onClick="onRemove()"><img src="${ctx}/images/icons/delete.gif"/> 删除文件</a>		
		</td>
		
	</tr>
</table>
<table width="920" border="0" cellpadding="0" cellspacing="0">
	<tr>
		<td>
			<ec:table items="items" var="item" retrieveRowsCallback="process" sortRowsCallback="process"
				action="filesList.do"
				useAjax="false" doPreload="false"
				pageSizeList="10,50,100,200" 
				editable="false" 
				sortable="true"	
				rowsDisplayed="50"	
				generateScript="true"	
				resizeColWidth="true"	
				classic="false"
				width="620" 	
				height="400"	
				minHeight="400"  
				toolbarContent="navigation|pagejump|pagesize|refresh|extend|status">
				<ec:row>
					<ec:column width="50" property="_s" title="选择" style="text-align:center" sortable="false">
						<input id="selFileList" type="checkbox" name="selFileList" value='${item.reqUrl}'/>
					</ec:column>
					<ec:column width="150" property="name" title="文件/目录名称" >
						<s:if test="#attr.item.directory">
							<a href="filesList.do?respath=${item.path}">${item.name}</a>						</s:if>
						<s:else>
							<font color="gray"><a target="_blank" href="${ctx}/${item.reqUrl}">${item.name}</font>
						</s:else>
					</ec:column>
					<ec:column width="320" property="reqUrl" title="应用路径" style="text-align:left" />
					<ec:column width="80" property="size" title="文件大小"
						style="text-align:center" />
				</ec:row>
			</ec:table>
			</td>
			
			<td valign="top" align="left" style=" border-left-style:solid; border-left-width: 1px; ">
			<s:form action="saveResFiles" namespace="/admin/template" method="post" enctype="multipart/form-data"  theme="simple">
				<input type="hidden" id="respath" name="respath" value="${respath}"/>
				<table width="300" border="0" style="margin-left: 5px; margin-right: 5px">
					<tr>
						<td style="border-bottom: 1px solid black;">
							<b>上传资源文件</b>&nbsp;&nbsp;&nbsp;&nbsp;
							<a href="#" onClick="javascript:addFileInput();">
								<img src="${ctx}/images/icons/file_add.gif">&nbsp;增加文件
							</a>
						</td>
					</tr>
					<tr><td>
						<tbody id="fileUpload"></tbody>
					</td></tr>
					<tr>
						<td style="border-top: 1px solid black; padding-right: 10px" align="right">
							<s:submit value="上传" cssClass="button" theme="simple"/>
						</td>
					</tr>
				</table>
			</s:form>
		</td>
	</tr>
</table>
</body>
</html>