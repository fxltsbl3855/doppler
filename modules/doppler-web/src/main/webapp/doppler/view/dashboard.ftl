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
		<li><a href="#">首页</a></li>
		<li class="active">Dashboard</li>
	</ol>
	<div class="padd-t">
		<div class="container-fluid">
			<div class="row">
				<div class="col-md-3">
					<div class="statbox bd-box">
						<span class="icon1"></span>
						<div class="title">WEB服务器总数</div>
						<div class="number" id="webNum"></div>
					</div>
				</div>
				<div class="col-md-3">
					<div class="statbox bd-box">
						<span class="icon2"></span>
						<div class="title">SERVER服务器总数</div>
						<div class="number" id="serverNum"></div>
					</div>
				</div>
				<div class="col-md-3">
					<div class="statbox bd-box">
						<span class="icon3"></span>
						<div class="title">WEB服务器请求总量</div>
						<div class="number" id="webReqNum"></div>
					</div>
				</div>
				<div class="col-md-3">
					<div class="statbox bd-box">
						<span class="icon4"></span>
						<div class="title">SERVER服务器请求总量</div>
						<div class="number" id="serverReqNum"></div>
					</div>
				</div>
			</div>
			<div class="blank15"></div>
			<div class="row">
				<div class="col-md-6">
					<div class="panel panel-default">
						<div class="panel-heading">
							WEB服务器 饼图
							<div class="panel-icon pull-right">
								<a href="#" class="btn-minimize"><i
									class="glyphicon glyphicon-chevron-up"></i></a> <a href="#"
									class="btn-close"><i class="glyphicon glyphicon-remove"></i></a>
							</div>
						</div>
						<div class="panel-body">
							<div id="webChart" style="height:300px;"></div>
				</div>
			</div>
		</div>
		<div class="col-md-6">
			<div class="panel panel-default">
				<div class="panel-heading">
					SERVER服务器 饼图
					<div class="panel-icon pull-right">
						<a href="#" class="btn-minimize"><i
							class="glyphicon glyphicon-chevron-up"></i></a> <a href="#"
							class="btn-close"><i class="glyphicon glyphicon-remove"></i></a>
					</div>
				</div>
				<div class="panel-body">
					<div id="serverChar" style="height:300px;"></div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<script type="text/javascript" src="${base}/doppler/js/index.js"></script>
</body>
