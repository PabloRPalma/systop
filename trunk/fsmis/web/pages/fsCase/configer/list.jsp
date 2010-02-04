<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<html>
<head>
<%@include file="/common/ec.jsp"%>
<%@include file="/common/extjs.jsp" %>
<%@include file="/common/meta.jsp"%>
<title>多体事件汇总配置列表</title>
<script type="text/javascript">
function remove(id){
	Ext.MessageBox.confirm('提示','确认要废止所选择的配置信息吗？', function(btn){
        if (btn == 'yes') {
        	location.href = "abolish.do?model.id=" + id;
        }
    });
}

function start(id){
	Ext.MessageBox.confirm('提示','确认要启用所选择的配置信息吗？', function(btn){
        if (btn == 'yes') {
        	location.href = "start.do?model.id=" + id;
        }
    });
}
</script>
</head>
<body>
<div class="x-panel">
  <div class="x-panel-header">多体事件汇总配置列表</div>
  <div class="x-toolbar">
     <table width="100%">
       <tr>
             <td style=" padding-left:5px; padding-top:5px;" align="right">   
               <a href="${ctx}/configuration/edit.do"> 添加</a>&nbsp;&nbsp;
             </td>
        </tr>
     </table>
   </div>
   <div class="x-panel-body">
     <div style="margin-left:-3px;" align="center">
     <ec:table items="items" var="item" retrieveRowsCallback="process" sortRowsCallback="process"
	   action="index.do" 
	   useAjax="true"
	   doPreload="false"
	   pageSizeList="20,50,100" 
	   editable="false" 
	   sortable="true"
	   rowsDisplayed="20" 
	   generateScript="true" 
	   resizeColWidth="true"
	   classic="false" 
	   width="100%" 
	   height="460px" 
	   minHeight="460"
	   toolbarContent="navigation|pagejump|pagesize|refresh|extend|status">
	   <ec:row>
		<ec:column width="40" property="_o" title="No." value="${GLOBALROWCOUNT}" style="text-align:center" sortable="false"/>
		<ec:column width="150" property="level"  mappingItem="levelMap" title="汇总配置级别" style="text-align:center" />
		<ec:column width="150" property="keyWord" title="关键字" />
		<ec:column width="60" property="records" title="记录数" style="text-align:center" />
		<ec:column width="60" property="days" title="天数" style="text-align:center"/>
		<ec:column width="80" property="isUse" title="是否可用" mappingItem="useMap" style="text-align:center"/>
		<ec:column width="140" property="_0" title="操作" style="text-align:center" sortable="false">
			<a href="edit.do?model.id=${item.id}">
			           编辑
			</a>|
			<a href="#" onclick="start(${item.id})">
			            启用
			</a>
			|
			<a href="#" onclick="remove(${item.id})">
			             废止
			</a>
			</ec:column>
 		
	  </ec:row>
    </ec:table>
    </div>
  </div>
</div>
</body>
</html>