/**
 * select下拉组件
 * 
 * @param options 列表组件的配置信息
 */
$(function () {
    $('.sino-select').sinoSelect();
});

(function($){
	
	
	/**
	 * 初始化
	 */
	function init(target){
		var url = target.attr("data-url");
		var dataName = target.attr("data-url-params");
		var dataValue = $("#" + target.attr("data-url-params")).val();
		var data = new Object();
		data[dataName] = dataValue;
			$.ajax({
				url:url,
				data: data,
				dataType:'json',
				async: false,
				success:function(data){
					var html = '';
					for ( var i in data) {
						var serviceId = data[i].serviceId;
						var name = data[i].name;
						html += '<option value="' + serviceId + '">' + name + '</option>';
					}
					target.html(html);
				}
			});
	}
	
	$.fn.sinoSelect = function(options, param){
		if (typeof options == 'string'){
			
		}
		options = options || {};
		return this.each(function(){
			var $this = $(this);
			init($this);
//			$this.selectpicker({
//				style : 'btn-default',
//				size : 4,
//				width : 218,
//			});
			$('.selectpicker').selectpicker({
			  style: 'btn-info',
			  size: 4
			});
		});
	};
	
	$.fn.sinoSelect.defaults = {
		
	};
})(jQuery);

