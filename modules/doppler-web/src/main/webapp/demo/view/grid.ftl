<!DOCTYPE html>
<html>
<head>
<#include "doppler/common_doppler.ftl">
</head>
<body>
<!-- Content Start -->
  <div class="padd">
  <div class="panel panel-default">
  <div class="panel-heading">普通表格
    <div class="panel-icon">
      <a href="#" class="btn-minimize"><i class="glyphicon glyphicon-chevron-up"></i></a>
      <a href="#" class="btn-close"><i class="glyphicon glyphicon-remove"></i></a>
    </div>
  </div>
  <div class="panel-body">
    <div id="toolbar">
        <button id="add_btn" class="btn btn-primary">添加</button> <button id="remove_btn" class="btn btn-danger">删除</button>
    </div>

 <table id="table" data-toggle="table" 
                      data-url="json/data2.json" 
                      data-pagination="true" 
                      data-show-columns="true" 
                      data-show-export="true" 
                      data-show-refresh="true" 
                      data-click-to-select="true">
        <thead>
        <tr>
            <th data-field="services_name">应用</th>
            <th data-field="call_time" data-halign="center" data-align="center" data-sortable="true">调用量</th>
            <th data-field="call_time_length">超时</th>
        </tr>
        </thead>
    </table>
    
    
 <table data-toggle="table"
		       data-url="${base}/doppler/app/appDetailList.shtml">
	<thead>
		<tr>
			<th data-field="appName" data-formatter="App.nameFormatter">应用</th>
			<th data-field="reqNum" data-halign="center" data-align="center" data-sortable="true">调用量</th>
			<th data-field="timeoutNum">超时</th>
			<th data-field="timeoutPercent" data-halign="center" data-align="center">超时百分比</th>
			<th data-field="errorNum">异常</th>
			<th data-field="errorPercent" data-halign="center" data-align="center">异常百分比</th>
		</tr>
	</thead>
</table> 
  </div>
</div>
  </div>
<script>
   
</script>
</body>
</html>