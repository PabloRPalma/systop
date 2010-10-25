<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>FCKEditor</title>
<script type="text/javascript" src="fckeditor.js"></script>
<script type="text/javascript" src="/js/jquery/jquery-1.3.2.js"></script>      
</head>
<script type="text/javascript">

$(function() {
	var fckEditor;
	fckEditor = new FCKeditor( 'content' ) ;
	fckEditor.BasePath = "/js/fckeditor/";
	fckEditor.Config['SkinPath'] = fckEditor.BasePath + 'editor/skins/default/';
	fckEditor.ToolbarSet = 'Default'; //Basic
	fckEditor.Height = 200;
	fckEditor.ReplaceTextarea();
	
	
	$('#gethtml').click(function()	{
		var oEditor = FCKeditorAPI.GetInstance('content') ;
		alert(oEditor.GetXHTML());
		});
});
</script>
<body>
<input type="button" value="取得HTML" id="gethtml"/>
<br>
<textarea rows="30" cols="80" id="content"></textarea>
</body>
</html>