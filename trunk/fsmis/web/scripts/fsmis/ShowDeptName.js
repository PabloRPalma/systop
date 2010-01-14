function DeptOperator() {

	/**
	 * 初始化
	 * @param elemanetName
	 * @param showNameDivId
	 * @return
	 */
	this.initShowDept = function(elemanetName, showNameDivId) {
		this.elemanetName = elemanetName;
		this.showDeptName(elemanetName, showNameDivId);
	};
	
	/**
	 * 在指定的DIV显示已经选中的部门名称
	 * @param elemanetName
	 * @param showNameDivId
	 * @return
	 */
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
		var selectedDeptNameStr = "&nbsp;";
		for (i in selectedDeptNames) {
			selectedDeptNameStr += "<b>" + selectedDeptNames[i] + "</b>&nbsp;&nbsp;";
		}
		showNameDiv.innerHTML = selectedDeptNameStr;
	};
	
	/**
	 * 获得重复的牵头部门名称
	 * @param leaderElId
	 * @param deptElName
	 * @return
	 */
	this.getRepetitiveLeader = function(leaderElId, deptElName){
		var selt = document.getElementById(leaderElId);
		var inpts = document.getElementsByName(deptElName);
		var opts = selt.options;
		if (opts != null){
			for(i = 0; i < opts.length; i++){
				if (opts[i].selected){
					for(j = 0; j < inpts.length; j++){
						if (inpts[j].checked == true && inpts[j].value == opts[i].value){
							return opts[i].text;
						}
					}
				}
			}
		}
		return "";
	};
}

// 创建部门操作管理对象
var deptOperator = new DeptOperator();