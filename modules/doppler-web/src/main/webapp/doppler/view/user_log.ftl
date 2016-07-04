<!DOCTYPE html>
<head>
</head>
<body>
<!-- Content Start -->
<ol class="breadcrumb">
  <li><a href="#">首页</a></li>
  <li><a href="#">日志管理</a></li>
  <li class="active">用户操作日志</li>
</ol> 
  <div class="padd">
    <div id="toolbar">
      <span class="tit">用户操作日志</span>
    </div>
		<table id="table" data-toggle=sino-grid 
		                  data-url="json/data3.json"
			              data-pagination="true" 
			              data-search="true" 
			              data-show-columns="true"
			              data-show-export="true" 
			              data-show-refresh="true"
			              data-click-to-select="true" 
			              data-toolbar="#toolbar">
			<thead>
				<tr>
					<th data-field="id" data-halign="center" data-align="center">编号</th>
					<th data-field="name">操作用户</th>
					<th data-field="address">操作地址</th>
					<th data-field="operation">操作描述</th>
					<th data-field="ip">操作IP</th>
					<th data-field="time" data-halign="center" data-align="center">操作时间</th>
				</tr>
			</thead>
		</table>
	</div>
</body>
</html>