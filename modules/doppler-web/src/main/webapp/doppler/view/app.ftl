<!DOCTYPE html>
<head>
	<#include "doppler/common_doppler.ftl">
</head>
<body>
<!-- Header Start -->
<#include "doppler/top.ftl">
<!-- Header End -->
<!-- Page-sidebar Start -->
<#include "doppler/menu.ftl">
<!-- Page-sidebar End -->
<div class="page-content">
	<!-- Content Start -->
	<ol class="breadcrumb">
		<li><a href="javascript:void(0)">首页</a></li>
		<li class="active">服务器监控</li>
	</ol>
	<div class="padd">
		<form class="form-inline">
			<div class="form-group">
				<label for="date">日期：</label>
				 <div class="input-group date form_datetime" data-date-format="yyyy-MM-dd" data-link-field="dtp_input1" data-initialDate="new Date()">
		            <input class="form-control" size="16" type="text" readonly id="date">
		            <span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
		        </div>
			</div>
			<button class="btn btn-primary" style="width:100px;" type="button" onclick="App.query()">查询</button>
		</form>
		
        <table id="appTable" data-toggle="table" 
        	   data-show-columns="true" 
               data-show-export="true" 
		       data-unique-id="appId">
			<thead>
				<tr>
					<th data-field="appId" data-visible="false">id</th>
					<th data-field="appName" data-formatter="App.nameFormatter">应用</th>
					<th data-field="reqNum" data-halign="center" data-align="center" data-sortable="true">调用量</th>
					<th data-field="timeoutNum">超时</th>
					<th data-field="timeoutPercent" data-halign="center" data-align="center" data-formatter="App.toPercent">超时百分比</th>
					<th data-field="errorNum">异常</th>
					<th data-field="errorPercent" data-halign="center" data-align="center" data-formatter="App.toPercent">异常百分比</th>
				</tr>
			</thead>
		</table>
	</div>
</div>
<script type="text/javascript" src="${base}/doppler/js/app.js"></script>   	
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
    pickerPosition: "bottom-left"
  });

</script>
