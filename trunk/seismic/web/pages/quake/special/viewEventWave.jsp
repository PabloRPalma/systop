<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@include file="/common/taglibs.jsp" %>

<html>
<head>
<title></title>
<%@include file="/common/quake.jsp" %>
<link type="text/css" href="${ctx}/scripts/jquery/ui/css/jquery-ui-1.7.1.css"	rel="stylesheet" />
<script type="text/javascript"	src="${ctx}/scripts/jquery/bgiframe/jquery.bgiframe.js"></script>
<script type="text/javascript"	src="${ctx}/scripts/jquery/ui/jquery-ui-1.7.1.js"></script>
<script type="text/javascript">
$(function() {
	$("#queryFrm").validate();
});
</script>
<style type="text/css">
fieldset {
	border: 1px solid #97b7e7;
	padding: 2px;
	margin: 10px;
}
</style>
</head>
<body>
<s:form id="queryFrm" theme="simple">
		<table width="98%" align="center">
			<tr>
				<td width="85%">&nbsp;</td>			
		  	    <td>	   
		  	      <input type="button" align="right" value="查看波形图" onclick="return post();"  class="button"/> 	  
		  	    </td>
			</tr>			
		</table>
</s:form>

<table width="98%">
  <tr>
    <td>
<ec:table items="items" var="item"  retrieveRowsCallback="process" sortRowsCallback="process" 
	action="getEventWave.do"
	useAjax="true" doPreload="false"
	maxRowsExported="10000000" 
	pageSizeList="30,60,80,100" 
	editable="false" 
	sortable="false"	
	rowsDisplayed="30"	
	generateScript="true"	
	resizeColWidth="true"	
	classic="false"	
	width="100%" 	
	height="157px"	
	minHeight="157"
	toolbarContent="navigation|pagejump|pagesize|refresh|extend|status"     
	>
	<ec:row>
		<ec:column width="40" property="_0" title="序号" value="${GLOBALROWCOUNT}" style="text-align:center"/>
		<ec:column width="200" property="SeedFile" title="SEED"/>	
		<ec:column width="80" property="Station" title="台站名" style="text-align:center"/>	
		<ec:column width="80" property="_9" title="&nbsp;<input type='checkbox' onclick='selecteAll(this);'/>" style="text-align:center" >			
			<input type="checkbox" name="selectedItems" value="${item.ID}"/>
		</ec:column>
	</ec:row>   
</ec:table>
    </td>
  </tr>
</table>
</body>
<script type="text/javascript">	
	function selecteAll(val) {
     var checkBox = document.getElementsByName("selectedItems");
     if (val.checked) {
       for (var i=0;i<checkBox.length;i++) {
	      if (!checkBox[i].disabled) {
	         checkBox[i].checked = 'checked';
	      }
       }
     } else {
       for (var i=0;i<checkBox.length;i++) {
	      if (checkBox[i].checked = 'checked') {
	         checkBox[i].checked = '';
	      }
       }
     }
   }
   
	function post(){
		var ids = new Array();
		var obj = document.getElementsByName('selectedItems');
		for(i = 0,j=0;i < obj.length;i++){
			if(obj[i].type == 'checkbox'){
				if(obj[i].checked){
					ids[j] = obj[i].value;
					j++;
				}				
			}
			if(i == obj.length -1 && j == 0){
					alert('请至少选择一个台站！');
					return false;
			}
		}
		if(ids.length != 0)
			queryFrm.action = "${ctx}/quake/seismic/data/seed/view.do?id=" + ids;
			
		queryFrm.target="_blank";
		queryFrm.submit();
	}
</script>
</html>