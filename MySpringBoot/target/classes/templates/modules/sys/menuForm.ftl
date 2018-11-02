<!DOCTYPE html>
<html lang="zh">

	<head>
		<#include "../../include/head.ftl"/>
	</head>

	<body>
		<div class="animated fadeIn">
			<div class="am-u-sm-12 am-u-md-11 form-top">
           		<form class="am-form am-form-horizontal" id="saveForm" action="/sys/menu/save">
           			<div class="am-form-group" hidden="hidden">
                 		<label  class="am-u-sm-3 am-form-label">主键ID</label>
                  		<div class="am-u-sm-9">
                  			<div>
	                       		<input autocomplete="off" type="text" name="id" id="id"  value="${menu.id !''}" >
	                       		<small></small>
                  			</div>
                    	</div>
                	</div>
                	<div class="am-form-group">
                 		<label  class="am-u-sm-3 am-form-label"><span class="required">*</span>上级菜单</label>
                  		<div class="am-u-sm-9">
                  			<div onclick="menuSelect('选择上级菜单', '/sys/menu/menuSelect?id=${menu.id !''}','260px', '430px')">
                  				<#if !menu.parent??>
                  					<input autocomplete="off" type="text" id="parentName" placeholder="点击选择"  value="功能菜单" required readonly="readonly" class="click">
                  					<input type="hidden" name="parentId" id="parentId" value="1">
                  				<#else>
                  					<input autocomplete="off" type="text" id="parentName" placeholder="点击选择"  value="${menu.parent.name}" required readonly="readonly" class="click">
                  					<input type="hidden" name="parentId" id="parentId" value="${menu.parent.id}">
                  				</#if>
	                       		<small>请选择上级菜单</small>
                  			</div>
                    	</div>
                	</div>
                	<div class="am-form-group">
                 		<label  class="am-u-sm-3 am-form-label"><span class="required">*</span>菜单名称</label>
                  		<div class="am-u-sm-9">
                  			<div>
	                       		<input autocomplete="off" type="text" name="name" id="name" placeholder="菜单名称" value="${menu.name !''}" maxlength="20" required>
	                       		<small>当前菜单名称</small>
                  			</div>
                    	</div>
                	</div>
                	<div class="am-form-group">
                 		<label  class="am-u-sm-3 am-form-label"><span class="required">*</span>菜单排序</label>
                  		<div class="am-u-sm-9">
                  			<div>
	                       		<input autocomplete="off" type="text" name="sort" id="sort" placeholder="排序" value="${menu.sort !''}" maxlength="3" required>
	                       		<small>升序排列</small>
                  			</div>
                    	</div>
                	</div>
                	<div class="am-form-group">
                 		<label  class="am-u-sm-3 am-form-label">菜单图标</label>
                  		<div class="am-u-sm-9">
                  			<div>
	                       		<input autocomplete="off" type="text" name="icon" id="icon" placeholder="图标" value="${menu.icon !''}" maxlength="20">
	                       		<small>菜单图标</small>
                  			</div>
                    	</div>
                	</div>
                	<div class="am-form-group">
                 		<label  class="am-u-sm-3 am-form-label">菜单链接</label>
                  		<div class="am-u-sm-9">
                  			<div>
	                       		<input autocomplete="off" type="text" name="href" id="href" placeholder="链接" value="${menu.href !''}" maxlength="50">
	                       		<small>菜单跳转链接</small>
                  			</div>
                    	</div>
                	</div>
                	<div class="am-form-group">
                 		<label  class="am-u-sm-3 am-form-label">权限标识</label>
                  		<div class="am-u-sm-9">
                  			<div>
	                       		<input autocomplete="off" type="text" name="permission" id="permission" placeholder="权限标识" value="${menu.permission !''}" maxlength="50">
	                       		<small>按钮的权限标识</small>
                  			</div>
                    	</div>
                	</div>
                	<div class="am-form-group">
                 		<label  class="am-u-sm-3 am-form-label"><span class="required">*</span>是否显示</label>
                  		<div class="am-u-sm-9">
                  			<div>
                  				<#if !menu.isShow??>
									<input type="radio" name="isShow" id="showNo" value="0" ><label for="showNo" class="selabel">隐藏</label>
									<input type="radio" name="isShow" id="showYes" value="1" ><label for="showYes" class="selabel">显示</label>
                  				<#else>
                  					<input type="radio" name="isShow" id="showNo" value="0"  ${(menu.isShow==0) ? string('checked','')}><label for="showNo" class="selabel">隐藏</label>
									<input type="radio" name="isShow" id="showYes" value="1" ${(menu.isShow==1) ? string('checked','')}><label for="showYes" class="selabel">显示</label>
                  				</#if>
                  			</div>
                    	</div>
                	</div>
       			</form>
        	</div>
		</div>
		<script type="text/javascript">
			//页面加载完成时调用  
	        $(function(){
	        	//要给对应的表单加入validate校验  
	            $("#saveForm").validate({  
	                rules:{  
	                	sort:{ 
	                		digits:true
	                    },
	                    isShow:{ 
	                    	required:true
	                    },
	                },  
	                messages:{  
	                	sort:{ 
	                		digits:"请输入正整数"
	                    },
	                    isShow:{ 
	                    	required:"必输字段"
	                    },
	                },
	                errorPlacement : function(error, element) {
						error.insertAfter(element.parent());
					}
	            }); 
	        });
			
	        function doSubmit () {
				if ($("#saveForm").valid()) {
					$("#saveForm").submit();
					return true;
				}
				return false;
			}
		</script>
	</body>
</html>