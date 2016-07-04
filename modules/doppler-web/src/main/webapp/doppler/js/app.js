var App = function(){
	return {
		
		query : function(){
			var url = $.modulePath + "/app/appDetailList.shtml";
			$.ajax({
				url:url,
				data: App.queryParams(),
				dataType:'json',
				success:function(data){
					if(data.length == 1 && !data[0].isDefineException){
						$("#appTable tbody:first").empty().append('<tr align="center" class="tr.no-records-found"><td colspan="6">后台正在执行任务，请3秒后重试</td></tr>');
					}else if(data.length == 1 && !data[0].isSystemException){
						$("#appTable tbody:first").empty().append('<tr align="center" class="tr.no-records-found"><td colspan="6">服务器异常</td></tr>');
					}else{
						$("#appTable").bootstrapTable('load',data);
					}
				}
			});
		},

		nameFormatter : function(value, row, index) {
			var appId = row.appId;
			var appName = row.appName;
		    return '<a href="javascript:App.toServices('+ appId +',\'' + appName +'\')">' + value + '</a>';
		},
		
		toServices : function(appId, appName){
			$.post($.modulePath + "/view/services.shtml", function(data){
				$(".page-content").empty();
				$(".page-content").append(data);
				
				$("#appId").val(appId);
				$("#appName").html(appName);
			});
		},
		
		queryParams : function(){
			var params = {'date' : $("#date").val()};
			 return params;
		},
		
		toPercent : function(value){
			return value.toPercent();
		}
	};
}();