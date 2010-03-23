CMSQuery = function(/*String*/tableId, /*String*/paginatorId, /*DWR object*/action, /*Number*/pageSize) {
   
    this.tableId = tableId; //widget id of 'dojo.ex.FilteringTableEx'
    this.dojoHelper = new DojoHelper();    
    this.paginatorId = paginatorId;//widget id of 'dojo.ex.Paginator'
    this.action = action; //DWR CMSDwrAction   
    this.pageSize = pageSize;// size of the page
};

CMSQuery.prepareEdit = null;

CMSQuery.makeTypeNode = function(data, typeIcons) {    
     //A HTML image node to show the content type
    var div = document.createElement('div');
    div.setAttribute("align", "center");
    var node = document.createElement('img');
    var iconIndex = 
        (typeof data.type == "undefined" || !data.type) ? 0: parseInt(data.type); 
    if(isNaN(iconIndex)) iconIndex = 0;                         
    node.setAttribute('src', typeIcons[iconIndex].src);
    node.setAttribute('title', typeIcons[iconIndex].alt);
    div.appendChild(node);
    data.type = div;
};

CMSQuery.makeTitleNode = function(data) {
    var thisType = data.type;
    if(thisType == '0') {
        if(CMSQuery.prepareEdit) {
            var span = document.createElement('span');
            span.innerHTML = data.title;
            span.setAttribute('value', data.id);
            span.setAttribute("align", "center");
            span.style.textDecoration = "underline";
            span.style.cursor = "pointer";
            span.style.color = "#0000BB";
            var cId = data.id;
            span.onclick = function(){CMSQuery.prepareEdit(cId, currentFolderId);};
            data.title = span;
       } else {
           var a = document.createElement('a');
           a.innerHTML = data.title;
           a.setAttribute('href', "editContent.jsp?contentId=" + data.id + "&parent=" + currentFolderId);
           data.title = a;
       }
   }
};

CMSQuery.onSelect = function(selectedId, checkbox) {
};

CMSQuery.makeCheckBox = function(data) {
    var cb = document.createElement("input");
    cb.setAttribute("type", "checkbox");
    if(data.changed) { //we can't set checked=false, why? use defaultChecked?
        cb.setAttribute("checked", true);
    }
    cb.onclick = function() {
        CMSQuery.onSelect(data.id, cb);
    }
    data.changed = cb;
};

CMSQuery.makeAuditNode = function(data) {
   var div = document.createElement("div");
   div.setAttribute('align', 'center');
   if(!data.audited || data.audited == '&nbsp;') {
      div.innerHTML = "<img src='../images/icons/help_16.gif' border='0' title='waiting...'/>";
   }
   if(data.audited == '0') {
      div.innerHTML = "<img src='../images/icons/forbidden.gif' border='0' title='denied'/>";
   }
   if(data.audited == '1') {
      div.innerHTML = "<img src='../images/icons/ok_1.gif' border='0' title='passed'/>";
   }
   data.audited = div;
};

CMSQuery.prototype = {
  pageNo: 1,
  tableId: "",
  folerId: "",//Current active folder's id.
  dojoHelper: null,
  paginatorId: "",
  action: null,
  pageSize: 10,
  typeIcons: [],/*array of {src:"",alt:""}*/
  editIconSrc: "",
  
  //Accessor methods for folderId.
  getFolderId: function() {
      return this.folderId;
  },
  
  setFolderId: function(folderId) {
      this.folderId = folderId;
  },
  
  //get query criteria from HTML node or dojo widget.
  _getFilter: function() {
      return {
         title:      this.dojoHelper.getValue('query_title'),
         author:     this.dojoHelper.getValue('author'),
         updater:    this.dojoHelper.getValue('updater'),
         createTime: this.dojoHelper.getDatePickerValue('query_createTime'),
         updateTime: this.dojoHelper.getDatePickerValue('query_updateTime'),
         type:       this.dojoHelper.getValue('query_type'),
         parent:     this.folderId
      };
  },
  
  clearFilter: function() {
      this.dojoHelper.setValue('query_title', '');
      this.dojoHelper.setValue('query_type', '');
      dojo.byId('query_type').selectedIndex = 0;
      this.dojoHelper.setValue('author', '');
      this.dojoHelper.setValue('updater', '');
      this.dojoHelper.resetDatePicker('query_createTime');
      this.dojoHelper.resetDatePicker('query_updateTime');
  },
  
  
  //Execute DWR query function and refresh table & paginator
  query: function(/*number*/pageNo) {
     var table = dojo.widget.byId(this.tableId);
     var paginator = dojo.widget.byId(this.paginatorId);
     //We must change the member variable to local variable.
     var pageSize = this.pageSize;
     if(pageNo <= 0) {
         pageNo = 1;
     }
     this.pageNo = pageNo;
     var filter = this._getFilter();
     if(table) {
         var sort = table.getSort();
     }
     var typeIcons = this.typeIcons;
     
     //var editIcon = this.editIconSrc;
     this.action.query(filter, sort, this.pageSize, pageNo, 
         function(page) {             
             if(table) {
                 for(var i = 0; i < page.data.length; i++) {
                     CMSQuery.makeTitleNode(page.data[i]);           
                     CMSQuery.makeTypeNode(page.data[i], typeIcons);
                     CMSQuery.makeCheckBox(page.data[i]);   
                     CMSQuery.makeAuditNode(page.data[i]);                            
                 }
                 table.store.setData(page.data);//refresh table
             }                          
             if(paginator) {//refresh paginator
                 paginator.setParams({
                 pageNo    :page.currentPageNo,
                 totalCount:page.totalCount,
                 pageSize  :pageSize
              });  
            }
     });
  }
  
};