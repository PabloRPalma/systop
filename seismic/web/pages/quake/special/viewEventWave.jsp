<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp" %>
<html>
<head>
<title></title>
<%@include file="/common/quake.jsp" %>
</head>
<body>	
<div class="x-panel">
<div class="x-panel-body">
<div style="margin-left: -3px;" align="center">
<ec:table items="waveItems" var="item" retrieveRowsCallback="process" sortRowsCallback="process" 
	action="getEventWave.do"
	useAjax="true" doPreload="false"
	maxRowsExported="1000" 
	pageSizeList="30,60,80,100" 
	editable="false" 
	sortable="false"	
	rowsDisplayed="20"	
	generateScript="true"	
	resizeColWidth="true"	
	classic="false"	
	width="100%" 	
	height="340px"	
	minHeight="340"  
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
	 <c:if test="${!empty waveItems}">	   
	<ec:extend>
				<div align="right" style="padding-right: 10px;">
					<div id="watch" style="cursor: hand;" onclick="return post();">
						<span style="color: green; font-weight: bold;">查看波形图</span>
					</div>
				</div>
			</ec:extend> 
			 </c:if> 
</ec:table>
</div>
</div>
</div>
<s:form id="queryFrm"></s:form>	
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
		if(ids.length != 0){
			queryFrm.action = "${ctx}/quake/seismic/data/seed/view.do?id=" + ids;
		}
			
		queryFrm.target="_blank";
		queryFrm.submit();
	}
</script>
</html>