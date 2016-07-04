
$(document).ready(function(){
	

	$.post($.modulePath + "/service/serviceChart.shtml", {
		"date" : $("#date").val(),
		"spanViewId" : $("#spanViewId").val()
	}, function(data) {
		if(!data[0].isDefineException){
			$("#chart1").empty().append('后台正在执行任务，请3秒后重试');
		}else if(!data[0].isSystemException){
			$("#chart1").empty().append('服务器异常');
		}
		else{
			var xAxisDatas = new Array();
			var seriesDatas = new Array();
			// 动态加载数据
			for (var i in data){
				xAxisDatas.push(data[i].date);
				seriesDatas.push(data[i].reqNum);
			}
			
			// 指定图表的配置项和数据
			var chart1 = echarts.init(document.getElementById('chart1'));
			options = {
				    tooltip : {
				        trigger: 'axis'
				    },
				    legend: {
				    },
				    calculable : true,
				    xAxis : [
				        {
				            type : 'category',
				            boundaryGap : false,
				            data : xAxisDatas
				        }
				    ],
				    yAxis : [
				        {
				            type : 'value'
				        }
				    ],
				    series : [
				        {
				            name:'调用量',
				            type:'line',
				            data:seriesDatas
				        }
				    ]
				};
			chart1.setOption(options);
		}
		});
	});

var ServiceChart = function(){
	return {
		toServer : function(appId, appName){
			$.post($.modulePath + "/view/services.shtml", function(data){
				$(".page-content").empty();
				$(".page-content").append(data);
				
				$("#appId").val(appId);
				$("#appName").html(appName);
			});
		}
	};
}();
