$(function() {
	jQuery.fn.extend({
		/* 去除所有空格 */
        "trimVal" : function(){
                return $(this).val() == null ? "" : $(this).val().replace(/(^\s*)|(\s*)|(\s*$)/g, "");
        },
        
	})
})
  