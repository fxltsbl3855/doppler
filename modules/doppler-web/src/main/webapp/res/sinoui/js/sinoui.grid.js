$(function () {
    $('[data-toggle="sino-grid"]').sinoGrid();
});
    
(function($){    
    /**
	 * Grid列表组件
	 * 
	 * @param options 列表组件的配置信息
	 */
	$.fn.extend({
		sinoGrid : function(options){
			if (typeof options == 'string'){
				return $(this).datagrid.apply(this, arguments);
			} 
			options = options || {};
			return this.each(function(){
				var $this = $(this),
                data = $this.data('bootstrap.table'),
                options = $.extend({}, $this.data(),
                    typeof option === 'object' && option);
				$this.bootstrapTable(options);
			});
		},
	});
	
	// 覆盖默认值
	$.fn.sinoGrid.defaults = $.extend({},$.fn.bootstrapTable.defaults, {
		striped : true,
		pagination: false,
		pageNumber: 1,
		sidePagination : "server",
		pageList:"[5, 10, 20, 50, 100, 200]"
	});
})(jQuery);	
	