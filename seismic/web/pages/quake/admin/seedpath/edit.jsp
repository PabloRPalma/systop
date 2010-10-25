<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link type="text/css" href="${ctx}/styles/style.css" rel="stylesheet" />
<link type="text/css" href="${ctx}/scripts/jquery/layout/layout.css" rel="stylesheet" />
<link type="text/css" href="${ctx}/scripts/jquery/treeview/treeview.css" rel="stylesheet" />
<link type="text/css" href="${ctx}/scripts/jquery/ui/css/jquery-ui-min.css"	rel="stylesheet" />

<script type="text/javascript" src="${ctx}/scripts/jquery/jquery-1.3.2.js"></script>
<script type="text/javascript" src="${ctx}/scripts/jquery/ui/jquery-ui-1.7.1.js"></script>
<script type="text/javascript" src="${ctx}/scripts/jquery/layout/jquery.layout.js"></script>
<script type="text/javascript" src="${ctx}/scripts/jquery/treeview/treeview.js"></script>
<script type="text/javascript" src="${ctx}/scripts/jquery/treeview/treeview.async.js" ></script>
<script type="text/javascript">
	$(function(){
		$("#loading").bind("ajaxSend", function(){
		   $("#canEx")[0].style.display = 'none';
		   $("#disEx")[0].style.display = '';
	       $(this).show();
	    }).bind("ajaxComplete", function(){
	       $("#canEx")[0].style.display = '';
		   $("#disEx")[0].style.display = 'none';
	       $(this).hide();
	    });
	});
</script>
<title></title>
</head>
<body>
<div class="x-panel">
    	<div class="x-panel-header">Seed解析程序配置</div>
    	<div class="x-panel-body">
    		<div id="loading" style="display:none;text-align:center;">
				<img src="${ctx}/images/grid/loading.gif" border="0"/>&nbsp;&nbsp;Seed解析程序正在执行，您可以继续其他操作...
			</div>
			<s:form action="save" theme="simple" id="save" validate="true" method="POST">
		   		<s:hidden name="model.id"/>
		   		<s:hidden name="model.version"/>
		   		<div style="margin:30px;text-align:center;">
		   		    <%@ include file="/common/messages.jsp"%>
		   		</div>
		   		<fieldset style="margin:30px;">
			    <legend>设定路径</legend>
				<table width="100%">
                     <tr>
                        <td style="text-align:right;">Seed解析程序路径：（<font color="red">*</font>）</td>
                        <td style="text-align:left;">
                        	<s:textfield size="60" name="model.path" cssClass="required"/>
                        </td>
                     </tr>
                </table>
                </fieldset>
                <table width="100%" style="margin-bottom:10px;">
                       <tr id="canEx">
                          <td style="text-align:center;">
                            <s:submit value="保存" cssClass="button"></s:submit>&nbsp;&nbsp;
                            <s:reset value="重置" cssClass="button"></s:reset>&nbsp;&nbsp;
                            <input id="exbtn" value="执行" onclick="exeSeedPro()" size="6" style="text-align: center;cursor: auto;" type="button" class="button"/>
                       	  </td>
                        </tr>
                        <tr id="disEx" style="display: none">
                          <td style="text-align:center;">
                            <input id="exbtn" disabled="disabled" value="保存" size="6" style="text-align: center;cursor: auto;" type="button" class="button"/>&nbsp;&nbsp;&nbsp;
                            <input id="exbtn" disabled="disabled" value="重置" size="6" style="text-align: center;cursor: auto;" type="button" class="button"/>&nbsp;&nbsp;&nbsp;
                            <input id="exbtn" disabled="disabled" value="执行" size="6" style="text-align: center;cursor: auto;" type="button" class="button"/>
                       	  </td>
                        </tr>
                </table>
             </s:form>
    	</div>
  </div>
<script type="text/javascript">
function exeSeedPro() {
	$.ajax({
		url: '${ctx}/quake/admin/seedpath/exeSeedPro.do',
		type: 'post',
		dataType: 'json',
		success: function(result, textStatus){
			alert(result);
		}
	});
}
</script>
</body>
</html>