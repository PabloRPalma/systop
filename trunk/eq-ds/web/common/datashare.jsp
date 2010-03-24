<%@ taglib prefix="ec"  uri="http://www.ecside.org" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="Cache-Control" content="no-cache"/>
<meta http-equiv="Pragma" content="no-cache"/>
<meta http-equiv="Expires" content="0"/>
<link type="text/css" rel="stylesheet"  href="${ctx}/scripts/extjs/resources/css/loading.css" />
<link type="text/css" rel="stylesheet" href="${ctx}/scripts/jquery/validate/css/screen.css">
<LINK type="text/css" rel="stylesheet" href="${ctx}/styles/datashare.css">

<script type="text/javascript" src="${ctx}/scripts/jquery/jquery-1.3.2.js" ></script>
<script type="text/javascript" src="${ctx}/scripts/utils.js" ></script>
<script type="text/javascript" src="${ctx}/scripts/ec/ecside_msg_utf8_cn.js" ></script>
<script type="text/javascript" src="${ctx}/scripts/ec/ecside-unpack.js" ></script>
<script type="text/javascript" src="${ctx}/scripts/jquery/validate/jquery.validate.js"></script>
<script type="text/javascript" src="${ctx}/scripts/jquery/validate/messages_cn.js"></script>
<script type="text/javascript" src="${ctx}/scripts/calendar/WdatePicker.js"></script>
<div id="loading-mask" style=""></div>
<div id="loading">
    <div class="loading-indicator">Loading...</div>
</div>
<script type="text/javascript">
$(function(){
	setTimeout(function(){
	        $('#loading').hide();
	        $('#loading-mask').fadeOut();	        
	    }, 250);
});
</script>