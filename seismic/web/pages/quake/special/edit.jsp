<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<html>
<head>
<title>专题地震</title>
<%@include file="/common/extjs.jsp"%>
<%@include file="/common/meta.jsp"%>
<%@include file="/common/validator.jsp"%>
<style type="text/css">
#mytable {
	border: 0px solid #A6C9E2;
	margin-left: 0px;
	margin-top: 0px;
	width: 100%;
	border-collapse: collapse;
}

#mytable td {
	border: 0px solid #A6C9E2;
	height: 28;
}
</style>
</head>
<script type="text/javascript">
//文本编辑组件
function preFckEditor(){
	epifocus();
	history_pic();
	m_t();
	mechanism();
	fracture();
	intensity();
	event_wave();
	station_wave();
}
function epifocus(){
	var fckEditor = new FCKeditor( 'epifocus' ) ;
	  fckEditor.BasePath = "${ctx}/scripts/fckeditor/";
	  fckEditor.ToolbarSet = 'Basic';
	  fckEditor.Height = 250;
	  fckEditor.Width = 600;
	  fckEditor.ReplaceTextarea();
}
function history_pic(){
	var fckEditor = new FCKeditor( 'history_pic' ) ;
	  fckEditor.BasePath = "${ctx}/scripts/fckeditor/";
	  fckEditor.ToolbarSet = 'Basic';
	  fckEditor.Height = 250;
	  fckEditor.Width = 600;
	  fckEditor.ReplaceTextarea();
}
function m_t(){
	var fckEditor = new FCKeditor( 'm_t' ) ;
	  fckEditor.BasePath = "${ctx}/scripts/fckeditor/";
	  fckEditor.ToolbarSet = 'Basic';
	  fckEditor.Height = 250;
	  fckEditor.Width = 600;
	  fckEditor.ReplaceTextarea();
}
function mechanism(){
	var fckEditor = new FCKeditor( 'mechanism' ) ;
	  fckEditor.BasePath = "${ctx}/scripts/fckeditor/";
	  fckEditor.ToolbarSet = 'Basic';
	  fckEditor.Height = 250;
	  fckEditor.Width = 600;
	  fckEditor.ReplaceTextarea();
}
function fracture(){
	var fckEditor = new FCKeditor( 'fracture' ) ;
	  fckEditor.BasePath = "${ctx}/scripts/fckeditor/";
	  fckEditor.ToolbarSet = 'Basic';
	  fckEditor.Height = 250;
	  fckEditor.Width = 600;
	  fckEditor.ReplaceTextarea();
}
function intensity(){
	var fckEditor = new FCKeditor( 'intensity' ) ;
	  fckEditor.BasePath = "${ctx}/scripts/fckeditor/";
	  fckEditor.ToolbarSet = 'Basic';
	  fckEditor.Height = 250;
	  fckEditor.Width = 600;
	  fckEditor.ReplaceTextarea();
}
function event_wave(){
	var fckEditor = new FCKeditor( 'event_wave' ) ;
	  fckEditor.BasePath = "${ctx}/scripts/fckeditor/";
	  fckEditor.ToolbarSet = 'Basic';
	  fckEditor.Height = 250;
	  fckEditor.Width = 600;
	  fckEditor.ReplaceTextarea();
}
function station_wave(){
	var fckEditor = new FCKeditor( 'station_wave' ) ;
	  fckEditor.BasePath = "${ctx}/scripts/fckeditor/";
	  fckEditor.ToolbarSet = 'Basic';
	  fckEditor.Height = 250;
	  fckEditor.Width = 600;
	  fckEditor.ReplaceTextarea();
}
</script>
<body onload="preFckEditor()">
<script type="text/javascript">

	Ext.onReady(function() {
		var tabs = new Ext.TabPanel( {
			renderTo : 'tabs',
			anchor : '100% 100%',
			height : 380,
			activeTab : 0,
			frame : false,
			defaults : {
				autoHeight : false
			},
			items : [ {
				contentEl : 'basic',
				title : '地震参数'
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
				contentEl : 'event_wave',
				title : '事件波形数据'
			}, {
				contentEl : 'station_wave',
				title : '台站波形记录图'
			}  ]
		});
	});
</script>
<div class="x-panel">
<div class="x-panel-header">编辑专题地震</div>
<div class="x-toolbar">
<table width="99%">
	<tr>
		<td align="right">
		<table>
			<tr>
				<td align="right" height="20"><a href="${ctx}/urgentcase/index.do"> 专题地震列表</a></td>
			</tr>
		</table>
		</td>
	</tr>
