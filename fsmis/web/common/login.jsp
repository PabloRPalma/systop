<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<stc:ifNotLogin>
<style>
.loginItem {
	border:1px solid #C1C1BB;
	font-family:verdana;
	height:16px;
	line-height:16px;
	padding:2px;
	width:215px;
}
</style>
	<div id="login_dialog">
	   <div id="error_name" style="display:none;color:#cc0000;">请输入正确的用户名!</div>
	   <div id="error_pwd" style="display:none;color:#cc0000;">请输入正确的密码!</div>
	   <div style="display:none;margin:0 auto;" id="loginPrc"><img src="${ctx}/images/loading.gif"></img>正在登录...</div>
	   <div style="width:50px;float:left;">用户名:</div>
	   <div style="float:left;"><input name="j_username" id="j_username" type="text" class="loginItem"/>
	   </div>
	   <div><a href="${ctx}/personal/reg.do">立即注册</a></div>
<br></br>
	   <div style="width:50px;float:left;">密码:
	   </div>
	   <div style="float:left;">
	     <input name="j_password" id="j_password" type="password" class="loginItem"/>
	   </div>   
	   <div><a href="${ctx}/restorePassword/edit.do">忘记密码</a></div>
	   <br></br>
	   <div><input type="checkbox" name="rememberme" id="rememberme"
					style="border: 0px;margin:0px;padding:0px">保存我的信息</div>
	</div>
	<script>
	function login() {
		$('#login_dialog').dialog('open');
	}
	$(function() {
		$('#login_dialog').dialog({
			autoOpen: false,
			width: 500,
			height:250,
			modal:true,
			buttons: {
				"登录": function() {
			        if($('#j_username').val() == '') {
			        	$('#error_name').fadeIn();
				        return;
			        }
			        if($('#j_password').val() == '') {
			        	$('#error_pwd').fadeIn();
				        return;
			        }
			        $.ajax({
				        url:'${ctx}/j_security_check?j_username=' 
					        + $('#j_username').val() + '&j_password=' 
					        + $('#j_password').val()
					        + ($('#rememberme').attr('checked')? '&rememberme=true':''),
					    type:'POST',
					    success: function (){
				        	$('#login_dialog').dialog("close");
				        	window.location.reload(true);				        	
				        },
				        beforeSend:function(){
				        	$('#loginPrc').show();
					    }
				    });
					 
				}, 
				"取消": function() { 
					$(this).dialog("close"); 
				} 
			}
		});	
		$('#j_username').keydown(function() {
			$('#error_name').fadeOut();
		});
		$('#j_password').keydown(function() {
			$('#error_pwd').fadeOut();
		});
	});
	</script>
</stc:ifNotLogin>
<stc:ifLogin>
<script>
function logout() {
	$.ajax({
		url:'${ctx}/j_acegi_logout',
		method:'POST',
		success:function(){
        	window.location.reload(true);	
		}
	});
}
</script>
</stc:ifLogin>