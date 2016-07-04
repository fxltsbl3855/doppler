<!DOCTYPE html>
<head><#include "doppler/common_doppler.ftl">
</head>
<body>
<!-- Header Start -->
<#include "doppler/top.ftl">
<!-- Header End -->
<!-- Page-sidebar Start -->
<#include "doppler/menu.ftl">
<!-- Page-sidebar End -->
<div class="page-content">
	<!-- 异常列表页面	start-->
	<div id="mainDiv">
		<!-- Content Start -->
		<ol class="breadcrumb">
			<li><a href="javascript:void(0)">首页</a></li>
			<li class="active">异常监控</li>
		</ol>
		<div class="padd">
			<form class="form-horizontal">
				<div class="row">
					<div class="col-xs-6 col-md-4">
						<label class="col-sm-4">开始时间：</label>
						<div class="input-group date form_datetime" data-date-format="yyyy-MM-dd hh:mm" data-link-field="dtp_start_time" data-initialDate="new Date()">
							<input class="form-control" size="16" type="text" readonly id="startDate"><span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
						</div>
					</div>
					<div class="col-xs-6 col-md-4">
						<label class="col-sm-4">结束时间：</label>
						<div class="input-group date form_datetime" data-date-format="yyyy-MM-dd hh:mm" data-link-field="dtp_end_time" data-initialDate="new Date()">
							<input class="form-control" size="16" type="text" readonly id="endDate"> <span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
						</div>
					</div>
					<div class="col-xs-6 col-md-4">
						<label class="col-sm-4">&nbsp;&nbsp;&nbsp;应用名：</label>
						<select class="sino-select" id="appId" style="height: 30px;width: 200px" data-url="${base}/doppler/exception/displayItems.shtml?itemId=1" data-url-params="appId" >
	 					</select>
					</div>
				</div>
				<div class="row" style="margin-top: 10px">
					<div class="col-xs-12 col-md-8">
						<label class="col-sm-2">&nbsp;&nbsp;&nbsp;服务名：</label>
						<select class="sino-select" id="serviceId" style="margin-left: -5px;width: 500px;height: 30px" data-url="${base}/doppler/exception/serviceItems.shtml" data-url-params="appId">
							
	 					</select>
					</div>
					<div class="col-xs-6 col-md-4">
						<label class="col-sm-4">错误级别：</label> 
						<select class="sino-select" id="errorLevel" style="height: 30px;width: 200px" data-url="${base}/doppler/exception/displayItems.shtml?itemId=2" data-url-params="errorLevel">
							
	 					</select>
					</div>
					
				</div>
				<div class="row" style="margin-top: 15px">
					<div class="col-xs-6 col-md-4">
						<label class="col-sm-4" style="padding-left: 42px;">地址：</label>
						<input id="addr" name="addr" style="width: 200px;" type="text" >
					</div>
					<div class="col-xs-6 col-md-4">
						<label class="col-sm-4" style="padding-left: 28px;">关键字：</label>
						<input id="key" name="key" type="text" >
					</div>
					<button class="btn btn-primary" style="width: 100px;" type="button"
						onclick="ExceptionMonitor.queryHandler()">查询</button>
				</div>
			</form>
	
			<table id="exceptionTable" data-toggle="table"
				data-show-columns="true" data-show-export="true"
				data-unique-id="spanId">
				<thead>
					<tr>
						<th data-field="spanId" data-visible="false">spanId</th>
						<th data-field="appName">应用名</th>
						<th data-field="serviceName">服务名</th>
						<th data-field="addr">主机地址</th>
						<th data-field="errorLevelDisplay">日志级别</th>
						<th data-field="reqTime">请求时间</th>
	<!-- 					<th data-field="logInfo">日志内容</th> -->
						<th data-formatter="ExceptionMonitor.detailNameFormatter">明细</th>
					</tr>
				</thead>
			</table>
		</div>
	</div>
	<!-- 异常列表页面	 end -->
	<!-- 异常明细页面	 start -->
	<div id="detailDiv" style="display: none">
		<ol class="breadcrumb">
		  <li><a href="javascript:void(0)">首页</a></li>
		  <li><a href="javascript:void(0)">异常监控</a></li>
		  <li class="active">详情</li>
		</ol>
		<div id="infoDiv" class="padd" style="margin-left:20px;">
			<button class="btn btn-primary" style="width:100px;margin-bottom: 20px;" type="button" onclick="ExceptionMonitor.backHandler();">返回</button>
			<form id="detailForm" class="form-horizontal" style="width:95%;">
				<div class="form-group">
					<label class="col-sm-12">异常发生时的请求（span）信息（SpanId：<input id="spanId" name="spanId" style="border: 0px;background-color: white" disabled="disabled">）</label>
				</div>
				<div class="form-group">
					<label class="col-sm-2">源项目：</label>
					<input class="col-sm-4" id="sourceAppName" name="sourceAppName" disabled="disabled" type="text" >
					<label class="col-sm-2">目标項目：</label>
					<input class="col-sm-4" id="targetAppName" name="targetAppName" disabled="disabled" type="text" >
				</div>
				<div class="form-group">
					<label class="col-sm-2">源服务：</label>
					<input class="col-sm-4" id="sourceServiceName" name="sourceServiceName" disabled="disabled" type="text" >
					<label class="col-sm-2">目标服务：</label>
					<input class="col-sm-4" id="targetServiceName" name="targetServiceName" disabled="disabled" type="text" >
				</div>
				<div class="form-group">
					<label class="col-sm-2">源地址：</label>
					<input class="col-sm-4" id="sourceAddrName" name="sourceAddrName" disabled="disabled" type="text" >
					<label class="col-sm-2">目标地址：</label>
					<input class="col-sm-4" id="targetAddrName" name="targetAddrName" disabled="disabled" type="text" >
				</div>
				<div class="form-group">
					<label class="col-sm-2">异常信息：</label>
					<div  class="col-sm-4">
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2">异常发生地址：</label>
					<input class="col-sm-10" id="errorAddr" name="errorAddr" disabled="disabled" type="text" >
				</div>
				<div class="form-group">
					<label class="col-sm-2">异常发生时间：</label>
					<input class="col-sm-10" id="errorTime" name="errorTime" disabled="disabled" type="text" >
				</div>
				<div class="form-group">
					<label class="col-sm-2">异常内容：</label>
					<textarea class="enable-control col-sm-12" id="errorInfo" name="errorInfo" style="overflow-x:hidden;overflow-y:hidden;height:1300px;" disabled="disabled">
					</textarea>
				</div>
			</form>
		</div>
		<div id="errorDiv" class="padd" style="margin-left:20px;display: none">
			<button class="btn btn-primary" style="width:100px;margin-bottom: 20px;" type="button" onclick="ExceptionMonitor.backHandler();">返回</button>
			<form id="errorMsg" class="form-horizontal" style="width:95%;"></form>
		</div>
	</div>
<!-- 异常明细页面 	end -->
</div>
<script type="text/javascript" src="${base}/doppler/js/exceptionMonitor.js"></script>
</body>
<script type="text/javascript">
	$('#startDate').val(new Date(new Date() - 10*60*1000).Format("yyyy-MM-dd hh:mm"));
	$('#endDate').val(new Date(new Date().getTime()).Format("yyyy-MM-dd hh:mm"));
	$('.form_datetime').datetimepicker({
		language : 'zh-CN',
		format : "yyyy-mm-dd hh:ii",
		weekStart : 7,
		todayBtn : 1,
		autoclose : 1,
		todayHighlight : 1,
		startView : 0,
		forceParse : 0,
		//minView: "month",//此项删除可选择时间
		pickerPosition : "bottom-left"
	});
</script>
