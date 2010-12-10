<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<html>
<head>
<title>专题地震</title>
<%@include file="/common/ec.jsp"%>
<LINK type="text/css" rel="stylesheet" href="${ctx}/styles/quake.css">
<%@include file="/common/extjs.jsp"%>
<%@include file="/common/meta.jsp"%>
</head>
<script type="text/javascript">
	Ext.onReady(function() {
		var tabs = new Ext.TabPanel( {
			renderTo : 'tabs',
			anchor : '100% 100%',
			height : 560,
			width : '99.9%',
			activeTab : 0,
			frame : false,
			defaults : {
				autoHeight : false
			},
			items : [ {
				contentEl : 'basic',
				title : '地震参数'
			},{
				contentEl : 'area',
				title : '影响地区'
			}, {
				contentEl : 'epifocus',
				title : '震中分布图'
			}, {
				contentEl : 'history_pic',
				title : '历史地震分布图'
			}, {
				contentEl : 'm_t',
				title : '震中区M-T图'
			}
			, {
				contentEl : 'mechanism',
				title : '震源机制解'
			}
			, {
				contentEl : 'fracture',
				title : '震源破裂过程'
			}
			, {
				contentEl : 'intensity',
				title : '烈度分布'
			}
			, {
				contentEl : 'wave',
				title : '波形图'
			}
			, {
				contentEl : 'phase',
				title : '震相数据'
			}  ]
		});
	});
</script>
<body>
<div class="x-panel" style="width: 99.99%">
<div class="x-panel-header">查看专题地震</div>
<div id="tabs" style="margin-top: 0px;margin-left: 0px;margin-right: 0px;">
<div id="basic" class="x-hide-display" >
	<table>
		<tr>
		<td width="700" valign="top">
				<table>
					<tr>
						<td>&nbsp;</td>
						<td>&nbsp;</td>
					</tr>
					<tr>
						<td>地震目录:</td>
						<td>${catalogName}</td>
					</tr>
					<tr>
						<td>&nbsp;</td>
						<td>&nbsp;</td>
					</tr>
					<tr>
						<td>地  点:</td>
						<td>${model.location}</td>
					</tr>
					<tr>
						<td>&nbsp;</td>
						<td>&nbsp;</td>
					</tr>
					<tr>
						<td>经  度:</td>
						<td>${model.longitude}</td>
					</tr>
					<tr>
						<td>&nbsp;</td>
						<td>&nbsp;</td>
					</tr>
					<tr>
						<td>纬  度:</td>
						<td>${model.latitude}</td>
					</tr>
					<tr>
						<td>&nbsp;</td>
						<td>&nbsp;</td>
					</tr>
					<tr>
						<td>震  级:</td>
						<td>${model.magnitude}</td>
					</tr>
					<tr>
						<td>&nbsp;</td>
						<td>&nbsp;</td>
					</tr>
					<tr>
						<td>深 度:</td>
						<td>${model.depth}</td>
					</tr>
					<tr>
						<td>&nbsp;</td>
						<td>&nbsp;</td>
					</tr>
					<tr>
						<td>发震时刻:</td>
						<td>${model.quakeTime}</td>
					</tr>	
					<tr>
						<td>&nbsp;</td>
						<td>&nbsp;</td>
					</tr>	
				</table>
			</td>
			<td width="325">
				<table>
					<tr>
						<td width="80">专题图片:</td>
						<td>
							<c:if test="${not empty model.front_pic}">
								<img width="225" height="175" src="${ctx}/${model.front_pic}"/>
							</c:if> 
							<c:if test="${empty model.front_pic}">
								<img width="225" height="175" src="${ctx}/images/nophoto.gif" />
							</c:if>
						</td>
					</tr>		
				</table>
			</td>
			
		</tr>
		<tr>
			<td colspan="2">
				<table>
					<tr>
						<td width="80">专题标题:</td>
						<td>${model.title} </td>
					</tr>
					<tr>
						<td>&nbsp;</td>
						<td>&nbsp;</td>
					</tr>	
					<tr>
						<td valign="top">地震描述:</td>
						<td><div style="overflow: auto;height: 400px;">${model.desn}</div> </td>
					</tr>
				</table>
			</td>
		</tr>
	</table>	
</div>
<div id="area" class="x-hide-display">
<table id="mytable" height="380" style="margin-top: 5px;width: 800px" align="center">
	<tr>
		<td width="15%" align="right" valign="top">影响地区：</td>
		<td align="left" style="vertical-align: top;" width="85%">
		<div style="overflow: auto;height: 480px;">${model.area}</div>
		</td>
	</tr>
</table>
</div>
<div id="epifocus" class="x-hide-display">
<table id="mytable" height="380" style="margin-top: 5px;width: 800px" align="center">
	<tr>
		<td width="15%" align="right" valign="top">震中分布图：</td>
		<td align="left" style="vertical-align: top;" width="85%">
		<div style="overflow: auto;height: 480px;">${model.epifocus}</div>
		</td>
	</tr>
