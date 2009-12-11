/** set parentId */
function setCatalog(){
	//catalogId 似乎没有用，可以删除
	CatalogDwrAction.getCatalog("catalog", $('model.id').value, function(catalog){
		DWRUtil.removeAllOptions("parentId");
		DWRUtil.addOptions($("parentId"), catalog,
    		function getValue(catalog) {
    			return catalog.id;
    		},
    		function getText(catalog) {
    			return catalog.text;
    		});
		DWRUtil.setValue("parentId", $('model.parentCatalog.id').value);
		} 
	);
}


/** set template */
function setTemplate(){
	/** set catalogTemplate */
	CatalogDwrAction.getTemplate("catalog",function(template){
		DWRUtil.removeAllOptions("catalogTemplate");
		DWRUtil.addOptions($("catalogTemplate"), template,
    		function getValue(template) {
    			return template.id;
    		},
    		function getText(template) {
    			return template.name;
    		});
    	DWRUtil.setValue("catalogTemplate", $('model.cataTemplate.id').value);
	});
	/** set articleTemplate */
	CatalogDwrAction.getTemplate("article",function(template){
		DWRUtil.removeAllOptions("articleTemplate");
		DWRUtil.addOptions($("articleTemplate"), template,
    		function getValue(template) {
    			return template.id;
    		},
    		function getText(template) {
    			return template.name;
    		});
    	DWRUtil.setValue("articleTemplate", $('model.artTemplate.id').value);
	});
}

function showInner(){
	document.getElementById("SystopTab2").style.display = "";
	document.getElementById("root").style.display = "";
	document.getElementById("externalUrl").style.display = "none";
	$('model.linkUrl').value == ""
	$('model.type').value = "1";
}

function showExternal(){
	document.getElementById("SystopTab2").style.display = "none";
	document.getElementById("root").style.display = "none";
	document.getElementById("externalUrl").style.display = "";
	$('model.root').value = "";
	if($('model.linkUrl').value == ""){
		$('model.linkUrl').value = "http://";
	}
	$('model.type').value = "2";
}

function init(){
  	setCatalog();
  	setTemplate();
  	if($('model.type').value == 2){
		document.getElementById("external").checked=true;
		showExternal();
	}
}