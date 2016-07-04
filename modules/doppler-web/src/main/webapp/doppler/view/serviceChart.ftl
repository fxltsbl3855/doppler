<!DOCTYPE html>
<head>
	<#include "doppler/common_doppler.ftl">
</head>
<body>
	<!-- Content Start -->
	<ol class="breadcrumb">
		<li><a href="javascript:void(0)">首页</a></li>
		<li><a href="javascript:void(0)">服务器监控</a></li>
		<li><a href="javascript:void(0)">应用</a></li>
		<li class="active">接口方法走势图</li>
	</ol>
	<div class="padd">
		<form class="form-inline">
			<div id="legend" class="">
		        <legend class="" id="legendName"></legend>
		    </div>
		     <input type="hidden" id="spanViewId" name="spanViewId">
		 	 <input type="hidden" id="date" name="date">
		</form>
		 <div class="panel-body">
           <div id="chart1" style="height:300px;"></div>
         </div>
	</div>
<script type="text/javascript" src="${base}/doppler/js/serviceChart.js"></script>   	
</body>
<script type="text/javascript">

</script>
