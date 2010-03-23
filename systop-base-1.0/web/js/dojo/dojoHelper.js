DojoHelper = function() {
}

DojoHelper.prototype = {
    getValue: function(elementId) {
        var node = dojo.byId(elementId);
        return (node) ? node.value : null;
    },
    
    setValue: function(elementId, val) {
        var node = dojo.byId(elementId);
        if(node) {
            node.value = val;
        }
    },
    
    resetDatePicker: function(datePickerId) {
        var datePicker = dojo.widget.byId(datePickerId);
        datePicker.value = 'today';
        datePicker.inputNode.value="";
    },
    
    setDatePicker: function(datePickerId, date) {
        var datePicker = dojo.widget.byId(datePickerId);
        if(datePicker) {
            if(date) {
                datePicker.setDate(date);
            } else {
                datePicker.inputNode.value = "";
            }
            //datePicker.inputNode = date;
        }
    },
    
    getDatePickerValue: function(/*dojo DatePicker Id*/datePickerId) {
        var dp = dojo.widget.byId(datePickerId);
        if(!dp) {
            return null;
        }
        
        var date = dp.getDate();
        if(date == "" || dp.inputNode.value == "") {
            date = null;
        } else {
            date = Date.parse(date);
        }
        
        return date;
    }
}