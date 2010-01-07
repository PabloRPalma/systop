function DeptOperator() {

	this.init = function(elemanetName, showNameDivId) {
		this.showDeptName(elemanetName, showNameDivId);
	};

	this.showDeptName = function(elemanetName, showNameDivId) {
		// 获得所有选择框
		var inputs = document.getElementsByName(elemanetName);

		// 获得显示部门名称的DIV
		var showNameDiv = document.getElementById(showNameDivId);

		// 定义数组,存放被选择的部门名称
		var selectedDeptNames = [];
		for (i = 0; i < inputs.length; i++) {
			if (inputs[i].checked == true) {
				selectedDeptNames.push(inputs[i].deptName);
			}
		}
		showNameDiv.innerHTML = "&nbsp;";
		var selectedDeptNameStr = "";
		for (i in selectedDeptNames) {
			selectedDeptNameStr += "<b>" + selectedDeptNames[i] + "</b>&nbsp;&nbsp;";
		}
		showNameDiv.innerHTML = selectedDeptNameStr;
	};
}

// 创建部门操作管理对象
var deptOperator = new DeptOperator();