<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<html>
<head>
<title>事件信息</title>
<%@include file="/common/extjs.jsp"%>
<%@include file="/common/meta.jsp"%>
<style type="text/css">
.mytable {
  border:2;
	width: 100%;
	margin-top: 5px;
	margin-bottom: 5px;
}
.mytable td {
	border: 0px solid #A6C9E2;
	margin-top: 2;
	margin-bottom: 2;
	padding: 2px 2px 2px 2px;
}
</style>
</head>
<body>
<!-- 事件信息 -->
<div class="x-panel" style="width: 100%">
	<div class="x-panel-header">事件信息</div>
	<div class="x-toolbar">
		<table width="100%">
			<tr>
				<td width="80%" align="left"></td>
				<td align="right"><a href="#"> 地理位置</a></td>
				<c:if test="${model.status eq '0' || model.status eq '3'}">
				<td><span class="ytb-sep"></span></td>
				    <td align="right"><a href="#"> 上报市级</a></td>
				</c:if>
				<c:if test="${model.status eq '0'}">
				<td><span class="ytb-sep"></span></td>
					<td align="right"><a href="#" onclick="showChooseSendTypeWindow(${model.id})"> 任务派遣</a></td>
				</c:if>
				<c:if test="${model.status eq '0' || model.status eq '3'}">	
				<td><span class="ytb-sep"></span></td>	
				    <c:if test="${empty model.assessmentses}">
				    <td align="right"><a href="${ctx}/assessment/edit.do?model.fsCase.id=${model.id}"> 风险评估</a></td>
				    </c:if>
				    <c:if test="${!empty model.assessmentses}">
				    <td align="right"><a href="${ctx}/assessment/view.do?model.id=<c:forEach items='${model.assessmentses}' var='assessment'>${assessment.id}</c:forEach>"> 查看风险评估</a></td>
				    </c:if>
				</c:if>
				<c:if test="${empty model.jointTaskses && (model.status eq '0' || model.status eq '3')}">
				<td><span class="ytb-sep"></span></td>
				    <td align="right"><a href="${ctx}/jointTask/edit.do?model.fsCase.id=${model.id}"> 联合整治</a></td>
				</c:if>
			</tr>
		</table>
	</div>
	<div id="tabs" style="width: 100%">
		<div id="fsCaseDiv" class="x-hide-display">			
			<table width="100%">		
				<tr>
					<td  width="10%"align="right">案件标题：</td>
					<td  align="left" >${model.title}</td>
				</tr>
				<tr>
					<td width="10%"  align="right">案件类别：</td>
					<td align="left" >${model.caseType.name}</td>
				</tr>
				<tr>
					<td width="10%"  align="right">案件地点：</td>
					<td align="left" >${model.address}</td>
				</tr>
				<tr>
					<td width="10%"  align="right">事发时间：</td>
					<td align="left" ><s:date name="model.caseTime"format="yyyy-MM-dd" /></td>
				</tr>
				<tr>
					<td width="10%"  align="right">案件报告人：</td>
					<td  align="left">${model.informer}</td>
				</tr>
				<tr>
					<td width="10%"  align="right">报告人电话：</td>
					<td  align="left">${model.informerPhone}</td>
				</tr>
				<tr>
					<td width="10%"  align="right">案件描述：</td>
					<td  align="left">${model.descn}</td>
				</tr>						
			</table>
		</div>
		<c:if test="${model.isMultiple eq '1'}">		
		<div id="general" class="x-hide-display"  style="background-color: white; z-index: 100;">
			<!-- include进来二级Tab以现实一个食品安全综合案件关联的多个一般案件 -->		
			<%@include	file="viewGenericCases.jsp"%>			
		</div>
		</c:if>
		<c:if test="${not empty model.taskses }">	
		<div id="tasks" class="x-hide-display" style="margin: -1; border: 0; width: 100%;">
			<!-- include进来二级Tab以现实一个食品安全案件下的多个任务 -->		
			<%@include file="viewTasks.jsp"%>
		</div>
		</c:if>
		<div id="sms" class="x-hide-display" style="width: 100%; background-color: white; z-index: 1000;">
		<%@include file="viewSmsGrid.jsp" %>
		</div>
		<c:if test="${not empty model.corp}">
			<div id="corp" class="x-hide-display" style="width: 100%;">		
				<table width="100%">		
					<tr>
						<td  width="10%"align="right">企业名称：</td>
						<td  align="left" >${model.corp.name}</td>
					</tr>
					<tr>
						<td width="10%"  align="right">企业编号：</td>
						<td align="left" >${model.corp.code} </td>
					</tr>
					<tr>
						<td width="10%"  align="right">地址：</td>
						<td align="left" >${model.corp.address}</td>
					</tr>
					<tr>
						<td width="10%"  align="right">法人：</td>
						<td align="left" >${model.corp.legalPerson}</td>
					</tr>
					<tr>
						<td width="10%"  align="right">生产许可证：</td>
						<td  align="left">${model.corp.produceLicense}</td>
					</tr>
					<tr>
						<td width="10%"  align="right">卫生许可证：</td>
						<td  align="left">${model.corp.sanitationLicense}</td>
					</tr>
					<tr>
						<td width="10%"  align="right">经营范围：</td>
						<td  align="left">${model.corp.operateDetails}</td>
					</tr>						
			</table>			
	</div>
		</c:if>
	</div>
