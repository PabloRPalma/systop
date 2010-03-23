dojo.provide("dojo.ex.Paginator");
dojo.require("dojo.widget.*");
dojo.require("dojo.widget.HtmlWidget");
dojo.require("dojo.event.*");
dojo.require("dojo.html.style");

dojo.widget.defineWidget(
	"dojo.ex.Paginator",
	dojo.widget.HtmlWidget,
	{
		templatePath: dojo.uri.dojoUri('src/ex/templates/Paginator.html'),
		templateCssPath: dojo.uri.dojoUri('src/ex/templates/Paginator.css'),
		
		widgetId : "",
		
		cssStyle: "",
		
		//CSS
		paginatorClass: "paginatorClass",
		paginatorButtonClass: "paginatorButtonClass",
		paginatorInformationClass: "paginatorInformationClass",
		paginatorFastStepClass: "paginatorFastStepClass",
		//Button images
		firstImageSrc: dojo.uri.dojoUri('src/ex/templates/images/firstPage.gif'),
		firstImageDisabledSrc: dojo.uri.dojoUri('src/ex/templates/images/firstPageDisabled.gif'),
		
		prevImageSrc: dojo.uri.dojoUri('src/ex/templates/images/prevPage.gif'),
		prevImageDisabledSrc: dojo.uri.dojoUri('src/ex/templates/images/prevPageDisabled.gif'),
		
		nextImageSrc: dojo.uri.dojoUri('src/ex/templates/images/nextPage.gif'),
		nextImageDisabledSrc: dojo.uri.dojoUri('src/ex/templates/images/nextPageDisabled.gif'),
		
		lastImageSrc: dojo.uri.dojoUri('src/ex/templates/images/lastPage.gif'),
		lastImageDisabledSrc: dojo.uri.dojoUri('src/ex/templates/images/lastPageDisabled.gif'),
		
		//Page Information
		params: {
			pageNo: 1,     //Current page No, first is 1.
			pageSize: 5,   //max size of page.
			totalCount: 0, //total rows of all data.
			sortField: "", //sort field name, used by query.
			sortDir: "",   //sort direction(desc/asc).
			queryArgs: {}, //query arguments.
			data: []       //data of the page.
		},
		messageWidth: "",
		showMessage: true, //Boolean, if show paging message.
		//default paging message template.
		//{0} - total rows.
		//{1} - total pages.
		//{2} - max size of page.
		//{3} - current page No.
		messageTemplate: "total rows {0}, pages {1}, pageSize {2}, this is No. {3} Page.",
		
		//the widgetId of dojo FilteringTable, Paginator will refesh it to showing page data.		
		dojoTableWidgetId: "",
		//the FilteringTable widget that the Paginator associate with it.
		dojoTable: null,
		//fase step
		showFastStep: false,
		maxFastStep: 5,
		steps: [], //HTML DOM NODE
		fastStepStartPageNo: 1,
		/**
		 *
		 */
		postCreate: function() {
			
			if(this.dojoTableWidgetId) {
				 this.dojoTable = dojo.widget.byId(this.dojoTableWidgetId);
		  }
			this._connectEvents();
			this._setInformation();
			this._buildFastStep();			
		},
		/**
		 * Build fast steps.
		 */
		_buildFastStep: function() {
			if(!this.showFastStep) {
				this.fastStepTd.width = 0;
				return;
			}
			
		  if(this.fastStepTd) { 
		  	this.fastStepTd.align = 'right';
		  	this._resetFastStep();
		  }		  
		},
		
		/**
		 * _resetFastStep
		 */
		_resetFastStep: function() {
			if(!this.showFastStep) {
				this.fastStepTd.width = 0;
				return;
			}
			if(this.steps) {
				for(var i = 0; i < this.steps.length; i++) {
					  dojo.html.removeNode(this.steps[i]);
				}
			}
			var inactiveClass = "paginatorFastStepInactiveClass";
			var activeClass = "paginatorFastStepActiveClass";
			var startPage = this._calcStartPage();
			var stepSize = this._calcStepPageSize();
			
			var index = 0;
		  for(var i = startPage; i < startPage + stepSize; i ++) {
		  	this.steps[index] = document.createElement('span');
		  	this.steps[index].innerHTML = i;
		  	this.steps[index].value=i;
		  	if(i == this.params.pageNo) {
		  		dojo.html.setClass(this.steps[index], activeClass);
		  	}
		  	else {
		  	    dojo.html.setClass(this.steps[index], inactiveClass);
		  	    dojo.event.connect(this.steps[index], "onclick", this, "_onFastStep");
		    }
		    dojo.html.insertBefore(this.steps[index], this.placeholder);
		    index ++;
		  }
		},
		
		
		/**
		 * calculate fist page No of fast step.
		 */
		_calcStartPage: function() {
			if(this.params.pageNo < (this.maxFastStep / 2) ||
			  (this.params.pageNo - parseInt(this.maxFastStep / 2)) < 1) {
				return 1;
			}
			else {
				return this.params.pageNo - parseInt(this.maxFastStep / 2);
			}
		},
		
		/**
		 * calculate current fast step page size.
		 */
		_calcStepPageSize: function() {
			if((this._calcStartPage() + this.maxFastStep) > this._getTotalPages()) {
				return this._getTotalPages() - this._calcStartPage() + 1;
			} else {
       return this.maxFastStep;
      }
			
		},
		
		/**
		 * fast step clicked.
		 */
		_onFastStep: function(/*Event*/e) {
			var toPageNo = e.target.value;
			this.params = this.pagingFunction({queryArgs: this.params.queryArgs,
				                                sortField: this.params.sortField,
				                                sortDir:   this.params.sortDir,
				                                pageNo:    toPageNo,
				                                pageSize:  this.params.pageSize});		
			this._onPage();  //refresh
		},
		
		/**
		 * Refresh the pageing information.
		 */
		_setInformation: function() {
			if(!this.showMessage) {
				return;
			}
			var infoNode = document.getElementById(this.widgetId + "information");
			if(infoNode) {
				infoNode.innerHTML = this.messageTemplate.replace("{0}", this.params.totalCount)
				  .replace("{1}", this._getTotalPages())
				  .replace("{2}",this.params.pageSize)
				  .replace("{3}",this.params.pageNo);
			}
		},
		
		_connectEvents: function(){
			dojo.event.connect(this.firstImageNode, "onclick", this, "_onFirstClick");			
			dojo.event.connect(this.prevImageNode,  "onclick", this, "_onPrevClick");
			dojo.event.connect(this.nextImageNode,  "onclick", this, "_onNextClick");
			dojo.event.connect(this.lastImageNode,  "onclick", this, "_onLastClick");
		},
		
		/**
		 * The firstPage button click
		 */
		_onFirstClick: function() {	
			if(this._isFirstPage()) { // if current page is the first, do nothing.
				 return;
			}
			this.params = this.pagingFunction({queryArgs: this.params.queryArgs,
				                                sortField: this.params.sortField,
				                                sortDir:   this.params.sortDir,
				                                pageNo:    1,
				                                pageSize:  this.params.pageSize});		
			this._onPage();  //refresh
		},
		/**
		 * The previous page button click
		 */
		_onPrevClick: function() {
			if(this._isFirstPage()) { // if current page is the first, do nothing.
				 return;
			}
			this.params = this.pagingFunction({queryArgs: this.params.queryArgs,
				                                sortField: this.params.sortField,
				                                sortDir:   this.params.sortDir,
				                                pageNo:    this.params.pageNo - 1,
				                                pageSize:  this.params.pageSize});
		  this._onPage(); //refresh
		},
		/**
		 * The next page button click
		 */
		_onNextClick: function() {
			if(this._isLastPage()) { // if current page is the last, do nothing.
				 return;
			}
			this.params = this.pagingFunction({queryArgs: this.params.queryArgs,
				                                sortField: this.params.sortField,
				                                sortDir:   this.params.sortDir,
				                                pageNo:    this.params.pageNo + 1,
				                                pageSize:  this.params.pageSize});
		  this._onPage(); //refresh
		},
		
		/**
		 * The last page button click
		 */
		_onLastClick: function() {
			if(this._isLastPage()) { // if current page is the last, do nothing.
				 return;
			}
			this.params = this.pagingFunction({queryArgs: this.params.queryArgs,
				                                sortField: this.params.sortField,
				                                sortDir:   this.params.sortDir,
				                                pageNo:    this._getTotalPages(),
				                                pageSize:  this.params.pageSize});
		  this._onPage(); //refresh.
		},
		/**
		 * Return page count.
		 */
		_getTotalPages: function() {
			 if (this.params.totalCount % this.params.pageSize == 0) {
        return parseInt(this.params.totalCount/this.params.pageSize);
       } 
       else {
        return parseInt(this.params.totalCount/this.params.pageSize) + 1;
       }
		},
		
		/**
		 * Refresh the widget, reset dojoTable, paging information and buttons status.
		 */
		_onPage: function() {
			if(!this.params) {
				return;
			}
			this.refreshTable();			
			this._setInformation();
			this._resetButtons();
			this._resetFastStep();
		},
		
		refreshTable: function() {
			if(this.dojoTable && this.params.data) {
				this.dojoTable.store.setData(this.params.data);
			}
		},
		
		/**
		 * Reset button images.
		 */
		_resetButtons: function() {
			if(this._isFirstPage()) {
				if(this.firstImageNode) {
					this.firstImageNode.src = this.firstImageDisabledSrc;
				} 
				
				if(this.prevImageNode) {
					this.prevImageNode.src = this.prevImageDisabledSrc;
				}
			} 
			else {
				if(this.firstImageNode) {
					this.firstImageNode.src = this.firstImageSrc;
				} 
				if(this.prevImageNode) {
					this.prevImageNode.src = this.prevImageSrc;
				}
			}
			
			if(this._isLastPage()) {
				if(this.lastImageNode) {
					this.lastImageNode.src = this.lastImageDisabledSrc;
				} 
				
				if(this.nextImageNode) {
					this.nextImageNode.src = this.nextImageDisabledSrc;
				}
			} 
			else {
				if(this.lastImageNode) {
					this.lastImageNode.src = this.lastImageSrc;
				} 
				if(this.nextImageNode) {
					this.nextImageNode.src = this.nextImageSrc;
				}
			}
		},
		
		/**
		 * Current page is the first page?
		 */
		_isFirstPage: function() {
			return this.params.pageNo == 1;
		},
		/**
		 * Curretn page is the last page?
		 */
		_isLastPage: function() {
			return this.params.pageNo == this._getTotalPages();
		},
		/**
		 * callback function, invoked when paing buttons clicked.
		 * User must implement.
		 * @param args a javascript object: {queryArgs: query arguments,
				                                sortField: sort field name,
				                                sortDir:   sort direction,
				                                pageNo:    current page to be display,
				                                pageSize:  max pageSize}
		 
		 */
		pagingFunction: function(args) {
			return {};
		},
		
		/**
		 * Set parameters of the Paginator.
		 */
		setParams: function(params) {
			this.params = params;
			this.refreshTable();
			this._setInformation();
			this._resetButtons();
			this._resetFastStep();
		}
	}
);	