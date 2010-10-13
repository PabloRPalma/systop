<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp" %>
<html>
<head>
<%@include file="/common/meta.jsp" %>
<%@include file="/common/extjs.jsp" %>
<title></title>
<%@ taglib prefix="stc" uri="/systop/common" %>
<style type="text/css">
.x-panel{
margin-top:5px;
}
</style>
<script type="text/javascript">

Ext.onReady(function(){

       Ext.state.Manager.setProvider(new Ext.state.CookieProvider());

       var viewport = new Ext.Viewport({            
                region:'center',
                margins:'5 5 5 5',
                layout:'column',
                autoScroll:true,
                items:[{
                    columnWidth:.5,
                    baseCls:'x-plain',
                    bodyStyle:'padding:5px',
                    items:[{
                        title: '系统配置情况',
                        html: "<p id='cfg'>" +
                        "<div id='cfgLoading'>"+
                        "<img src='${ctx}/images/grid/loading.gif' />正在检查...</div></p>"
                    }]
                }]
            
        });
        
        Ext.Ajax.request({
          url:'${ctx}/quake/admin/main/validateConfigurations.do',
          success: function(response) {
              Ext.get('cfgLoading').fadeOut();
              Ext.get('cfgLoading').remove();
              var data = Ext.util.JSON.decode(response.responseText);
              if(data.undefineMessages.length == 0) {
                  Ext.getDom('cfg').innerHTML = "<img src='${ctx}/images/icons/accept.gif' />所有系统配置项都已配置成功。";
	           } else {
	              var html = "以下项目尚未配置：";
	              for(var i = 0; i < data.undefineMessages.length; i++) {
	                 html += "<br><img src='${ctx}/images/icons/124.gif' style='margin-right:5px;margin-top:5px;'/>";
	                 html += data.undefineMessages[i];	                 
	              }
	              Ext.getDom('cfg').innerHTML = html;
	           }
	           
          }
        });
        
    });
</script>
</head>
<body>

</body>
</html>