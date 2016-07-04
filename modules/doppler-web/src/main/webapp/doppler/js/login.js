$(function(){
   //错误提示
	var errorMsg=$.trim($("#errorMsg").val());
	if(errorMsg!=""){
		$("#alert_msg").empty().append("<div class='alert alert-error'><button type='button' class='close' data-dismiss='alert'>&times;</button><strong></strong> "+errorMsg+"</div>");
	}
	
});

var Login = function(){
	return{
		/**登录*/
		login : function(){
			if ($("#username").val() == ""){
					$("#alert_msg").empty().append("<div class='alert alert-error'><button type='button' class='close' data-dismiss='alert'>&times;</button><strong></strong> 用户名不能为空</div>");
				return;
			} else if ($("#password").val() == ""){
					$("#alert_msg").empty().append("<div class='alert alert-error'><button type='button' class='close' data-dismiss='alert'>&times;</button><strong></strong> 密码不能为空</div>");
				return;
			} 
			$("#loginForm").submit();
		}
	}
}();