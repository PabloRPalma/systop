var RoleManager;
RoleManager = new Ext.Window({
    el: 'RoleManager',
    width: 360,
    height: 200,
    layout : 'fit',
    closeAction:'hide',
    buttonAlign:'center',
    resizable: false,
    modal:'true',//启用遮罩
    buttons:[{
       text:'保存',
       handler:function(){
        if (!RoleManager.check()){
       	 var rId = document.getElementById('rId').value;
       	 var rName = document.getElementById('rName').value;
       	 var u = encodeURI('/security/rbac/role/checkName.do?rName='+rName+'&rId='+rId);
       	 Ext.Ajax.request({
       	   	url : u,
       	   	method : 'GET',
       	   	success : function(response) {
       	   		var rst = Ext.util.JSON.decode(response.responseText)['result'];
       	   		if (rst == "exist") {
       	   		  Ext.MessageBox.alert('提示', '您所输入的角色名称已存在！');
       	   		} else {
       	   		  document.getElementById('roleForm').submit();
       	   	      RoleManager.hide();
       	   		}
       	   	  }
       	    });
         }
       }
    }, {text:'取消',
        handler:function(){RoleManager.hide();}
    }]
  });
RoleManager.check = function() {
	var roleName = document.getElementById('rName').value;
	var rDescn = document.getElementById('rDescn').value;
	var roleDescn = rDescn.replace(/(^\s*)/g, "");
	var patrn = /^ROLE_/;

	if (roleName == null || roleName == '') {
		Ext.MessageBox.alert('提示', '角色名称不能为空！');
		return true;
	} else {
		if (!patrn.exec(roleName)) {
			Ext.MessageBox.alert('提示', '角色名称必须以“ROLE_”开始！');
			return true;
		}
		if (getLen(roleName) > 255) {
			Ext.MessageBox.alert('提示', '角色名称过长！');
			return true;
		}
	}
	if (roleDescn == null || roleDescn == '') {
		Ext.MessageBox.alert('提示', '角色描述不能为空！');
		return true;
	} else {
		if (getLen(roleDescn) > 255) {
			Ext.MessageBox.alert('提示', '角色描述信息过长！');
			return true;
		}
	}
	return false;
}
// 显示
RoleManager.showMe = function(rId, rName, rDescn) {
	document.getElementById('rId').value = rId;
	document.getElementById('rName').value = rName;
	document.getElementById('rDescn').value = rDescn;
	if (rId == null || rId == '') {
	  document.getElementById('edit_div').innerHTML = "新建角色";
	}else{
	  document.getElementById('edit_div').innerHTML = "编辑角色";
	}
	RoleManager.show();
}
function getLen(str) {
	var totallength = 0;
	for ( var i = 0; i < str.length; i++) {
		var intCode = str.charCodeAt(i);
		if (intCode >= 0 && intCode <= 128) {
			totallength = totallength + 1;
		} else {
			totallength = totallength + 2;
		}
	}
	return totallength;
}
Ext.onReady( function() {
	var tb = new Ext.Toolbar();
	tb.render('toolbar');
	tb.addElement(Ext.get('queryFrm').dom);
	tb.addButton( {
		text :'查询',
		handler : function() {
			Ext.get('queryFrm').dom.submit();
		}
	});

	tb.addButton( {
		text :'新建',
		handler : function() {
			RoleManager.showMe('', '', '');
		}
	});
	tb.addButton( {
		text :'删除',
		handler : function() {
			onRemove();
		}
	});
});
function onRemove() {
	var selectItem = document.getElementsByName("selectedItems");
	var j = 0;
	for ( var i = 0; i < selectItem.length; i++) {
		if (selectItem[i].checked) {
			j++;
		}
	}
	if (j > 0) {
		Ext.MessageBox.confirm('提示', '确定删除所选择的角色吗？', function(btn) {
			if (btn == 'yes') {
				document.getElementById('ec').action = "remove.do";
				document.getElementById('ec').submit();
			}
		});
	} else {
		Ext.MessageBox.alert('提示', '请选择要删除的角色！');
	}
}