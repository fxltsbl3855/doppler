$(document).ready(function(){
	
	$.post($.modulePath + "/app/dashboard.shtml",function(data){
		var webNum = data.webNum;
		var serverNum = data.serverNum;
		var webReqNum = data.webReqNum;
		var serverReqNum = data.serverReqNum;
		var webReqList = data.webReqList;
		var serverReqList = data.serverReqList;
		
		$("#webNum").html(webNum);
		$("#serverNum").html(serverNum);
		$("#webReqNum").html(webReqNum);
		$("#serverReqNum").html(serverReqNum);
		
		var webChart = echarts.init(document.getElementById('webChart'));
		var serverChar = echarts.init(document.getElementById('serverChar'));
		var webLegendDatas = new Array();	
		var webSeriesDatas = new Array();
		// 动态加载数据
		for (var i in webReqList){
			webLegendDatas.push(webReqList[i].appName);
			var webSeriesData = new Object();
			webSeriesData.value = webReqList[i].num;
			webSeriesData.name =webReqList[i].appName;
			webSeriesDatas.push(webSeriesData);
		}
		
		var serverLegendDatas = new Array();	
		var serverSeriesDatas = new Array();
		// 动态加载数据
		for (var i in serverReqList){
			serverLegendDatas.push(serverReqList[i].appName);
			var serverSeriesData = new Object();
			serverSeriesData.value = serverReqList[i].num;
			serverSeriesData.name =serverReqList[i].appName;
			serverSeriesDatas.push(serverSeriesData);
		}
		// 指定图表的配置项和数据
		var webOptions = {
		    tooltip : {
		        trigger: 'item',
		        formatter: "{a} <br/>{b} : {c} ({d}%)"
		    },
		    legend: {
		        orient: 'horizontal',
		        top: 'top',
		        data: webLegendDatas
		    },
		    series : [
		        {
		            name: '访问来源',
		            type: 'pie',
		            radius : '55%',
		            center: ['50%', '60%'],
		            data:webSeriesDatas,
		            itemStyle : {
		                normal : {
		                    label : {
		                        show :true,
		                        position:'top',
		                        formatter: "{b}:\n{c}",
		                        textStyle : {
		                        color : 'blue',
		                        fontSize : 12,
		                        fontWeight : 'bold'
		                        }
		                    },

		                }
		            }
		        }
		    ]
		};
		
		var serverOptions = {
			    tooltip : {
			        trigger: 'item',
			        formatter: "{a} <br/>{b} : {c} ({d}%)"
			    },
			    legend: {
			        orient: 'horizontal',
			        top: 'top',
			        data: serverLegendDatas
			    },
			    series : [
			        {
			            name: '访问来源',
			            type: 'pie',
			            radius : '55%',
			            center: ['50%', '60%'],
			            data: serverSeriesDatas,
			            itemStyle : {
			                normal : {
			                    label : {
			                        show :true,
			                        position:'top',
			                        formatter: "{b}:\n{c}",
			                        textStyle : {
			                        color : 'blue',
			                        fontSize : 12,
			                        fontWeight : 'bold'
			                        }
			                    },

			                }
			            }
			        }
			    ]
			};
		
		
		webChart.setOption(webOptions);
		serverChar.setOption(serverOptions);
	});
});


var Index = function(){
	return {
	};
}();
