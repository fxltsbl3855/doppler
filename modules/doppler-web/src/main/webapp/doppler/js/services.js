$(document).ready(function() {
	Services.query();
});
var Services = function(){
	return {
		
		query : function(){
			$("#servicesTable").bootstrapTable('refresh');
			var url = $.modulePath + "/service/serviceDetailList.shtml";
			$.ajax({
				url:url,
				data: Services.queryParams(),
				dataType:'json',
				success:function(data){
					if(data.length == 1 && !data[0].isDefineException){
						$("#servicesTable tbody:first").empty().append('<tr align="center" class="tr.no-records-found"><td colspan="8">后台正在执行任务，请3秒后重试</td></tr>');
					}else if(data.length == 1 && !data[0].isSystemException){
						$("#servicesTable tbody:first").empty().append('<tr align="center" class="tr.no-records-found"><td colspan="8">服务器异常</td></tr>');
					}else{
						$("#servicesTable").bootstrapTable('load',data);
					}
				}
			});
		},
		
		nameFormatter : function(value, row, index) {
			 var spanViewId = row.spanViewId;
			 var spanView = row.spanView;
			 return '<a href="javascript:Services.toChars(\''+ spanViewId +'\',\''+ spanView +'\')">' + value + '</a>';
		},
	
		toChars : function(spanViewId, spanView){
			$.post($.modulePath + "/view/serviceChart.shtml", function(data){
				var date = $("#date").val();
				var appName = $("#appName").text();
				var appId = $("#appId").val();
				$(".page-content").empty();
				$(".page-content").append(data);
				
				$("#spanViewId").val(spanViewId);
				$("#date").val(date);
				$("#legendName").html('<a href="javascript:ServiceChart.toServer('+ appId +',\''+ appName +'\')">' + appName + '</a>' + '  >  ' + spanView);
			});
		},
		
		queryParams : function(){
			var params={
				'date': $("#date").val(),
				'serviceId' : $.trim($("#serviceSelect").val())
			};
			return params;
		},
		
		toPercent : function(value){
			return value.toPercent();
		}
	};
}();