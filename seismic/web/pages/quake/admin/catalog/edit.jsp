<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@include file="/common/meta.jsp" %>
<script type="text/javascript">
  window.onload = function() {
    disMagType(document.getElementById('magTname').value, document.getElementById("dtype").value);
  }
  function disMagType(dValue,tValue){
     var disDiv1 = document.getElementById("magType");
     if(dValue == '' || dValue == null){
       disDiv1.style.display = "";
       document.getElementById("dtype").value = tValue;
     }else{
       disDiv1.style.display = "none";
       document.getElementById("dtype").value = tValue;
     }
  }
</script>
</head>
<body>
<div class="x-panel">
  <div class="x-panel-header">地震目录管理</div>
    <div class="x-toolbar">
	  <table width="100%">	      
		<tr>	         
		  <td align="right">   
			<table>
			  <tr>
      <td><a href="${ctx}/quake/admin/czcatalog/index.do"> 地震目录管理首页</a></td> 
      <td><span class="ytb-sep"></span></td>
      <td><a href="#"> 编辑地震目录</a></td>
              </tr>
            </table>
          </td>
        </tr>
      </table>
    </div>
<table width="100%" border="0" cellpadding="5" cellspacing="1">
	<tr>
		<td><%@ include file="/common/messages.jsp"%></td>
	</tr>
</table>
<div class="x-panel-body">
<table width="600" align="center"><tr><td align="center">
<s:form action="save" namespace="/quake/admin/catalog" validate="true" theme="simple">
<s:hidden name="model.id"/>
	<fieldset style="margin:10px;">
	<legend>编辑地震目录属性</legend>
	<table>
	<tr>
		<td width="120px">地震目录表名：</td>
		<td>
		<s:textfield id="model.cltName" name="model.cltName" /><font color="red">&nbsp;*</font>
			&nbsp;&nbsp;填写数据库中的实际‘地震目录表’名
		</td>
	</tr>
	<tr>
		<td>地震目录名称：</td>
		<td>
		<s:textfield id="model.clcName" name="model.clcName" /><font color="red">&nbsp;*</font>
			&nbsp;&nbsp;网站页面的数据服务中显示的地震目录名称
		</td>
	</tr>
	<tr>
		<td>目录震級表名：</td>
		<td>
		<s:textfield id="magTname" name="model.magTname" onchange="disMagType(this.value,'')" />
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;填写数据库中的实际‘震級表’名
		</td>
	</tr>
	<tr>
		<td>目录震相表名：</td>
		<td>
		<s:textfield id="model.phaseTname" name="model.phaseTname" />
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;填写数据库中的实际‘震相表’名
		</td>
	</tr>
	<tr id="magType" style="display: ''">
		<td>震级列显示类型：</td>
		<td>
		<s:select list="disTypeMap" id="dtype"
				cssClass="required" name="model.disType" headerKey=""
				headerValue="--全部类型--" cssStyle="width:156px;" />
		</td>
	</tr>
	<tr>
		<td>是否显示事件波形：</td>
		<td>
		<s:select list="seedDisMap"
				cssClass="required" name="model.seedDis" headerKey="" cssStyle="width:156px;" />
		</td>
	</tr>
	<tr>
		<td>地震目录描述：</td>
		<td>
		<s:textarea id="model.clDescn" name="model.clDescn" cssStyle="width:350px;height:100px"/>
		</td>
	</tr>
	</table>
	</fieldset>
	<table width="100%" style="margin-bottom:10px;">
	<tr>
		<td colspan="2" align="center" class="font_white">
		<s:submit value="保存" cssClass="button"></s:submit>
		</td>
	</tr>
</table>
</s:form>
 </td></tr></table>
        </div></div>
</body>
</html>