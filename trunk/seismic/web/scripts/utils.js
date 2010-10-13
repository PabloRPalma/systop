/**
 * Use for delete rows in ecside
 * 
 * @param options = {
 *            noneSelectedMsg:"If user select nothing to delete",
 *            confirmMsg:"Confirm message.", ecsideFormId:"ecside from id",
 *            deleteFormId:"Remove form id, you must provide a form to post
 *            delete request." }
 */
function onRemove(options) {
	if (!options)
		options = {};
	var opt = {
		noneSelectedMsg : (options.noneSelectedMsg)
				? options.noneSelectedMsg
				: '请至少选择一个.',
		confirmMsg : (options.confirmMsg) ? options.confirmMsg : '确认删除吗?',
		ecsideFormId : (options.ecsideFormId) ? options.ecsideFormId : 'ec',
		deleteFormId : (options.deleteFormId)
				? options.deleteFormId
				: 'removeForm'
	};
	var checked = false;
	$('input').each(function(i, item) {
		if (item.checked && item.id == 'selectedItems') {
			checked = true;
		}
	});
	if (!checked) {
		alert(opt.noneSelectedMsg);
		return;
	}

	if (confirm(opt.confirmMsg)) {
		$('#' + opt.ecsideFormId)[0].action = $('#' + opt.deleteFormId)[0].action;		
		$('#' + opt.ecsideFormId)[0].submit();
	} else {
		return false;
	}
}

var Cookie = function() {
	if (document.cookie.length) {
		this.cookies = ' ' + document.cookie;
	}
};

Cookie.prototype.setCookie = function(key, value) {
	document.cookie = key + "=" + escape(value);
};

Cookie.prototype.getCookie = function(key) {
	if (this.cookies) {
		var start = this.cookies.indexOf(' ' + key + '=');
		if (start == -1) {
			return null;
		}
		var end = this.cookies.indexOf(";", start);
		if (end == -1) {
			end = this.cookies.length;
		}
		end -= start;
		var cookie = this.cookies.substr(start, end);
		return unescape(cookie.substr(cookie.indexOf('=') + 1, cookie.length
				- cookie.indexOf('=') + 1));
	} else {
		return null;
	}
};
var Util = function() {
};

Util.resetDatePicker = function(pickerId) {
    var picker = document.getElementById(pickerId);
    var v = picker.value;
    if(!v) return;
    var idx = v.indexOf('0:00:00.000');
    idx = (idx > 0) ? idx : v.indexOf(' 00:00:00.0');
    if( idx > 0) {  
        v = v.substring(0, idx);
    } 
    picker.value = v;
};