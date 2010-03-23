dojo.provide("dojo.ex.FilteringTableEx");
dojo.require("dojo.widget.*");
dojo.require("dojo.widget.FilteringTable");
dojo.require("dojo.widget.HtmlWidget");
dojo.require("dojo.event.*");
dojo.require("dojo.html.style");

dojo.widget.defineWidget(
	"dojo.ex.FilteringTableEx",
	dojo.widget.FilteringTable,{
		sortField:     "",
		sortDirection: "",  
		
	    fillCell: function(/* HTMLTableCell */cell, /* object */meta, /* object */val){
	    if(typeof val == 'object') {
	    	cell.appendChild(val);
	    	return;
	    }  
			//	summary
			//	Fill the passed cell with value, based on the passed meta object.
			if(meta.sortType=="__markup__"){
				cell.innerHTML=val;
			} else {
				if(meta.getType()==Date) {
					val=new Date(val);
					if(!isNaN(val)){
						var format = this.defaultDateFormat;
						if(meta.format){
							format = meta.format;
						}
						cell.innerHTML = dojo.date.strftime(val, format);
					} else {
						cell.innerHTML = val;
					}
				} 
				 else if ("Number number int Integer float Float".indexOf(meta.getType())>-1){
					//	TODO: number formatting
					if(val.length == 0){
						val="0";
					}
					var n = parseFloat(val, 10) + "";
					//	TODO: numeric formatting + rounding :)
					if(n.indexOf(".")>-1){
						n = dojo.math.round(parseFloat(val,10),2);
					}
					cell.innerHTML = n;
				}else{
					cell.innerHTML = val;
				}
			}
	  },
	  
	  
	  _onSort: function(/* HTMLEvent */e){
		  //	summary
		  //	Sort the table based on the column selected.
		  var oldIndex=this.sortIndex;
		  var oldDirection=this.sortDirection;
		
		  var source=e.target;
		  var row=dojo.html.getParentByType(source,"tr");
		  var cellTag="td";
		  if(row.getElementsByTagName(cellTag).length==0){
		      cellTag="th";
		  }

		  var headers=row.getElementsByTagName(cellTag);
		  var header=dojo.html.getParentByType(source,cellTag);
		
		  for(var i=0; i<headers.length; i++){
			dojo.html.setClass(headers[i], this.headerClass);
			if(headers[i]==header){
				if(this.sortInformation[0].index != i){
					this.sortInformation.unshift({ 
						index:i, 
						direction:0
					});
				} else {
					this.sortInformation[0] = {
						index:i,
						direction:(~this.sortInformation[0].direction)&1
					};
				}
			}
		  }

		  this.sortInformation.length = Math.min(this.sortInformation.length, this.maxSortable);
		  for(var i=0; i<this.sortInformation.length; i++){
			  var idx=this.sortInformation[i].index;
			  var dir=(~this.sortInformation[i].direction)&1;
			  dojo.html.setClass(headers[idx], dir==0?this.headerDownClass:this.headerUpClass);
		  }
		  //this.render();
	    },
	    
	    getSort: function() {
	        this.sortField = this.columns[this.sortInformation[0].index];
	        this.sortDirection = 
	           (this.sortInformation[0].direction == 0) ? "asc" : "desc";
	        if(!this.sortField) {
	            return null;
	        }
	        return [this.sortField.getField(), this.sortDirection];
	    }
	}
);