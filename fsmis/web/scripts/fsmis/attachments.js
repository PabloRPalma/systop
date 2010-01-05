/*
 *	增加的文件上传控件file的name属性都为attachments,对应在后台action中的接收属性以此为参照
 */

function fileUtil() {
	this.style = "";
	this.deleteIcon = "";

	/** 增加附件上传框(TR) */
	this.add = function(tagName) {
		var tbody = document.getElementById(tagName);
		var fileIndex = tbody.childNodes.length + 1;
		var oTR = document.createElement("TR");
		var oTD1 = document.createElement("TD");
		var oTD2 = document.createElement("TD");
		var deleteIconStr = "删除";
		if (this.deleteIcon != "") {
			deleteIconStr = "<img src='" + this.deleteIcon + "'>";
		}
		oTR.setAttribute("id", "file_" + fileIndex);
		oTD1.setAttribute("style", "text-align:right;");
		oTD2.innerHTML = '<input type="file" name="attachments" style="'
				+ this.style + '"/>&nbsp;<a href="#" onclick="remove(\'' + tagName +'\',' + fileIndex + ')">' + deleteIconStr + '</a>';

		oTR.appendChild(oTD1);
		oTR.appendChild(oTD2);
		tbody.appendChild(oTR);
	};

}
/** 删除文件输入框(TR) */
function remove(tagName, fileIndex) {
	var oTR = document.getElementById("file_" + fileIndex);
	var tbody = document.getElementById(tagName);
	tbody.removeChild(oTR);
};
