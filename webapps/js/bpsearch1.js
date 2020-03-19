//$Id$
(function (window) {
	
	var events = ["click"];//No i18n
	
	for(var i = 0; i < events.length; i++){
		document.addEventListener(events[i],function(e){
			//close selectJS element.
			var isSelectJSElement = false;
			if(e.target && e.target.classList){
				var clazz = e.target.classList
				for(var j = 0; j < clazz.length; j++){
					if(clazz[j].startsWith("selectJS-")){
						isSelectJSElement = true;
						break;
					}
				}
			}
			if(!isSelectJSElement){
				var container = document.querySelector("#selectJS_parent_container");//No i18n
				if(container){
					container.remove();
				}
			}
		});
	}
	
    function SelectJS(props) {
        if(typeof props !== "object" || !props.id){
        	throw new Error("Invalid Properties");//No i18n
        } 
    	var elem = document.querySelector("#"+props.id);
    	if(!elem || elem.tagName !== "SELECT"){
    		throw new Error("Can't load selectJS for given Element.");//No i18n
    	}
    	
    	this._ID = elem.id;
    	if(typeof props.optionResult === "function"){
    		this._OptionsResult = props.optionResult;
    	}
    	if(typeof props.optionSelection === "function"){
    		this._OptionSelection = props.optionSelection;
    	}
    	if(typeof props.matcher === "function"){
    		this._matcher = props.matcher;
    	}
    	this.isOpen = false;
    	this.hideDuplicates = props.hideDuplicates ? !!props.hideDuplicates : this.Constants.hideDuplicates;
    	this.hideSelected = props.hideSelected ? !!props.hideSelected : this.Constants.hideSelected;
    	this.maxSelection = props.maxSelection && !isNaN(props.maxSelection) ? Number(props.maxSelection) : this.Constants.maxSelection; 
    	
    	this._containerId = "selectJS_parent_container";//No i18n
    	elem._SelectJS = this;
    	
    	this.showSelected();
    };
    
    SelectJS.prototype = {
    	
    	Constants : {
    		hideDuplicates : true,
    		hideSelected : true,
    		maxSelection : 100
    	},
    	
		getDOM : function(string){
    		var dom = document.createElement("template");
			dom.innerHTML = string;
			return dom.content && dom.content.children && dom.content.children.length ? dom.content.children[0] : null;
    	},
    	
    	getElement : function(){
    		const elem = document.querySelector("#"+this._ID);
    		if(!elem){
    			throw new Error("Element does not Exists.");//No i18n
    		}
        	return elem;
    	},
    	
    	getSelectedElement : function(){
    		const elem = document.querySelector("#selectJS-"+this._ID+"-selected-container");//No i18n
    		if(!elem){
    			throw new Error("Please initialize selectJs for the Element.");//No i18n
    		}
    		return elem;
    	},
    	
    	getPositions : function(elem){
    		var rect = elem.getClientRects().length > 0 ? elem.getBoundingClientRect() : null, winW = window.innerWidth, winH = window.innerHeight;
    		return {top: rect ? rect.top : 0, left: rect ? rect.left : 0, bottom : (winH - (rect ? rect.top : 0)), right : (winW - (rect ? rect.left : 0))};
    	},
    		
    	getOptions : function(elem, qry){
    		var _selectJS = this, results = [], vals = [];
    		if(elem){
	    		qry = qry ? qry : "";
	    		if(elem){
	    			var opts = elem.options;
	    			for(var i = 0; i < opts.length; i++){
	    				var isMatched = !qry;
	    				if(!isMatched){
		    				if(_selectJS._matcher){
		    					isMatched = _selectJS._matcher(opts[i],qry)
		    				}else{
		    					isMatched = opts[i].text.toLowerCase().indexOf(qry.toLowerCase()) != -1;
		    				}
	    				}
	    				isMatched && (!_selectJS.hideDuplicates || (_selectJS.hideDuplicates && !vals.indexOf(opts[i].value) != -1)) && (!_selectJS.hideSelected || (_selectJS.hideSelected && !opts[i].selected)) ? results.push(opts[i]) : null;
	    				vals.push(opts[i].value);
	    			}
	    		}
    		}
    		return results;
    	},
    	
    	getSelectedOptions : function(elem){
    		var results = [];
    		if(elem){
    			var opts = elem.options;
    			for(var i = 0; i < opts.length; i++){
    				opts[i].selected ? results.push(opts[i]) : null;
    			}
    		}
    		return results;
    	},
    	
    	val : function(elem){
    		var results = [];
    		if(elem){
    			var opts = elem.options;
    			for(var i = 0; i < opts.length; i++){
    				opts[i].selected ? results.push(opts[i].value) : null;
    			}
    		}
    		return this.multiple ? results : results.length ? results[0] : null;
    	},
    	
    	
    	
    	getSelectedContainer : function(elem){
    		var _selectJS = this, container = _selectJS.getDOM(elem.multiple ? '<span id="selectJS-'+_selectJS._ID+'-selected-container" class="selectJS-select-container"><span class="selectJS-selection"><span class="selectJS-selection-multiple" id="selectJS-selected-container"></span></span><span>' : '<span id="selectJS-'+_selectJS._ID+'-selected-container" class="selectJS-select-container"><span class="selectJS-selection"><span class="selectJS-selection-single" id="selectJS-selected-container"></span></span><span>');
    		container.onclick = function(e){
    			_selectJS.Utils.stopEvent(e);
    			_selectJS.open();
    		};
    		return container;
    	},
    	
    	getSelectedHTML : function(elem){
    		var _selectJS = this, ops = _selectJS.getSelectedOptions(elem), dom = null;
    		if(!elem.multiple){
    			dom = ops.length ? _selectJS.getDOM('<span class="selectJS-selection-rendered" title="'+ops[0].text+'">'+ops[0].text+'</span>') : null;
    			if(_selectJS._OptionSelection){
    				_selectJS._OptionSelection(dom,ops[0]);
    			}
    		}else{
    			if(ops && ops.length){
    				dom = _selectJS.getDOM('<ul class="selectJS-selection-rendered-ul"></ul>');
    				for(var i = 0; i < ops.length; i++){
    					const op = ops[i], li = _selectJS.getDOM('<li class="selectJS-selection-rendered-li" title="'+op.text+'"><span class="selectJS-selection-rendered-remove">×</span>'+op.text+'</li>');
    					li.querySelector("[class=selectJS-selection-rendered-remove]").onclick = function(e){//No i18n
    						_selectJS.Utils.stopEvent(e);
    						this.parentNode.remove();
    						op.selected = false;
    					}
    					if(_selectJS._OptionSelection){
    	    				_selectJS._OptionSelection(li,op);
    	    			}
    					dom.appendChild(li);
    				}
    			}
    		}
    		return dom;
    	},
    	
    	showSelected : function(){
    		var _selectJS = this, elem = _selectJS.getElement(), container = _selectJS.getSelectedContainer(elem);
    		
    		var prev = document.querySelector('#selectJS-'+_selectJS._ID+'-selected-container');//No i18n
    		if(prev){
    			prev.remove();
    		}
    		
    		elem.className = elem.className+" selectJS-selectbox";
    		
    		var result_container = container.querySelector("#selectJS-selected-container"), html = _selectJS.getSelectedHTML(elem);//No i18n
    		result_container && html ? result_container.appendChild(html) : null;
    		elem.parentNode.insertBefore(container,elem.nextElementSibling);
    	},
    	
    	
    	
    	
    	
    	
    	
    	
    	/**
    	 * Construct dropdown
    	 */
    	getDropdownContainer : function(pos){
    		var _selectJS = this, container = _selectJS.getDOM('<span id="'+_selectJS._containerId+'" class="selectJs-dropdown-container"><span class="selectJs-dropdown-results"></span></span>');
    		if(pos){
    			container.style.top = pos.top+"px";container.style.left = pos.left+"px";
    		}
    		var elem = document.querySelector("#selectJS-"+_selectJS._ID+"-selected-container");//No i18n
    		if(elem){
    			container.style.width = elem.clientWidth+"px";
    		}
    		return container;
    	},
    	
    	getSearchContainer : function(elem,ul){
    		var _selectJS = this, searchContainer = _selectJS.getDOM('<span class="selectJS-searchbox"><input class="selectJS-searchinput" type="text" tabindex="0" autocomplete="off" autocorrect="off" autocapitalize="off" spellcheck="false"></span>');
			
			var input = searchContainer.querySelector("input[type=text]");//No i18n
			input.onkeyup = function(){
				var qry = this.value, ops = _selectJS.getOptions(elem,qry), lis = ul.querySelectorAll("li");//No i18n
				for(var i = 0; i < lis.length; i++){
					lis[i].remove();
				}
				_selectJS.appendLiElements(elem,ops,ul);
			}
			return searchContainer;
    	},
    	
    	getDropdownHTML : function(elem,parent){
    		if(parent){
	    		var _selectJS = this, ul = _selectJS.getDOM('<ul class="selectJs-dropdown-results-options" id="selectJs_'+_selectJS._ID+'_results"></ul>'), ops = _selectJS.getOptions(elem), selOps = _selectJS.getSelectedOptions(elem);
	    		if(ul){
		    		if(selOps && selOps.length >= _selectJS.maxSelection){
		    			var li = _selectJS.getDOM('<li class="selectJs-dropdown-results-option">You can select only '+_selectJS.maxSelection+' items.</li>');
		    			if(li){
							li.onclick = function(){
								_selectJS.close();
							}
							ul.appendChild(li);
						}
		    		}else{
		    			parent.appendChild(_selectJS.getSearchContainer(elem,ul));
		    			_selectJS.appendLiElements(elem,ops,ul);
		    		}
	    		}
	    		parent.appendChild(ul);
    		}
    	},
    	
    	appendLiElements : function(elem,ops,ul){
    		var _selectJS = this;
    		if(ul){
	    		if(ops && ops.length){
	    			for(var i = 0; i < ops.length; i++){
	    				const op = ops[i], val = op.value, text = op.text; 
	    				var li = _selectJS.getDOM('<li class="selectJs-dropdown-results-option" value="'+(val ? val : "")+'">'+(text ? text : "")+'</li>');
	    				if(_selectJS._OptionsResult){
	    					_selectJS._OptionsResult(li);
	    				}
	    				if(li){
	    					li.onclick = function(){
	    						var option = elem.querySelector("option[value="+val+"]");//No i18n
	    						if(!option){
	    							option = _selectJS.getDOM('<option value="'+val+'">'+text+'</option>')
	    							elem.appendChild(option);
	    						}
	    						!elem.multiple ? (elem.value = val ? val : "") : option.selected = true;
	    						_selectJS.close();
	    						_selectJS.showSelected();
	    					}
	    					ul.appendChild(li);
	    				}
	    			}
	    		}else{
	    			var li = _selectJS.getDOM('<li class="selectJs-dropdown-results-option">No Results Found.</li>');
	    			if(li){
						li.onclick = function(){
							_selectJS.close();
						}
						ul.appendChild(li);
					}
	    		}
    		}
    	},
    	
    	loadMore : function(elem,ul){
    		var  _selectJS = this;
    		if(ul && _selectJS.transport){
	    		if(_selectJS.isLoading){
	    			return;
	    		}
	    		_selectJS.isLoading = true;
	    		
	    		var li = _selectJS.getDOM('<li class="selectJs-dropdown-results-option">Loading Results...</li>');
	    		ul.appendChild(li);
	    		
	    		_selectJS.ajax.transport(function(results){
	    			ul.removeChild(li);
		    		if(results && results.length){
		    			var ops = [];
		    			for(var i = 0; i < results.length; i++){
		    				ops.push({text: results[i].text, value: results[i].value});
		    			}
		    			_selectJS.appendLiElements(elem,ops,ul);
		    		}
		    		_selectJS.isLoading = false;
	    		},function(){
	    			//failure case.
	    			_selectJS.isLoading = false;
	    		});
    		}
    	},
    	
    	
    	open : function(qry){
    		var _selectJS = this;
    		if(this.isOpen){
    			return;
    		}
    		_selectJS.close();
    		
    		var elem = _selectJS.getElement(), options = _selectJS.getOptions(elem), scrollParents = _selectJS.Utils.getScrollParents(elem), selectedElement = _selectJS.getSelectedElement(), positions = _selectJS.getPositions(selectedElement);

    		var container =  _selectJS.getDropdownContainer(_selectJS.Utils.getAvailablePosition(selectedElement,positions)), resultContainer = container ? container.querySelector("[class=selectJs-dropdown-results]") : null;//No i18n
    		if(resultContainer){
    			this.getDropdownHTML(elem,resultContainer);
    		}
    		document.body.appendChild(container);
    		
    		if(scrollParents && scrollParents.length){
    			for(var i = 0; i < scrollParents.length; i++){
    				scrollParents[i].addEventListener("scroll",function(){
    					_selectJS.close();
    				});
    			}
    		}
    	},
    	
    	close : function(){
    		var _selectJS = this, container = document.querySelector("#"+this._containerId);
    		if(container){
    			container.remove();
    		}
    		_selectJS.isLoading = false;
    		_selectJS.isOpen = false;
    	},
    	
    	Utils : {
    		
    		stopEvent : function(event){
        		typeof event.stopPropagation == "function" ? event.stopPropagation() : null; // no i18n
        		typeof event.stopImmediatePropagation == "function" ? event.stopImmediatePropagation() : null;// no i18n
        		typeof event.preventDefault == "function" ? event.preventDefault() : (event.returnValue = false);// no i18n
        	},
        	
    		hasScroll : function(el) {
	    	    if(el){
		    	    var scrollX = el.style.overflowX, scrollY = el.style.overflowY;
		    	    return scrollX === scrollY && (scrollY === 'hidden' || scrollY === 'visible') ? false : (scrollX === 'scroll' || scrollY === 'scroll') || (el.clientHeight < el.scrollHeight || el.clientWidth < el.scrollWidth);//No i18n
	    	    }
	    	    return false;
    		},

    		escapeMarkup : function (markup) {
    			var replaceMap = {'\\': '&#92;','&': '&amp;','<': '&lt;','>': '&gt;','"': '&quot;','\'': '&#39;','/': '&#47;'};
    			if(typeof markup === 'string') {
    				return markup;
    			}
    			return String(markup).replace(/[&<>"'\/\\]/g, function (match) {
    				return replaceMap[match];
    			});
    		},
    		
    		getScrollParents : function(el){
    			var scrollParents = [], elem = el;
    			while(elem != null){
    				var parent = elem.parentNode;
    				if(this.hasScroll(parent)){
    					scrollParents.push(parent);
    				}
    				elem = parent.tagName !== "BODY" && parent.tagName !== "HTML" ? parent : null;//No i18n
    			}
    			return scrollParents;
    		},
    		
    		getAvailablePosition : function(elem,actualPosition){
    			if(elem){
    				var selectJS_Maxheight = 320, h = elem.clientHeight, winH = window.innerHeight, winW = window.innerWidth;//300 is assumption. TODO: make constant.
    				actualPosition.top += h;
    				if(winH < (actualPosition.top + selectJS_Maxheight)){
    					actualPosition.top -= selectJS_Maxheight;
    				}
    			}
    			return actualPosition;
    		}
    	}
    }
    
    window.SelectJS = SelectJS;

})(window);
    	