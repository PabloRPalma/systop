<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<html>
<head>
<title>事件信息</title>
<%@include file="/common/ec.jsp"%>
<%@include file="/common/extjs.jsp"%>
<%@include file="/common/meta.jsp"%>
<script type="text/javascript">
var initWidth = 0;
</script>
<style type="text/css">
.mytable{
	width:600px;
	margin-top:10px;
}
.title {
	padding:6 5 3 5;
	width:100px;
	font-size:12px;
	font-weight: bold;
	text-align: right;
}
.content {
	padding:6 5 3 5;
	font-size:12px;
	text-align: left;
}
/*去掉粗边框的样式*/
.x-tab-panel{
  margin: -1;
}
/*设置GridPanel空行背景为*/
.x-panel-mc{
 background-color: white;	
 margin-top: -4;
 }
</style>
</head>
<body>
<!-- 事件信息 -->
<div class="x-panel" style="width: 100%">
	<div class="x-panel-header"style="width: 100%">事件信息</div>
	<div class="x-toolbar" style="padding: 5 5 0 5;width: 100%">
		<table width="100%">
			<tr>
				<td width="90%" align="left"></td>
				<td align="right"> <a href="#">地理位置</a></td>
				<c:if test="${model.caseSourceType eq 'generic'}">				
					<c:if test="${model.status eq '0' || model.status eq '2' || model.status eq '3'}">
					<td><span class="ytb-sep"></span></td>
					    <td align="right"><a href="#"> 上报市级</a></td>
					</c:if>
					<c:if test="${model.status eq '0' || model.status eq '2' || model.status eq '3'}">
					<td><span class="ytb-sep"></span></td>
						<td align="right"><a href="#" onclick="showChooseSendTypeWindow(${model.id},'${model.title}')"> 任务派遣</a></td>
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
					<c:if test="${empty model.jointTaskses && (model.status eq '0' || model.status eq '2' || model.status eq '3')}">
					<td><span class="ytb-sep"></span></td>
					    <td align="right"><a href="${ctx}/jointTask/edit.do?model.fsCase.id=${model.id}"> 联合整治</a></td>
					</c:if>					
				</c:if>
				<c:if test="${model.caseSourceType eq 'jointask'}">		
					<c:if test="${empty model.jointTaskses && (model.status eq '0' || model.status eq '2' || model.status eq '3')}">
					<td><span class="ytb-sep"></span></td>
					    <td align="right"><a href="${ctx}/jointTask/edit.do?model.fsCase.id=${model.id}"> 联合整治</a></td>
					</c:if>					
				</c:if>				
			</tr>
		</table>
	</div>
	<div id="tabs" style="width: 100%;">
		<div id="fsCaseDiv" class="x-hide-display" style="width: 100%">			
			<table class="mytable" align="center" cellpadding="0" cellspacing="0">		
				<tr>
					<td class="title">事件标题:</td>
					<td class="content" colspan="3"><b>${model.title}</b></td>
				</tr>
				<tr>
					<td class="title">事件类别:</td>
					<td class="content" colspan="3">${model.caseType.name}</td>
				</tr>
				<tr>
					<td class="title">事发时间:</td>
					<td class="content" width="100"><s:date name="model.caseTime"format="yyyy-MM-dd" /></td>
					<td class="title" width="300">事件地点:</td>
					<td class="content">${model.address}</td>
				</tr>
				<tr>
					<td class="title">事件报告人:</td>
					<td class="content">${model.informer}&nbsp;</td>
					<td class="title">报告人电话:</td>
					<td class="content">${model.informerPhone}&nbsp;</td>
				</tr>
				<tr>
					<td class="content" colspan="4" style="border-top:1px dashed #99BBE8; padding: 4 10 4 10;">
						${model.descn}
					</td>
				</tr>						
			</table>
		</div>
		<c:if test="${model.isMultiple eq '1'}">		
		<div id="general" class="x-hide-display"  style="z-index: 100;width: 100%">
			<!-- include进来二级Tab以现实一个食品安全综合案件关联的多个一般案件 -->		
			<%@include	file="viewGenericCases.jsp"%>			
		</div>
		</c:if>
		<c:if test="${not empty model.taskses }">	
		<div id="tasks" class="x-hide-display" style="z-index: 100;width: 100%">	
			<!-- include进来二级Tab以现实一个食品安全案件下的多个任务 -->		
			<%@include file="viewTasks.jsp"%>
		</div>
		</c:if>
		<div id="sms" class="x-hide-display" style="z-index: 100;width: 100%">
		<%@include file="viewSmsGrid.jsp" %>
		</div>
		<c:if test="${not empty model.corp}">
			<div id="corp" class="x-hide-display" style="width: 100%;">		
				<table  class="mytable" align="center" cellpadding="0" cellspacing="0">	
					<tr>
						<td class="title">企业名称：</td>
						<td class="content" colspan="3" >${model.corp.name}</td>
					</tr>
					<tr>
						<td class="title">企业编号：</td>
						<td class="content">${model.corp.code} </td>
						<td class="title">法人：</td>
						<td class="content"  >${model.corp.legalPerson}</td>
					</tr>
					<tr>
						<td class="title">地址：</td>
						<td class="content" colspan="3" >${model.corp.address}</td>
					</tr>
					<tr>
						<td class="title">营业执照：</td>
						<td class="content">${model.corp.businessLicense}</td>
						<td class="title">有效期：</td>
						<td class="content"><s:date name="model.corp.businessLicenseIndate" format="yyyy-MM-dd HH:mm"/></td>
					</tr>					
					<tr>
						<td class="title">生产许可证：</td>
						<td class="content" >${model.corp.produceLicense}</td>
						<td class="title">有效期：</td>
						<td class="content"><s:date name="model.corp.produceLicenseIndate" format="yyyy-MM-dd HH:mm"/></td>
					</tr>
					<tr>
						<td class="title"  align="right">卫生许可证：</td>
						<td  class="content">${model.corp.sanitationLicense}</td>
						<td class="title">有效期：</td>
						<td class="content"><s:date name="model.corp.sanitationLicenseIndate" format="yyyy-MM-dd HH:mm"/></td>
					</tr>
					<tr>
						<td class="title"  align="right">固话：</td>
						<td  class="content">${model.corp.phone}</td>
						<td class="title">手机：</td>
						<td class="content">${model.corp.mobile}</td>
					</tr>
						<tr>
						<td class="title"  align="right">诚信等级：</td>
						<td  class="content">${model.corp.integrityGrade}</td>
						<td class="title">邮编：</td>
						<td class="content">${model.corp.zip}</td>
					</tr>
					<tr>
						<td class="title" valign="top">经营范围：</td>
						<td class="content" valign="top"colspan="3"><div style="overflow: auto; width:100%; height: 20px;">${model.corp.operateDetails}</div></td>
					</tr>			
					<tr>
						<td class="title" valign="top">备注：</td>
						<td class="content" valign="top"colspan="3"><div style="overflow: auto; width:100%; height: 20px;">${model.corp.remark}</div></td>
					</tr>	
					<tr>
						<td class="title" valign="top">企业简介：</td>
						<td class="content" valign="top"colspan="3"><div style="overflow: auto; width:100%; height: 20px;">${model.corp.descn}</div></td>
					</tr>				
			</table>			
	</div>
		</c:if>
	</div>
</div>
<%@include file="chooseSendType.jsp"%>
<%@include file="viewGenericCaseResult.jsp"%>
<%@include file="viewTaskDetailResult.jsp"%>
<%@include file="viewTaskDetailReturn.jsp"%>
<script type="text/javascript">
	Ext.onReady(function() {
		var tabs = new Ext.TabPanel( {
			layoutOnTabChange : true ,
			renderTo : 'tabs',
			anchor : '100% 100%',
			activeTab : ${param['modelId']},
			frame : false,
			defaults : {
				autoHeight : true,
				autoWidth : true
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
				title : '短信收发情况',
				listeners:{
					activate:function(tab){
					}
				}
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