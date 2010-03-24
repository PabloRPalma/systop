function importResources() {
	if (confirm("确定要导入资源吗?")) {
		ResourceAction.saveUrl(function(result) {
			ECSideUtil.reload('ec');
		});
	}
}