<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<link type="text/css" rel="stylesheet"
	href="<c:url value="/styles/dojoTable.css"/>">
<link type="text/css" rel="stylesheet"
	href="<c:url value="/styles/fieldset.css"/>">
	
<div align="center">
		<fieldset class="fieldsetStyle" style="width:680px">
		<legend><fmt:message key="map.info"/></legend>
			<table width="100%">
				<tr>
					<td width="20%" rowspan="3" align="center" valign="middle">
						<img id="mapInfoImg" src="../images/icons/info-02.gif" style=" padding-top:5px;" width="60" height="60">
					</td>
					<td width="80%" align="left">
							<div id="mapInfoTable" style="display:none;">
								<table width="100%" cellspacing="2">
									<tr>
										<td width="25%" align="right"><fmt:message key='map.info.title'/><b>:</b>&nbsp;</td>
										<td width="55%" align="left"><div id="mapTitleDiv"></div></td>
										<td width="20%" rowspan="3" valign="bottom" align="right">
											<div id="optionButton">
												<!--  
													<a href="#" onclick="mapInfoRefresh()"><img src="../images/icons/refresh02.gif" alt="<fmt:message key='cms.refresh'/>"></a>&nbsp;												
												-->
												<a href="#" onclick="mapInfoEdit()"><img src="../images/icons/edit_3.gif" alt="<fmt:message key='global.edit'/>"><fmt:message key='global.edit'/></a>&nbsp;
												<a href="#" onclick="mapInfoRemove()"><img src="../images/icons/remove.gif" alt="<fmt:message key='global.remove'/>"><fmt:message key='global.remove'/></a>
											</div>
										</td>
									</tr>
									<tr>
										<td width="25%" align="right"><fmt:message key='map.info.sign'/><b>:</b>&nbsp;</td>
										<td width="55%" align="left"><div id="mapSignDiv"></div></td>
									</tr>
									<tr>
										<td width="25%" align="right" valign="top"><fmt:message key='map.info.descn'/><b>:</b>&nbsp;</td>
										<td width="55%" align="left"><div id="mapDescnDiv"></div></td>
									</tr>
								</table>
							</div>
							<div id="sysCatalogInfo"><fmt:message key='map.sysCatalogInfo'/></div>
							<div id="onMapDiv" style="display:none;"><fmt:message key='map.noMapView'/></div>
					</td>
				</tr>
			</table>
	</fieldset>
</div>	
<br>
<table align="center" width="700px" background="../images/top-bg1.gif">
  <tr>
    <td style="padding-top:2px; padding-left:10px">
	    <img src="<c:url value='/images/icons/add_0.gif'/>" width="14" height="14">
			<a href="#" onclick="createEntry()" title="<fmt:message key='entry.new'/>"><fmt:message key='global.new'/></a>
			&nbsp;&nbsp; 
			<img src="<c:url value='/images/icons/remove.gif'/>" width="14" height="14"> 
			<a href="#" onclick="reMoveEntry()" title="<fmt:message key='entry.del'/>"><fmt:message key='global.remove'/></a>
		</td>
  </tr>
</table>

<div class='dojoTable' align="center">
<table dojoType="FilteringTableEx" widgetId="entryTable" id="entryTable" minRows="0"
	valueField="viewText" defaultDateFormat="%D" headerClass="tableHeader"
	tbodyClass="tableBody" rowAlternateClass="odd"
	headerUpClass="tableHeaderSort" headerDownClass="tableHeaderSort"
	alternateRows="true" cellpadding="0" cellspacing="0" border="0"
	class='tableRegion' style="width:700px" align="center">
	<thead>
		<tr>
			<th field="toRemove" width="10%" dataType="String" class="tableHeader" align="center" noSort="true">
				<fmt:message key="global.select"/></th>
			<th field="viewText" width="32%" dataType="String" class="tableHeader" align="center" >
				<fmt:message key="entry.viewText"/></th>
			<th field="refValue" width="15%" dataType="String" class="tableHeader" align="center" >
				<fmt:message key="entry.refValue"/></th>
			<th field="entryDescn" width="38%" dataType="String" class="tableHeader" align="center" >
				<fmt:message key="entry.descn"/></th>
			<th field="entryEdit" width="10%" dataType="String" class="tableHeader" align="center" noSort="true">
				<fmt:message key="global.edit"/></th>
		</tr>
	</thead>
</table>
</div>