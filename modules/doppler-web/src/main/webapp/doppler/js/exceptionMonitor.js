$(document).ready(function() {
			ExceptionMonitor.initSelect();
		});
var ExceptionMonitor = function() {

	return {

		/**
		 * 获取服务名下拉框数据
		 */
		initSelect : function() {
			$('#appId').on('change', function() {
				$.post($.modulePath + "/exception/serviceItems.shtml", {
							'appId' : $('#appId').val()
						}, function(data) {
							$('#serviceId').empty();
							var html = '';
							for (var i in data) {
								var serviceId = data[i].serviceId;
								var name = data[i].name;
								html += '<option value="' + serviceId + '">'
										+ name + '</option>';
							}
							$('#serviceId').html(html);
						});
			});
		},

		/**
		 * 生成明细链接
		 */
		detailNameFormatter : function(value, row, index) {
			var id = row.id;
			return '<a style="text-decoration: none" href="javascript:ExceptionMonitor.switchToDetailHandler(\''
					+ id + '\'' + ')">明细</a>';
		},

		/**
		 * 异常明细查询
		 */
		switchToDetailHandler : function(id) {
			$('#mainDiv')[0].style.display = "none";
			$('#detailDiv')[0].style.display = "block";
			$.ajax({
				url : $.modulePath + "/exception/exceptionDetail.shtml",
				data : {
					'id' : id
				},
				dataType : 'json',
				async : false,
				success : function(data) {
					if (!data.isDefineException) {
						$('#errorDiv')[0].style.display = "block";
						$('#infoDiv')[0].style.display = "none";
						$('#errorMsg').empty()
								.append('<td colspan="6">后台正在执行任务，请3秒后重试</td>');
					} else if (!data.isSystemException) {
						$('#errorDiv')[0].style.display = "block";
						$('#infoDiv')[0].style.display = "none";
						$('#errorMsg').empty()
								.append('<td colspan="6">服务器异常</td>');
					} else {
						$('#errorDiv')[0].style.display = "none";
						$('#infoDiv')[0].style.display = "block";
						$('#spanId').val(data.spanId);
						$('#sourceAppName').val(data.sourceAppName);
						$('#targetAppName').val(data.targetAppName);
						$('#sourceServiceName').val(data.sourceServiceName);
						$('#targetServiceName').val(data.targetServiceName);
						$('#sourceAddrName').val(data.sourceAddrName);
						$('#targetAddrName').val(data.targetAddrName);
						$('#errorAddr').val(data.errorAddr);
						$('#errorTime').val(data.errorTime);
						$('#errorInfo').val(data.errorInfo);
					}
				}
			});
		},

		/**
		 * 返回
		 */
		backHandler : function() {
			$('#mainDiv')[0].style.display = "block";
			$('#detailDiv')[0].style.display = "none";
		},

		/**
		 * 查询条件参数
		 */
		queryParams : function() {
			var params = {
				'startDate' : $("#startDate").val() + ':00',
				'endDate' : $("#endDate").val() + ':00',
				'appId' : $("#appId").val() == null ? -1 : $("#appId").val(),
				'serviceId' : $("#serviceId").val() == null
						? -1
						: $("#serviceId").val(),
				'errorLevel' : $("#errorLevel").val() == null
						? -1
						: $("#errorLevel").val(),
				'addr' : $("#addr").val(),
				'key' : $("#key").val()
			};
			return params;
		},

		/**
		 * 列表查询
		 */
		queryHandler : function() {
			var url = $.modulePath + "/exception/exceptionList.shtml";
			$.ajax({
				url : url,
				data : ExceptionMonitor.queryParams(),
				dataType : 'json',
				success : function(data) {
					if (data.length == 1 && !data[0].isDefineException) {
						$("#exceptionTable tbody:first")
								.empty()
								.append('<tr align="center" class="tr.no-records-found"><td colspan="6">后台正在执行任务，请3秒后重试</td></tr>');
					} else if (data.length == 1 && !data[0].isSystemException) {
						$("#exceptionTable tbody:first")
								.empty()
								.append('<tr align="center" class="tr.no-records-found"><td colspan="6">服务器异常</td></tr>');
					} else {
						$("#exceptionTable").bootstrapTable('load', data);
					}
				}
			});
		}
	};
}();