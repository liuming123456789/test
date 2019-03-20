    $(function(){  
        var $category =$('ul li:gt(5):not(:last)');//获取索引值大于5的品牌集合对象（去掉最后一个）  
        $category.hide();   //隐藏获得的JQuery对象  
        var $toggleBtn = $('div.showmore >a');   //获取全部品牌的按钮  
        $toggleBtn.click(function(){  
            if($category.is(":visible")){   //如果元素显示，则执行对应的代码  
                $category.hide();   //隐藏$category  
                $('.showmore a span')  
                    .css("background","url(img/down.gif) no-repeat 0 5px")  
                    .text("更多");    //改变背景图片和文字  
                $('ul li').removeClass("promoted"); //去掉高亮样式  
            }else{  
                $category.show();  
                $('.showmore a span')  
                    .css("background","url(img/up.gif) no-repeat 0 5px")  
                    .text("精简");            //改变背景图片和文本内容  
                $('ul li').filter(":contains('华为'),:contains('苹果'),:contains('魅族')")  
                    .addClass("promoted");  //添加高亮样式  
            }  
              
            return false;   //超链接不可跳转  
        })  
    })  