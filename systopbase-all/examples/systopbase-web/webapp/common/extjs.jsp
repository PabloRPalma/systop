<link rel="stylesheet" type="text/css" href="${ctx}/js/extjs/resources/css/loading.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/js/extjs/resources/css/ext-all.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/js/extjs/resources/css/xtheme-slate.css" />
<div id="loading-mask" style=""></div>
<div id="loading">
    <div class="loading-indicator">Loading...</div>
</div>
<script type="text/javascript" src="${ctx}/js/extjs/adapter/ext/ext-base.js"></script>
<script type="text/javascript" src="${ctx}/js/extjs/ext-all-2.0.2.js"></script>
<script type="text/javascript" src="${ctx}/js/extjs/locale/ext-lang-zh_CN.js"></script>
<script type="text/javascript" src="${ctx}/js/extjs/ext-util.js"></script>

<script type="text/javascript">
Ext.onReady(function(){
	setTimeout(function(){
	        if(Ext.get('loading')) {
	          Ext.get('loading').remove();
	        } 
	        if(Ext.get('loading-mask')) {
	          Ext.get('loading-mask').fadeOut({remove:true});
	        }
	    }, 250);
});
</script>
