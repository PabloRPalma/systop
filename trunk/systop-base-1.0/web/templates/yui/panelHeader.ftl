<script>
 YAHOO.namespace("example.container");

  function init() {	
    YAHOO.example.container.${data.id} = 
      new YAHOO.widget.Panel("${data.id}", { xy:[${data.x},${data.y}], 
                                             visible:${data.visible}, 
                                             width:"${data.width}" } );
	YAHOO.example.container.${data.id}.render();
	YAHOO.example.container.manager = new YAHOO.widget.OverlayManager();
	YAHOO.example.container.manager.register([YAHOO.example.container.${data.id}]);
  }
  YAHOO.util.Event.addListener(window, "load", init);
</script>
	<div id="${data.id}" style="visibility:hidden">
			<div class="hd"><#if data.title?exists>${data.title}<#else>&nbsp;</#if></div>
			<div class="bd">