</table>
</div>
<div id="history_pic" class="x-hide-display">
<table id="mytable" height="380" style="margin-top: 5px;width: 800px" align="center">
	<tr>
		<td width="15%" align="right" valign="top">历史地震分布图：</td>
		<td align="left" style="vertical-align: top;" width="85%">
		<div style="overflow: auto;height: 480px;">${model.history_pic}</div>
		</td>
	</tr>
</table>
</div>
<div id="m_t" class="x-hide-display">
<table id="mytable" height="380" style="margin-top: 5px;width: 800px" align="center">
	<tr>
		<td width="15%" align="right" valign="top">震中区M-T图：</td>
		<td align="left" style="vertical-align: top;" width="85%">
		<div style="overflow: auto;height: 480px;">${model.m_t}</div>
		</td>
	</tr>
</table>
</div>
<div id="mechanism" class="x-hide-display">
<table id="mytable" height="380" style="margin-top: 5px;width: 800px" align="center">
	<tr>
		<td width="15%" align="right" valign="top">震源机制解：</td>
		<td align="left" style="vertical-align: top;" width="85%">
		<div style="overflow: auto;height: 480px;">${model.mechanism}</div>
		</td>
	</tr>
</table>
</div>
<div id="fracture" class="x-hide-display">
<table id="mytable" height="380" style="margin-top: 5px;width: 800px" align="center">
	<tr>
		<td width="15%" align="right" valign="top">震源破裂过程：</td>
		<td align="left" style="vertical-align: top;" width="85%">
		<div style="overflow: auto;height: 480px;">${model.fracture}</div>
		</td>
	</tr>
</table>
</div>
<div id="intensity" class="x-hide-display">
<table id="mytable" height="380" style="margin-top: 5px;width: 800px" align="center">
	<tr>
		<td width="15%" align="right" valign="top">烈度分布：</td>
		<td align="left" style="vertical-align: top;" width="85%">
		<div style="overflow: auto;height: 480px;">${model.intensity}</div>
		</td>
	</tr>
</table>
</div>
<div id="wave" class="x-hide-display">
<table id="mytable" height="380" style="margin-top: 5px;width: 800px" align="center">
	<tr height="250">
		<td width="15%" align="right" valign="top">事件波形数据：</td>
		<td align="left" style="vertical-align: top;" width="85%">
		<div style="overflow: auto;height: 240px;">${model.event_wave}</div>
		</td>
	   </tr> 
	<tr>
		<td width="15%" align="right" valign="top">台站波形记录图：</td>
		<td align="left" style="vertical-align: top;" width="85%">
		<div style="overflow: auto;height: 240px;">${model.station_wave}</div>
		</td>
	 </tr>
</table>
</div>
<div id="phase" class="x-hide-display">
<div class="x-panel-body">
<ec:table items="items" var="item" retrieveRowsCallback="process" sortRowsCallback="process" 
	action="list.do"
	useAjax="true" doPreload="false"
	maxRowsExported="1000" 
	xlsFileName="震相数据列表.xls" 
	pageSizeList="20,60,80,100" 
	editable="false" 
	sortable="false"	
	rowsDisplayed="20"	
	generateScript="true"	
	resizeColWidth="true"	
	classic="false"	
	width="100%" 	
	height="360px"	
	minHeight="360"  
	toolbarContent="navigation|pagejump|pagesize|refresh|extend|status"     
	>
	<ec:row>
		<ec:column width="40" property="_0" title="序号" value="${GLOBALROWCOUNT}" style="text-align:center" sortable="false"/>
		<ec:column width="80" property="NET_CODE" title="台网代码"/>
		<ec:column width="80" property="STA_CODE" title="台站代码"/>
		<ec:column width="80" property="CHN_CODE" title="通道代码"/>	
		<ec:column width="80" property="REC_TYPE" title="记录类型"/>	
		<ec:column width="80" property="PHASE_NAME" title="震相名称"/>	
		<ec:column width="160" property="PHASE_TIME" title="震相到时" cell="date" format="yyyy-MM-dd HH:mm:ss"/>	
		<ec:column width="100" property="PHASE_TIME_FRAC" title="1/10000秒"/>	
		<ec:column width="100" property="AMP_TYPE" title="振幅类型"/>	
		<ec:column width="60" property="AMP" title="振幅" cell="quake.seismic.data.phase.webapp.cell.PhaseNumCell"/>	
		<ec:column width="80" property="PERIOD" title="周期" cell="quake.base.webapp.DoubleCell"/>	
		<ec:column width="40" property="WEIGHT" title="权重"/>	
		<ec:column width="80" property="CLARITY" title="初动清晰度"/>	
		<ec:column width="80" property="WSIGN" title="初动方向"/>	
		<ec:column width="120" property="RESI" title="残差"/>	
		<ec:column width="60" property="MAG_NAME" title="震级名"/>	
		<ec:column width="120" property="MAG_VAL" title="震级值" cell="quake.seismic.data.catalog.webapp.cell.EMCell"/>	
		<ec:column width="120" property="DISTANCE" title="震中距" cell="quake.seismic.data.catalog.webapp.cell.EMCell"/>	
		<ec:column width="120" property="AZI" title="方位角"/>	
		<ec:column width="120" property="S_P" title="S-P(sec)"/>		
	</ec:row>  
</ec:table>
</div>
</div>
</div>
</div>
</body>
</html>