</div>
<%@include file="chooseSendType.jsp"%>
<%@include file="viewTaskDetailResult.jsp"%>
<%@include file="viewTaskDetailReturn.jsp"%>
<script type="text/javascript">
	Ext.onReady(function() {
		var tabs = new Ext.TabPanel( {
			renderTo : 'tabs',
			anchor : '100% 100%',
			activeTab : ${param['modelId']},
			frame : false,
			defaults : {
				autoHeight : true
			},
			items : [ {
				contentEl : 'fsCaseDiv',
				title : '事件信息'
			}
			<c:if test="${model.isMultiple eq '1'}">			
			,{
				contentEl : 'general',
				title : '相关单体事件'
			}
			
			</c:if>
			<c:if test="${not empty model.taskses }">			
			,{
				contentEl : 'tasks',
				title : '任务信息'
			}
			
			</c:if>			
			, {
				contentEl : 'sms',
				title : '短信收发情况'
			} 
			<c:if test="${not empty model.corp}">
			, {
				contentEl : 'corp',
				title : '关联企业'
			} 
			</c:if>
			]
		});
	});
</script>
<script type="text/javascript">
Ext.grid.TableGrid = function(table, config) {
	  config = config || {};
	  Ext.apply(this, config);
	  var cf = config.fields || [], ch = config.columns || [];
	  table = Ext.get(table);

	  var ct = table.insertSibling();

	  var fields = [], cols = [];
	  var headers = table.query("thead th");
	  for (var i = 0, h; h = headers[i]; i++) {
	    var text = h.innerHTML;
	    var name = 'tcol-'+i;

	    fields.push(Ext.applyIf(cf[i] || {}, {
	      name: name,
	      mapping: 'td:nth('+(i+1)+')/@innerHTML'
	    }));

	    cols.push(Ext.applyIf(ch[i] || {}, {
	      'header': text,
	      'dataIndex': name,
	      'width': h.offsetWidth,
	      'tooltip': h.title,
	      'sortable': true
	    }));
	  }

	  var ds  = new Ext.data.Store({
	    reader: new Ext.data.XmlReader({
	      record:'tbody tr'
	    }, fields)
	  });

	  ds.loadData(table.dom);

	  var cm = new Ext.grid.ColumnModel(cols);

	  if (config.width || config.height) {
	    ct.setSize(config.width || 'auto', config.height || 'auto');
	  } else {
	    ct.setWidth(table.getWidth());
	  }

	  if (config.remove !== false) {
	    table.remove();
	  }

	  Ext.applyIf(this, {
	    'ds': ds,
	    'cm': cm,
	    'sm': new Ext.grid.RowSelectionModel(),
	    autoHeight: true,
	    autoWidth: false
	  });
	  Ext.grid.TableGrid.superclass.constructor.call(this, ct, {});
	};

	Ext.extend(Ext.grid.TableGrid, Ext.grid.GridPanel);
</script>
</body>
</html>