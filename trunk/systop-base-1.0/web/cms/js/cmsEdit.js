CMSEditor = function(action, tabContainerId) {
    this.tabContainerId = tabContainerId;
    this.dojoHelper = new DojoHelper();
    this.action = action;
};

CMSEditor.prototype = {
    tabContainerId: "",
    action: null,
    dojoHelper: null,
    
    _getExpDate: function() {
       if(!dojo.byId("neverExp").checked) {
           return this.dojoHelper.getDatePickerValue("expireDate");
       } else {
           return null;
       }
    },
    
    _getParam: function() {
        return {
           id:         this.dojoHelper.getValue("id"),
           title:      this.dojoHelper.getValue("title"),
           subtitle:   this.dojoHelper.getValue("subtitle"),
           expireDate: this._getExpDate(),
           summary:    this.dojoHelper.getValue("summary"),
           available:  dojo.byId("available").checked,
           visible:    dojo.byId("visible").checked,
           isDraft:    dojo.byId('draft').checked,
           body:       ""
        }
    },
    
   /* _getFiles: function() {
         return {
             id:   file.id,
             desc: file.desc,
             filename: file.filename     
         }
    
    }*/
    // Create new content or Update a existed content
    save: function(body) {
       var content = this._getParam();
       if(content.id == ""){
            content.id = null;
       }
       var tabContainerId = this.tabContainerId;
       if(body) {
         content.body = body;
       }
       this.action.saveContent(content, this.dojoHelper.getValue('parentid'), getResults(),
           function(c) {
              if(tabContainerId) {
                 var tContainer = dojo.widget.byId(tabContainerId);
                 var cPane = dojo.widget.byId('view');
                 tContainer.selectChild(cPane, false, tContainer);
              }
           });
    }
};