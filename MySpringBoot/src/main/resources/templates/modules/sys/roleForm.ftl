<!DOCTYPE html>
<html lang="zh">

	<head>
		<#include "../../include/head.ftl"/>
	</head>

	<body>
		<div class="animated fadeIn">
			<div class="am-u-sm-12 am-u-md-11 form-top">
           		<form class="am-form am-form-horizontal" id="saveForm" action="/sys/role/save">
           			<div class="am-form-group" hidden="hidden">
                 		<label  class="am-u-sm-3 am-form-label">主键ID</label>
                  		<div class="am-u-sm-9">
                  			<div>
	                       		<input autocomplete="off" type="text" name="id" id="id"  value="${role.id !''}" >
	                       		<small></small>
                  			</div>
                    	</div>
                	</div>
                	<div class="am-form-group">
                 		<label  class="am-u-sm-3 am-form-label"><span class="required">*</span>角色名</label>
                  		<div class="am-u-sm-9">
                  			<div>
	                       		<input autocomplete="off" type="text" name="roleName" id="roleName" placeholder="用户名"  value="${role.roleName !''}" required>
	                       		<input type="hidden" name="oldRoleName" id="oldRoleName" value="${role.roleName !''}">
	                       		<small>角色名建议使用英文</small>
                  			</div>
                    	</div>
                	</div>
                	<div class="am-form-group">
                 		<label  class="am-u-sm-3 am-form-label"><span class="required">*</span>管理员</label>
                  		<div class="am-u-sm-9">
                  			<div>
                  				<#if !role.isAdmin??>
									<input type="radio" name="isAdmin" id="adminNo" value="0" ><label for="adminNo" class="selabel">非管理员</label>
									<input type="radio" name="isAdmin" id="adminYes" value="1" ><label for="adminYes" class="selabel">管理员</label>
                  				<#else>
                  					<input type="radio" name="isAdmin" id="adminNo" value="0"  ${(role.isAdmin==0) ? string('checked','')}><label for="adminNo" class="selabel">非管理员</label>
									<input type="radio" name="isAdmin" id="adminYes" value="1" ${(role.isAdmin==1) ? string('checked','')}><label for="adminYes" class="selabel">管理员</label>
                  				</#if>
                  			</div>
                    	</div>
                	</div>
                	<div class="am-form-group">
                 		<label  class="am-u-sm-3 am-form-label"><span class="required">*</span>状态</label>
                  		<div class="am-u-sm-9">
                  			<div>
                  				<#if !role.status??>
	                  				<input type="radio" name="status" id="statusNo" value="0" ><label for="statusNo" class="selabel">禁用</label>
									<input type="radio" name="status" id="statusYes" value="1" ><label for="statusYes" class="selabel">启用</label>
                  				<#else>
	                  				<input type="radio" name="status" id="statusNo" value="0"  ${(role.status==0) ? string('checked','')}><label for="statusNo" class="selabel">禁用</label>
									<input type="radio" name="status" id="statusYes" value="1" ${(role.status==1) ? string('checked','')}><label for="statusYes" class="selabel">启用</label>
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
	                	roleName:{ 
	                        rangelength:[3,20],
	                        remote: {
								type: "POST",
								url: "/sys/role/verifyRoleName",
								cache:false,
					            async:false,          
								data: {
									roleName : function(){return $("#roleName").val();},
									oldRoleName: function(){return $("#oldRoleName").val();},
								}
							}
	                    },
	                    isAdmin:{
	                    	required: true
	                    },
	                    status:{
	                    	required: true
	                    }
	                },  
	                messages:{  
	                	roleName:{ 
	                        rangelength:"角色名长度{0}到{1}位",
	                        remote: "角色名已存在"
	                    },
	                    isAdmin:{
	                    	required: "必输字段"
	                    },
	                    status:{
	                    	required: "必输字段"
	                    }
	                   
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