</table>
</div>
<div><%@ include file="/common/messages.jsp"%></div>
<s:form id="save" action="save.do" method="post" theme="simple" validate="true">
<div id="tabs" style="margin-top: -1px;margin-left: -1px;margin-right: 0px;">
<div id="basic" class="x-hide-display"><s:hidden name="model.id" />
<s:hidden name="model.qc_id" />
<fieldset style="margin:10px;">
	<legend>专题地震</legend>
	<table>
	<tr>
		<td width="120px">地点：</td>
		<td>
		<s:textfield id="location" name="location"  disabled="true"/><a href="#" onclick="javascript:selectQc(${item.id})">选择地震目录</a>
		</td>
	</tr>
	<tr>
		<td>经度：</td>
		<td>
		<s:textfield id="longitude" name="longitude" disabled="true"/>
			
		</td>
	</tr>
	<tr>
		<td>纬度：</td>
		<td>
		<s:textfield id="latitude" name="latitude" disabled="true"/>
			
		</td>
	</tr>
	<tr>
		<td>震级：</td>
		<td>
		<s:textfield id="magnitude" name="magnitude" disabled="true"/>
			
		</td>
	</tr>
	<tr>
		<td>发震时刻：</td>
		<td>
		<s:textfield id="quakeTime" name="quakeTime" disabled="true"/>
		</td>
	</tr>
	<tr>
		<td>影响地区:</td>
		<td>
		<s:textfield id="model.area" name="model.area"/>
		</td>
	</tr>
	<tr>
		<td>专题标题:</td>
		<td>
		<s:textfield id="model.title" name="model.title"/>
		</td>
	</tr>
	<tr>
		<td>专题图片:</td>
		<td>
		<s:if test="#attr.model.front_pic != null">
         			<img src="${ctx}/images/icons/compressed.gif"/>
         			<a href="${ctx}/software/down.do?model.id=${model.id}" title="${model.front_pic}"> 
         				查看文件
         			</a>
            	</s:if>
            	<s:else>
            		<s:file id="front_pic" name="front_pic" cssClass="FileText" cssStyle="width:400px"/> 
            	</s:else>
		</td>
	</tr>
	<tr>
		<td>地震描述：</td>
		<td>
		<s:textarea id="model.desn" name="model.desn" cssStyle="width:350px;height:100px"/>
		</td>
	</tr>
	</table>
	</fieldset>
</div>
<div id="epifocus" class="x-hide-display">
<table id="mytable" height="380" style="margin-top: 5px;width: 800px" align="center">
	<tr>
		<td width="15%" align="right">震中分布图：</td>
		<td align="left" style="vertical-align: top;" width="85%">
		<textarea id="epifocus" name="model.epifocus" cols="65" rows="4">${model.epifocus}</textarea>
		</td>
	</tr>
</table>
</div>
<div id="history_pic" class="x-hide-display">
<table id="mytable" height="380" style="margin-top: 5px;width: 800px" align="center">
	<tr>
		<td width="15%" align="right">历史地震分布图：</td>
		<td align="left" style="vertical-align: top;" width="85%">
		<textarea id="history_pic" name="model.history_pic" cols="65" rows="4">${model.history_pic}</textarea></td>
	</tr>
</table>
</div>
<div id="m_t" class="x-hide-display">
<table id="mytable" height="380" style="margin-top: 5px;width: 800px" align="center">
	<tr>
		<td width="15%" align="right">震中区M-T图：</td>
		<td align="left" style="vertical-align: top;" width="85%">
		<textarea id="m_t" name="model.m_t" cols="65" rows="4">${model.m_t}</textarea></td>
	</tr>
</table>
</div>
<div id="mechanism" class="x-hide-display">
<table id="mytable" height="380" style="margin-top: 5px;width: 800px" align="center">
	<tr>
		<td width="15%" align="right">震源机制解：</td>
		<td align="left" style="vertical-align: top;" width="85%">
		<textarea id="mechanism" name="model.mechanism" cols="65" rows="4">${model.mechanism}</textarea></td>
	</tr>
</table>
</div>
<div id="fracture" class="x-hide-display">
<table id="mytable" height="380" style="margin-top: 5px;width: 800px" align="center">
	<tr>
		<td width="15%" align="right">震源破裂过程：</td>
		<td align="left" style="vertical-align: top;" width="85%">
		<textarea id="fracture" name="model.fracture" cols="65" rows="4">${model.fracture}</textarea></td>
	</tr>
</table>
</div>
<div id="intensity" class="x-hide-display">
<table id="mytable" height="380" style="margin-top: 5px;width: 800px" align="center">
	<tr>
		<td width="15%" align="right">烈度分布：</td>
		<td align="left" style="vertical-align: top;" width="85%">
		<textarea id="intensity" name="model.intensity" cols="65" rows="4">${model.intensity}</textarea></td>
	</tr>
</table>
</div>
<div id="event_wave" class="x-hide-display">
<table id="mytable" height="380" style="margin-top: 5px;width: 800px" align="center">
	<tr>
		<td width="15%" align="right">事件波型数据：</td>
		<td align="left" style="vertical-align: top;" width="85%">
		<textarea id="event_wave" name="model.event_wave" cols="65" rows="4">${model.event_wave}</textarea></td>
	</tr>
</table>
</div>
<div id="station_wave" class="x-hide-display">
<table id="mytable" height="380" style="margin-top: 5px;width: 800px" align="center">
	<tr>
		<td width="15%" align="right">台站波形记录图：</td>
		<td align="left" style="vertical-align: top;" width="85%">
		<textarea id="station_wave" name="model.station_wave" cols="65" rows="4">${model.station_wave}</textarea></td>
	</tr>
</table>
</div>
</div>
<table width="100%" style="margin-bottom: 0px;">
	<tr>
		<td height="5"></td>
	</tr>
	<tr>
		<td style="text-align: center;"><s:submit value="保存"
			cssClass="button" />&nbsp;&nbsp; <s:reset value="重置"
			cssClass="button" /></td>
	</tr>
</table>
</s:form>
</div>
<script type="text/javascript">
	$(document).ready(function() {
	$("#save").validate();
});
</script>
</body>
</html>