<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="/common/taglibs.jsp"%>
<html>
<head>
<title></title>
<%@include file="/common/meta.jsp"%>
<%@include file="/common/ec.jsp"%>
<script type="text/javascript">
	function togovernment(id){
	    window.location.href="${ctx}/genericCase/saveSubSj.do?model.id=" + id;
		alert('申请已发送，等待审核');
	}
	function send(id){
		window.location.href="${ctx}/sendType/choose.do?eventid=" + id;
		//window.open(url,'main'); 
	}
	function ally(id){
	   
		window.location.href="${ctx}/allyTask/input.do?model.singleEvent.id=" + id +"&eventType=s";
		//window.open(url,'main'); 
	}
	function ret(id){
	   
		url="${ctx }/singleTaskDetail/listreteventid.do?eventid=" + id;
		window.open(url); 
	}
	//申请评估
	function apply(id){
		window.location.href="${ctx}/riskeval/input.do?model.singleEvent.id=" + id + "&eventType=s";	 
	}
	//在地图中标注事件位置
	function mapLabel(id){
	    window.location.href="${ctx}/pages/opengis/mapSingleEvent.jsp?value="+id;
	}
</script>
</head>
<body>
<div class="x-panel">
<div class="x-panel-header"></div>
<div class="x-toolbar">
	<table width="99%">
	<tr>
		<td align="right"><a href="${ctx}/singleEvent/index.do"><img
					src="${ctx}/images/icons/house.gif" /> 事件列表</a></td>
	</tr>
	</table>
</div>
<div align="center">
  <s:form action="#" method="post" theme="simple" >
	<s:hidden name="model.id" />
	<fieldset style="width: 66%; padding: 0px 10px 10px 10px;">
	<legend>事件描述</legend>
	<table width="600px" align="center">
		<tr>
			<td colspan="2"><%@ include file="/common/messages.jsp"%>
			</td>
		</tr>
		<tr>
			<td align="right" width="120">事件标题：</td>
			<td align="left" width="420"><s:property value="model.title" /></td>
		</tr>
		<tr>
			<td align="right" width="120">事件类别：</td>
			<td align="left" width="420"><s:property
				value="model.caseType.name" /></td>
		</tr>
		<tr>
			<td align="right" width="120">事发时间：</td>


			<td align="left" width="420"><s:date name="model.eventDate"
				format="yyyy-MM-dd HH:mm:ss" /></td>


		</tr>
		<tr>
			<td align="right" width="120">事发地点：</td>
			<td align="left" width="420"><s:property value="model.address" /></td>
		</tr>

		<tr>
			<td align="right" width="120">事件报告人：</td>
			<td align="left" width="420"><s:property
				value="model.informer" /></td>
		</tr>
		<tr>
			<td align="right" width="120">报告人电话：</td>
			<td align="left" width="420"><s:property
				value="model.informerPhone" /></td>
		</tr>
		<tr>
			<td align="" colspan="2">
				<div class="x-toolbar" style="padding:5px;5px;5px;5px;border: 1px solid #A9BFD3;" align="center">事件描述</div>
			</td>
		</tr>
		<tr>
			<td align="left" colspan="2" style="padding:5px 10px 5px 10px; border-bottom: 1px solid #A9BFD3;word-break:break-all;">
				${model.descn}
			</td>
		</tr>
	</table>
	</fieldset>
	
	<table width="510px" style="margin-bottom: 10px;">
		<tr>
		    <td style="text-align: center;"><input type="button" value="地理位置" class="button"  onclick="mapLabel(${model.id})"/></td>
			<c:if test="${model.status == 0 || model.status == 3}">
			<td style="text-align: center;"><input type="button" value="上报市级" class="button"  onclick="togovernment(${model.id})"/></td>
			<td style="text-align: center;"><input type="button" value="任务派遣" class="button" onclick="send(${model.id})"/></td>
			<td style="text-align: center;"><input type="button" value="申请联合整治" class="button" onclick="ally(${model.id})"/></td>
			<td style="text-align: center;">
				<!--<s:if test="model.riskEval.size == 0">-->
					<input type="button" value="申请评估" class="button" onclick="apply(${model.id})"/>
				<!--</s:if>-->
			</td>
			<td style="text-align: center;"><input type="button" value="查看退回记录" class="button" onclick="ret(${model.id})"/></td>
		    </c:if>
		</tr>
	</table>
	
	
</s:form>
</div>

</div>
<div align="center" style="width:630; border-left: 1px solid;">
<c:if test="${model.status != 0 && model.status != 3 }">
<iframe height="250" align="center" id="iFrame1" name="iFrame1" width="630" onload="this.height=iFrame1.document.body.scrollHeight" frameborder="0" src="${ctx }/singleTaskDetail/listbyeventid.do?eventid=${model.id}"></iframe>
</c:if>
</div>
</body>
</html>