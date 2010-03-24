<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@include file="/common/meta.jsp" %>
<script type="text/javascript">
$(function() {
    if($('#model.sort').val() == '') {
      $('#model.sort').val('0');
    }
    $('input').each(function(idx, item) {
        var span = document.getElementById('span_' + item.id);
        
        if(span != null) {
            $(item).focus(function() {
                $('#comment').html(span.innerHTML);
            });
            $(item).blur(function() {
                $('#comment').html('');
            });
        }
    });
});
</script>
</head>
<body>
<div class="x-panel">
  <div class="x-panel-header">采样率管理</div>
    <div class="x-toolbar">
	  <table width="100%">	      
		<tr>	         
		  <td align="right">   
			<table>
			  <tr>
      <td><a href="${ctx}/datashare/admin/samplerate/index.do"> 采样率管理首页</a></td> 
      <td><span class="ytb-sep"></span></td>
      <td><a href="#"> 编辑采样率信息</a></td>
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
<table width="90%"><tr><td>
<s:form action="save" namespace="/datashare/admin/samplerate" validate="true" theme="simple" onsubmit="return onSubmit()">
	<fieldset style="margin:10px;">
	<legend>编辑采样率信息</legend>
	<table>
	  <tr>
		<td align="left" nowrap="nowrap" width="150">采样率代码：</td>
		<td width="250">
			<s:textfield id="model.id" name="model.id"/>
			<font color="red">*&nbsp;</font>
			<td rowspan="8" valign="top">
			<div><b>说明：</b></div>
			<div id="comment" style="margin:10px;"></div>
		</td>
	  </tr>
	  <tr>
		<td align="left" nowrap="nowrap">采样率名称：</td>
		<td>
		<s:textfield id="model.name" name="model.name"/>
		<font color="red">*&nbsp;</font>
		
		</td>
	  </tr>
	  <tr>
		<td align="left" nowrap="nowrap">排列顺序：</td>
		<td>
		<s:textfield id="model.sort" name="model.sort" size="2"/>
		<font color="red">*&nbsp;</font>
		
		</td>
	  </tr>
	  <tr>
		<td align="left" nowrap="nowrap">采样率时间格式：</td>
		<td>
		<s:textfield id="model.dateFormat" name="model.dateFormat"/>
		<font color="red">*&nbsp;</font>
		
		</td>
	  </tr>
	  <tr>
		<td align="left" nowrap="nowrap">每天采集数据个数：</td>
		<td>
		<s:textfield id="model.dataAmount" name="model.dataAmount"/>
		<font color="red">*&nbsp;</font>
		</td>
		
	  </tr>
	  <tr>
		<td align="left" nowrap="nowrap">flash曲线显示时间间隔：</td>
		<td>
		<s:textfield id="model.stockPeriod" name="model.stockPeriod"/>
		<font color="red">&nbsp;</font>
		</td>
		
	  </tr>
	  <tr>
		<td align="left" nowrap="nowrap">flash曲线显示时间名称：</td>
		<td>
		<s:textfield id="model.stockPeriodName" name="model.stockPeriodName"/>
		<font color="red">&nbsp;</font>
		</td>
		
	  </tr>
	  <tr>
		<td align="left" nowrap="nowrap">flash曲线显示时间格式：</td>
		<td>
		<s:textfield id="model.stockDateFormat" name="model.stockDateFormat"/>
		<font color="red">&nbsp;</font>
		</td>
		
	  </tr>
	  <tr>
		<td align="left" nowrap="nowrap">是否启用：</td>
		<td>
		<input type="checkbox" id="enabled" ${model.enabled == 1 ? "checked" : ""}/>
		<s:hidden id="enabledTxt" name="model.enabled"/>
		</td>
	  </tr>
	  <tr>
		<td align="left" nowrap="nowrap">是否可以订阅：</td>
		<td>
		<input type="checkbox" id="forMail" ${model.forMail == 1 ? "checked" : ""}/>
		<s:hidden id="forMailTxt" name="model.forMail"/>
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
<div style="display:none">
<span id="span_model.sort">采样率在下拉菜单中的顺序号</span>
<span id="span_model.dateFormat">采样率对应的时间格式，例如：秒值是yyyy-MM-dd HH:mm:ss</span>
<span id="span_model.dataAmount">在该采样率下，每天采集多少数据，例如：秒值是86400，日均值是1</span>
<span id="span_model.stockPeriod">Flash曲线显示的时间间隔，例如：日均值可以显示1个月、两个月，那么就是MM；小时值则是DD;秒值是hh</span>
<span id="span_model.stockPeriodName">Flash曲线显示的时间间隔的名称：例如，日均值可以显示1个月、两个月，那么就是“月”；小时值则是“天”;秒值是“小时”</span>
<span id="span_model.enabled">如果启用这个采样率，那么就可以在数据查询中以它为查询条件.</span>
<span id="span_model.stockDateFormat">Flash曲线显示的时间格式，它的日期部分都是大写，时分秒都是小写，例如：秒值：YYYY-MM-DD hh:mm:ss</span>

</div>
</s:form>
 </td></tr></table>
        </div></div>
<script type="text/javascript">
function onSubmit() {
  $("#forMailTxt").attr("value", $("#forMail").attr("checked") ? "1" : "0");
  $("#enabledTxt").attr("value", $("#enabled").attr("checked") ? "1" : "0");
  return true;
}
</script>
</body>
</html>