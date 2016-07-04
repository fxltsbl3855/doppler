/**菜单*/
var Menu = function() {
	return {
		forward : function(url) {
			$.post(url, {}, function(data) {
				$(".page-content").empty();
				$(".page-content").html(data);
			});
		}
	};
}();
