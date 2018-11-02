$(function() {
	// 弹出页面信息
	if ($("#message").val() != undefined && $("#message").val() != "") {
		var icon = $("#message").attr("icon");
		if (icon == undefined) {
			icon = 2;
		}
		top.layer.msg($("#message").val(), {
			icon : icon,
			offset : '40%',
			time : 2000
		});
	}

	// ==========================
	// 侧边导航下拉列表
	// ==========================

	$('.tpl-left-nav-link-list').on('click',
			function() {
				$(this).siblings('.tpl-left-nav-sub-menu').slideToggle(80)
						.end().find('.tpl-left-nav-more-ico').toggleClass(
								'tpl-left-nav-more-ico-rotate');
			})
	// ==========================
	// 头部导航隐藏菜单
	// ==========================

	$('.tpl-header-nav-hover-ico').on('click', function() {
		$('.tpl-left-nav').toggle();
		$('.tpl-content-wrapper').toggleClass('tpl-content-wrapper-hover');
	})

	// 菜单点击
	$(".YeJR_menuItem").on('click', function() {
		var url = $(this).attr('href');
		if (url != "" && url != "#") {
			$(".activeMenu").removeClass("activeMenu");
			$(this).children("span").addClass("activeMenu");
			$("#YeJR_iframe").attr('src', url);
			if (url != "druid") {
				//弹出加载层
		    	top.layer.load(1, {shade: [0.1,'#fff'], offset : '40%'});
			}
		}
		return false;
	});

	//全选监听
	$(".checkboxAll").click(function() {
		$('input[name="box"]').prop("checked",this.checked); 
		var $subBox = $("input[name='box']");
		$subBox.click(function(){
	    	 $(".checkboxAll").prop("checked",$subBox.length == $("input[name='box']:checked").length ? true : false);
		});
	 });
	
	//关闭加载层
	top.layer.closeAll('loading');
})


/**
 * layer查看弹出框
 * 
 * @param title
 * @param url
 * @param width
 * @param height
 * @returns
 */
function openDialogView(title, url, width, height) {
	top.layer.open({
		type : 2,
		area : [ width, height ],
		title : title,
		maxmin : true, // 开启最大化最小化按钮
		content : url,
		btn : [ '关闭' ],
		cancel : function(index) {
		}
	});
}

/**
 * layer保存弹出框
 * @param title
 * @param url
 * @param width
 * @param height
 * @returns
 */
function openDialogSave(title, url, width, height) {
	top.layer.open({
		type : 2,
		area : [ width, height ],
		title : title,
		maxmin : true, // 开启最大化最小化按钮
		content : url,
		btn : [ '确定', '关闭' ],
		yes : function(index, layero) {
			//获取打开的页面对象
			var body = top.layer.getChildFrame('body', index);
			// 得到iframe页的窗口对象
			var iframeWin = layero.find('iframe')[0]; 
			var form = body.find('#saveForm');
			//表单提交成功后，从服务器返回的url在当前iframe中展示
			form.attr("target", "YeJR_iframe");
			// 执行iframe页面的doSubmit()方法， 这里的doSubmit()方法需要返回true
			if(iframeWin.contentWindow.doSubmit()){
				//关闭对话框。
	        	top.layer.close(index);
	        	//弹出加载层
		    	top.layer.load(1, {shade: [0.1,'#fff'], offset : '40%'});
	         }
		},
		cancel : function(index) {
		}
	});

}

/**
 * 菜单选择
 * @param title
 * @param url
 * @param width
 * @param height
 * @returns
 */
function menuSelect(title, url, width, height) {
	top.layer.open({
		type : 2,
		area : [ width, height ],
		title : title,
		maxmin : true, // 开启最大化最小化按钮
		content : url,
		btn : [ '确定', '关闭' ],
		yes : function(index, layero) {
			// 得到iframe页的窗口对象
			var iframeWin = layero.find('iframe')[0]; 
			// 执行iframe页面的方法
			var result = iframeWin.contentWindow.menuChoose();
			if (result.length > 0) {
				$("#parentId").val(result[0]);
				$("#parentName").val(result[1]);
			}
			//关闭对话框。
        	top.layer.close(index);
		},
		cancel : function(index) {
		}
	});

}

/**
 * 单条数据删除
 * 
 * @param msg
 * @param href
 * @returns
 */
function deleteItem(msg, href) {
	top.layer.confirm(msg, {
		icon : 3,
		title : '系统提示',
		offset : '40%'
	}, function(index) {
		if (typeof href == 'function') {
			href();
		} else {
			location = href;
		}
		top.layer.close(index);
		//弹出加载层
    	top.layer.load(1, {shade: [0.1,'#fff'], offset : '40%'});
	});
}

/**
 * 多条数据删除
 * @param msg
 * @param href
 * @returns
 */
function deleteMultIterm(msg, href) {
	var ids = "";
	$('input[name="box"]').each(function(){
		if($(this).is(':checked')){
			ids += $(this).attr("id")+",";
		}
	});
	if(ids.substr(ids.length-1)== ','){
	    ids = ids.substr(0,ids.length-1);
	}
	if(ids != ""){
		top.layer.confirm(msg, {
			icon : 3,
			title : '系统提示',
			offset : '40%'
		}, function(index) {
			if (typeof href == 'function') {
				href();
			} else {
				href = href + "?ids=" + ids;
				location = href;
			}
			top.layer.close(index);
			//弹出加载层
	    	top.layer.load(1, {shade: [0.1,'#fff'], offset : '40%'});
		});
	} else {
		top.layer.msg('请至少选择一条数据！', {time : 1500,icon : 0,offset : '40%'});
	}
}

/**
 * 导出数据
 * @param msg
 * @param href
 * @returns
 */
function exportData(msg, href) {
	top.layer.confirm(msg, {
		icon : 3,
		title : '系统提示',
		offset : '40%'
	}, function(index) {
		//导出之前备份
    	var url =  $("#searchForm").attr("action");
    	
    	//导出excel
        $("#searchForm").attr("action",href);
		$("#searchForm").submit();

		//导出excel之后还原
		$("#searchForm").attr("action",url);
		
		top.layer.close(index);
		
		//弹出加载层
    	top.layer.load(1, {shade: [0.1,'#fff'], offset : '40%'});
    	
    	// 定时器判断导出进度是否完成
        var timer = setInterval(function(){
            $.ajax({
                url: '/exportExcelFinished?id='+Math.random(),
                success: function(result){
                	if(result){
                		top.layer.closeAll('loading');
                    	clearInterval(timer);
                	}
                },
                error:function(e){
                    console.log(e.responseText);
            	}
            }); 
        }, 1000);
	});
}

/**
 * 分页
 * @param pageNo
 * @param pageSize
 * @param param
 * @returns
 */
function page(pageNo, pageSize, param) {
	$("#pageNo").val(pageNo);
	$("#pageSize").val(pageSize);
	$("#searchForm").submit();
	//弹出加载层
	top.layer.load(1, {shade: [0.1,'#fff'], offset : '40%'});
}

