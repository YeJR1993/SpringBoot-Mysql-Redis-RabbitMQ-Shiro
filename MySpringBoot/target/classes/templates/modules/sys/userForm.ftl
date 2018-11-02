<!DOCTYPE html>
<html lang="zh">

	<head>
		<#include "../../include/head.ftl"/>
	</head>

	<body>
		<div class="animated fadeIn">
			<div class="am-u-sm-12 am-u-md-11 form-top">
           		<form class="am-form am-form-horizontal" id="saveForm" action="/sys/user/save">
           			<div class="am-form-group" hidden="hidden">
                 		<label  class="am-u-sm-3 am-form-label">主键ID</label>
                  		<div class="am-u-sm-9">
                  			<div>
	                       		<input autocomplete="off" type="text" name="id" id="id" value="${user.id !''}">
	                       		<small></small>
                  			</div>
                    	</div>
                	</div>
                	<div class="am-form-group">
                 		<label  class="am-u-sm-3 am-form-label"><span class="required">*</span>用户名</label>
                  		<div class="am-u-sm-9">
                  			<div>
	                       		<input autocomplete="off" type="text" name="username" id="username" placeholder="用户名"  value="${user.username !''}">
	                       		<input type="hidden" name="oldUsername" id="oldUsername" value="${user.username !''}">
	                       		<small>用户名只能包含英文数字和下划线，且必须以英文字母开头</small>
                  			</div>
                    	</div>
                	</div>
                	<div class="am-form-group">
                 		<label  class="am-u-sm-3 am-form-label"><#if !user.id??><span class="required">*</span></#if>密码</label>
                  		<div class="am-u-sm-9">
                  			<div>
	                       		<input autocomplete="off" type="password" name="password" id="password" placeholder="密码" ${user.id !'required'}>
	                       		<small>密码只能包含字母，数字，下划线，减号</small>
                  			</div>
                    	</div>
                	</div>
                	<div class="am-form-group">
                 		<label  class="am-u-sm-3 am-form-label"><#if !user.id??><span class="required">*</span></#if>确认密码</label>
                  		<div class="am-u-sm-9">
                  			<div>
	                       		<input autocomplete="off" type="password" id="confirmPassword" name="confirmPassword" placeholder="确认密码">
	                       		<small>请输入相同的确认密码</small>
                  			</div>
                    	</div>
                	</div>
                	<div class="am-form-group">
                 		<label  class="am-u-sm-3 am-form-label"><span class="required">*</span>手机号</label>
                  		<div class="am-u-sm-9">
                  			<div>
	                       		<input autocomplete="off" type="text" id="phone" name="phone" placeholder="手机号" value="${user.phone !''}" required>
                  				<small>请填写真实的手机号码</small>
                  			</div>
                    	</div>
                	</div>
                	<div class="am-form-group">
                 		<label  class="am-u-sm-3 am-form-label">头像</label>
                  		<div class="am-u-sm-9">
                  			<div class="am-input-group am-input-group-sm">
	                       		<input autocomplete="off" class="uploadFile" uploadType="image" type="text" id="headImg" name="headImg" placeholder="用户头像" value="${user.headImg !''}" readonly="readonly">
                  				<span class="am-input-group-btn">
							    	<button class="am-btn input-btn am-btn-default iconfont icon-shangchuan" type="button" onclick="openUploadPage('image', 'headImg', 1)"></button>
							    </span>
                  			</div>
                  			<small>上传用户头像</small>
                    	</div>
                	</div>
                	<div class="am-form-group">
                 		<label  class="am-u-sm-3 am-form-label">角色</label>
                  		<div class="am-u-sm-9">
                  			<div>
	                       		<#list allRoles as role>
	                       			<span class="groupSelect"><input type="checkbox" id="${role.id}" name="roleIds" value="${role.id}" ${role.checkbox !''} class="tpl-table-fz-check"> <label for="${role.id}">${role.roleName}</label></span>
	                       		</#list>
                  			</div>
                    	</div>
                	</div>
                	<div class="am-form-group">
                 		<label  class="am-u-sm-3 am-form-label"><span class="required">*</span>期限</label>
                  		<div class="am-u-sm-9">
                  			<div class="am-input-group am-input-group-sm">
                  				<#if user.startTime??>
                  					<input autocomplete="off" type="text" id="startTime" name="startTime" placeholder="开始时间" value="${user.startTime?string('yyyy-MM-dd HH:mm:ss')}" required>
                  				<#else>
	                       			<input autocomplete="off" type="text" id="startTime" name="startTime" placeholder="开始时间" value="" required>
                  				</#if>
                  				<span class="am-input-group-btn">
                  					<button class="am-btn input-btn am-btn-default" type="button">至</button>
                  				</span>
                  				<#if user.endTime??>
                  					<input autocomplete="off" type="text" id="endTime" name="endTime" placeholder="结束时间" value="${user.endTime?string('yyyy-MM-dd HH:mm:ss')}" required>
                  				<#else>
                  					<input autocomplete="off" type="text" id="endTime" name="endTime" placeholder="结束时间" value="" required>
                  				</#if>
                  			</div>
                  			<small>用户使用期限</small>
                    	</div>
                	</div>
       			</form>
        	</div>
		</div>
		<script type="text/javascript">
			//页面加载完成时调用  
	        $(function(){
	        	//时间选择器
				laydate.render({
				    elem: '#startTime',
				    type: 'datetime',
				    change: function(value, date, endDate){
				        console.log(value); //得到日期生成的值，如：2017-08-18
				        console.log(date); //得到日期时间对象：{year: 2017, month: 8, date: 18, hours: 0, minutes: 0, seconds: 0}
				        console.log(endDate); //得结束的日期时间对象，开启范围选择（range: true）才会返回。对象成员同上。
				    },
				    done: function(value, date, endDate){
				    	console.log(value); //得到日期生成的值，如：2017-08-18
				    	console.log(date); //得到日期时间对象：{year: 2017, month: 8, date: 18, hours: 0, minutes: 0, seconds: 0}
				    	console.log(endDate); //得结束的日期时间对象，开启范围选择（range: true）才会返回。对象成员同上。
				    }
				});
				//时间选择器
				laydate.render({
				    elem: '#endTime',
				    type: 'datetime',
				    change: function(value, date, endDate){
				        console.log(value); //得到日期生成的值，如：2017-08-18
				        console.log(date); //得到日期时间对象：{year: 2017, month: 8, date: 18, hours: 0, minutes: 0, seconds: 0}
				        console.log(endDate); //得结束的日期时间对象，开启范围选择（range: true）才会返回。对象成员同上。
				     },
				     done: function(value, date, endDate){
					 	console.log(value); //得到日期生成的值，如：2017-08-18
					 	console.log(date); //得到日期时间对象：{year: 2017, month: 8, date: 18, hours: 0, minutes: 0, seconds: 0}
					 	console.log(endDate); //得结束的日期时间对象，开启范围选择（range: true）才会返回。对象成员同上。
					 }
				});
	        	
	        	// 账号的校验规则
	        	jQuery.validator.addMethod("usernameRule", function(value, element) {
					var reg = /^[a-zA-Z][a-zA-Z0-9_]*$/;
					return(reg.test(value));
				}, "");
	        	// 密码的校验规则
	        	jQuery.validator.addMethod("passwordRule", function(value, element) {
					var reg = /^[a-zA-Z0-9_-]*$/;
					return(reg.test(value));
				}, "");
	        	// 密码的校验规则
	        	jQuery.validator.addMethod("phoneRule", function(value, element) {
					var reg = /^[1][3,4,5,7,8][0-9]{9}$/;
					return(reg.test(value));
				}, "");
	            //要给对应的表单加入validate校验  
	            $("#saveForm").validate({  
	                rules:{  
	                    username:{  
	                        rangelength:[3,16],
	                        usernameRule: true,
	                        remote: {
								type: "POST",
								url: "/sys/user/verifyUsername",
								cache:false,
					            async:false,          
								data: {
									username : function(){return $("#username").val();},
									oldUsername: function(){return $("#oldUsername").val();},
								}
							}
	                    },
	                    password:{
	                    	rangelength:[3,16],
	                    	passwordRule: true
	                    },
	                    confirmPassword: {
	                    	equalTo:"#password"
	                    },
	                    phone:{
	                    	phoneRule: true
	                    }
	                   
	                },  
	                messages:{  
	                    username:{  
	                        rangelength:"用户名长度{0}到{1}位",
	                        usernameRule: "用户名格式错误",
	                        remote: "用户名已存在"
	                    },
	                    password:{
	                    	rangelength:"密码长度{0}到{1}位",
	                    	passwordRule: "密码格式错误"
	                    },
	                    confirmPassword: {
	                    	equalTo:"两次密码不一致"
	                    },
	                    phone:{
	                    	phoneRule: "手机号不存在"
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