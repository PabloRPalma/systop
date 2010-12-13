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
			height : 480,
			width : '100%',
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
<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td width="100%" height="126" align="center" valign="top"><img src="${ctx}/ResRoot/index/images/index_01.jpg" alt="" width="100%" height="126" border="0" usemap="#Map" />
      <map name="Map" id="Map">
        <area shape="rect" coords="818,20,871,38" href="${ctx}/index.shtml" />
        <area shape="rect" coords="884,20,938,38" href="#" onClick="addBookmark()"/>
      </map>    </td>
  </tr>  
</table>
<div class="x-panel">
<div id="tabs" align="center">
<div id="basic" class="x-hide-display" >
	<table> 
		<tr>
		<td width="500" valign="top">
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
			<td width="330">
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
			<td colspan="2" valign="top">
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
						<td><div style="overflow: auto;height: 140px;width: 900px">${model.desn}</div> </td>
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
	<tr height="220">
		<td width="15%" align="right" valign="top">事件波形数据：</td>
		<td align="left" style="vertical-align: top;" width="85%">
		<div style="overflow: auto;height: 210px;">${model.event_wave}</div>
		</td>
	   </tr> 
	<tr>
		<td width="15%" align="right" valign="top">台站波形记录图：</td>
		<td align="left" style="vertical-align: top;" width="85%">
		<div style="overflow: auto;height: 210px;">${model.station_wave}</div>
		</td>
	 </tr>
</table>
</div>
<div id="phase" class="x-hide-display">
<iframe height="450" src="${ctx}/admin/special/getPhaseList.do?specialId=${model.id}" width="100%" frameborder="0"></iframe>

</div>
</div>
</div>
</body>
</html>