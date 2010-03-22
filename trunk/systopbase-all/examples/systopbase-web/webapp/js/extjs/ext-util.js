/**
 * For JSP applications, the URLs are offen virtual, and the absolute path
 * must add the 'Context Path'.The Ext.ctx() help us add the 'Context Path'
 * in front of the URL.
 * For using the Ext.ctx() function, User should add the flowing coding in 
 * front of the JSP page :
 * <pre>
 * <%@ include file="/common/taglibs.jsp" %>
 * </pre>
 * @param url The URL string contains '{ctx}' signs, which will be replaced
 * by the 'Context Path'.
 * @return URL with 'Context Path', or just 'Context Path' be returned if
 * the argument 'url' is not defined.
 */
Ext.ctx = function(url) {
	var urlPrefix = (typeof URL_PREFIX == "undefined") ? '' : URL_PREFIX;
	if(typeof url == "undefined") 
	    return urlPrefix;
	return String(url).replace('{ctx}', urlPrefix);
};
/**
 * The variable is used by layout.jsp
 */
Ext.BLANK_IMAGE_URL = Ext.ctx('{ctx}/js/extjs/resources/images/default/s.gif');

/**
 * Add a global listener to ajax request. This will add the 'Context Path' 
 * implicitly before ajax request send.
 */
Ext.Ajax.on('beforerequest', function(conn, o) {
	if(o.url.indexOf(Ext.ctx()) >=0) {
		return;
	}
	if(o.url.indexOf('{ctx}') >= 0) {
		o.url = Ext.ctx(o.url);
	} else if(o.url.charAt(0) == '/') {
	  o.url = Ext.ctx() + o.url;
	} else {
		o.url = Ext.ctx() + '/' + o.url;
	}
});

Ext.Ajax.defaultHeaders = {
    'Powered-By': 'Ext',
    'Accept': 'application/x-json;text/x-json;charset=UTF-8'
};

Ext.my = function(){
    var msgCt;

    function createBox(t, s){
        return ['<div class="msg">',
                '<div class="x-box-tl"><div class="x-box-tr"><div class="x-box-tc"></div></div></div>',
                '<div class="x-box-ml"><div class="x-box-mr"><div class="x-box-mc"><h3>', t, '</h3>', s, '</div></div></div>',
                '<div class="x-box-bl"><div class="x-box-br"><div class="x-box-bc"></div></div></div>',
                '</div>'].join('');
    }
    return {
        msg : function(title, format){
            if(!msgCt){
                msgCt = Ext.DomHelper.insertFirst(document.body, {id:'msg-div'}, true);
            }
            msgCt.alignTo(document, 't-t');
            var s = String.format.apply(String, Array.prototype.slice.call(arguments, 1));
            var m = Ext.DomHelper.append(msgCt, {html:createBox(title, s)}, true);
            m.slideIn('t').pause(2).ghost("t", {remove:true});
        }
    };
};

Ext.tree.XTreeLoader = function(config) {
	config = config || {};
	Ext.tree.XTreeLoader.superclass.constructor.call(this, config);
	this.addEvents("beforeCreateNode");
	this.addEvents("afterCreateNode");
};

Ext.extend(Ext.tree.XTreeLoader, Ext.tree.TreeLoader, {
	createNode : function(attr){
		this.fireEvent("beforeCreateNode", this, attr);
		var node = Ext.tree.XTreeLoader.superclass.createNode.apply(this, arguments)
		this.fireEvent("afterCreateNode", node);
		return node;
    }
});




