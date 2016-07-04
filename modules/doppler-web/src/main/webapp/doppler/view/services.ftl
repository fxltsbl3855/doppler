<!DOCTYPE html>
<head>
	<#include "doppler/common_doppler.ftl">
</head>
<body>
<!-- Content Start -->
<ol class="breadcrumb">
  <li><a href="javascript:void(0)">首页</a></li>
  <li><a href="javascript:void(0)">服务器监控</a></li>
  <li class="active">应用</li>
</ol>
<div class="padd">
		<form class="form-inline">
			<div id="legend" class="">
		        <legend class="" id="appName"></legend>
		    </div>
			<input type="hidden" id="appId" name="appId">
			<div class="form-group">
				<label for="port">接口：</label>
				<div class="input-group">
					<select class="sino-select" id="serviceSelect" data-url="${base}/doppler/service/serviceList.shtml" data-url-params="appId">
					
					</select>
				</div>
			</div>
			<div class="form-group">
				<label for="date">日期：</label>
				 <div class="input-group date form_datetime" data-date-format="yyyy-MM-dd" data-link-field="dtp_input1">
		            <input class="form-control" size="16" type="text" readonly id="date">
		            <span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
		        </div>
			</div>
			<button class="btn btn-primary" style="width:100px;" type="button" onclick="Services.query()">查询</button>
		</form>
		<table id="servicesTable" data-toggle="sino-grid" 
                      data-show-columns="true" 
                      data-show-export="true" 
                      data-click-to-select="true"
		       		  data-unique-id="spanViewId">
	        <thead>
	        <tr>
	            <th data-field="spanViewId" data-visible="false">SPAN</th>
	            <th data-field="spanView" data-formatter="Services.nameFormatter">SPAN</th>
	            <th data-field="reqNum">调用量</th>
	            <th data-field="qps">QPS</th>
	            <th data-field="maxTime">最大耗时</th>
	            <th data-field="timeoutNum">超时</th>
	            <th data-field="timeoutPercent" data-formatter="Services.toPercent">超时百分比</th>
	            <th data-field="errorNum">异常</th>
	            <th data-field="errorPercent" data-formatter="Services.toPercent">异常百分比</th>
	        </tr>
	        </thead>
	    </table>
</div>
<script type="text/javascript" src="${base}/doppler/js/services.js"></script>   	
</body>
<script type="text/javascript">

$('.form_datetime input').val(new Date().Format("yyyy-MM-dd") );
$('.form_datetime').datetimepicker({
    language:  'zh-CN',
    format: "yyyy-mm-dd",
    weekStart: 7,
    todayBtn:  1,
    autoclose: 1,
    todayHighlight: 1,
    startView: 2,
    forceParse: 0,
    minView: "month",//此项删除可选择时间
    pickerPosition: "bottom-left",
  });
</script>
