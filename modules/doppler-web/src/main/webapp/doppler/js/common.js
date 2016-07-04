/*
 * @authors Coolia.Gong (Coolia.Gong@sinoservices.com)
 * @date    2016-01-05 14:06:36
 */

function getCurDate()
{
	var d = new Date();
	var week;
	switch (d.getDay()){
		case 1: week="星期一"; break;
		case 2: week="星期二"; break;
		case 3: week="星期三"; break;
		case 4: week="星期四"; break;
		case 5: week="星期五"; break;
		case 6: week="星期六"; break;
		default: week="星期天";
	}
	var years = d.getFullYear();
	var month = add_zero(d.getMonth()+1);
	var days = add_zero(d.getDate());
	var hours = add_zero(d.getHours());
	var minutes = add_zero(d.getMinutes());
	var seconds=add_zero(d.getSeconds());
	var ndate = years+"-"+month+"-"+days+"  "+hours+":"+minutes+":"+seconds+"  "+week;
	document.getElementById("timer").innerHTML= ndate;
}
 
function add_zero(temp)
{
	if(temp<10){
		return "0"+temp;
	}else{
		return temp;
	}
}
$(function(){
	// 获取时间
	//setInterval("getCurDate()",100);
	// 异常信息滚动
	jQuery(".abnormal").slide({mainCell:".bd ul",autoPage:true,effect:"topLoop",autoPlay:true,vis:1,interTime:3000,delayTime:800});
	// 菜单折叠
  $('[data-toggle="popover"]').popover({});
  // 导航菜单折叠
  $('.dropmenu').click(function(e){
    e.preventDefault();
    $(this).parent().find('ul').slideToggle();
  });

// 面板关闭按钮
    $('.btn-close').click(function(e){
    e.preventDefault();
    $(this).parent().parent().parent().fadeOut();
  });
// 面板最小化
  $('.btn-minimize').click(function(e){
    e.preventDefault();
    var $target = $(this).parent().parent().next('.panel-body');
    if($target.is(':visible')) $('i',$(this)).removeClass('glyphicon-chevron-up').addClass('glyphicon-chevron-down');
    else             $('i',$(this)).removeClass('glyphicon-chevron-down').addClass('glyphicon-chevron-up');
    $target.slideToggle();
  });
})
