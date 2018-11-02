<!DOCTYPE html>
<html lang="zh">

	<head>
		<#include "../include/head.ftl"/>
		<style type="text/css">
			.menu-div {
				overflow：hidden; 
			}
			.menu {
				overflow-y:auto;
				overflow-x:hidden;
			}
		</style>
	</head>

	<body>
		<!-- 顶部 -->
		<header class="am-topbar am-topbar-inverse admin-header">
			
			<!-- 顶部logo -->
			<div class="am-topbar-brand">
				<a href="javascript:;" class="tpl-logo">
					<img src="/common/img/yejr.png">
				</a>
			</div>
			
			<!-- logo旁的控制菜单按钮 -->
			<div class="am-icon-list tpl-header-nav-hover-ico am-fl am-margin-right"></div>
			
			<button class="am-topbar-btn am-topbar-toggle am-btn am-btn-sm am-btn-success am-show-sm-only" data-am-collapse="{target: '#topbar-collapse'}"><span class="am-sr-only">导航切换</span> <span class="am-icon-bars"></span></button>
			
			<!-- 导航栏右侧消息部分 -->
			<div class="am-collapse am-topbar-collapse" id="topbar-collapse">
		
				<ul class="am-nav am-nav-pills am-topbar-nav am-topbar-right admin-header-list tpl-header-list">
				
					<li class="am-dropdown" data-am-dropdown data-am-dropdown-toggle>
						<a class="am-dropdown-toggle tpl-header-list-link" href="javascript:;">
							<span class="am-icon-bell-o"></span> 提醒 <span class="am-badge tpl-badge-success am-round">5</span></span>
						</a>
						<ul class="am-dropdown-content tpl-dropdown-content">
							<li class="tpl-dropdown-content-external">
								<h3>你有 <span class="tpl-color-success">5</span> 条提醒</h3>
								<a href="###">全部</a>
							</li>
							<li class="tpl-dropdown-list-bdbc">
								<a href="#" class="tpl-dropdown-list-fl"><span class="am-icon-btn am-icon-plus tpl-dropdown-ico-btn-size tpl-badge-success"></span> 用户列表ok。</a>
								<span class="tpl-dropdown-list-fr">3小时前</span>
							</li>
							<li class="tpl-dropdown-list-bdbc">
								<a href="#" class="tpl-dropdown-list-fl"><span class="am-icon-btn am-icon-check tpl-dropdown-ico-btn-size tpl-badge-danger"></span> 用户权限ok</a>
								<span class="tpl-dropdown-list-fr">15分钟前</span>
							</li>
							<li class="tpl-dropdown-list-bdbc">
								<a href="#" class="tpl-dropdown-list-fl"><span class="am-icon-btn am-icon-bell-o tpl-dropdown-ico-btn-size tpl-badge-warning"></span> 用户增删改查ok</a>
								<span class="tpl-dropdown-list-fr">2天前</span>
							</li>
						</ul>
					</li>
					<li class="am-dropdown" data-am-dropdown data-am-dropdown-toggle>
						<a class="am-dropdown-toggle tpl-header-list-link" href="javascript:;">
							<span class="am-icon-comment-o"></span> 消息 <span class="am-badge tpl-badge-danger am-round">9</span></span>
						</a>
						<ul class="am-dropdown-content tpl-dropdown-content">
							<li class="tpl-dropdown-content-external">
								<h3>你有 <span class="tpl-color-danger">9</span> 条新消息</h3>
								<a href="###">全部</a>
							</li>
							<li>
								<a href="#" class="tpl-dropdown-content-message">
									<span class="tpl-dropdown-content-photo"><img src="/common/img/user02.png" alt=""> </span>
									<span class="tpl-dropdown-content-subject">
			                      		<span class="tpl-dropdown-content-from"> 禁言小张 </span>
										<span class="tpl-dropdown-content-time">10分钟前 </span>
									</span>
									<span class="tpl-dropdown-content-font"> 明天请我吃饭。 </span>
								</a>
								<a href="#" class="tpl-dropdown-content-message">
									<span class="tpl-dropdown-content-photo"><img src="/common/img/user03.png" alt=""> </span>
									<span class="tpl-dropdown-content-subject">
				                      	<span class="tpl-dropdown-content-from"> Steam </span>
										<span class="tpl-dropdown-content-time">18分钟前</span>
									</span>
									<span class="tpl-dropdown-content-font"> 请你妹。 </span>
								</a>
							</li>
						</ul>
					</li>
					<li class="am-dropdown" data-am-dropdown data-am-dropdown-toggle>
						<a class="am-dropdown-toggle tpl-header-list-link" href="javascript:;">
							<span class="am-icon-calendar"></span> 进度 <span class="am-badge tpl-badge-primary am-round">4</span></span>
						</a>
						<ul class="am-dropdown-content tpl-dropdown-content">
							<li class="tpl-dropdown-content-external">
								<h3>你有 <span class="tpl-color-primary">4</span> 个任务进度</h3>
								<a href="###">全部</a>
							</li>
							<li>
								<a href="javascript:;" class="tpl-dropdown-content-progress">
									<span class="task">
			                        	<span class="desc">请更新至 v1.2 </span>
										<span class="percent">45%</span>
									</span>
									<span class="progress">
		                        		<div class="am-progress tpl-progress am-progress-striped"><div class="am-progress-bar am-progress-bar-success" style="width:45%"></div></div>
		                    		</span>
								</a>
							</li>
							<li>
								<a href="javascript:;" class="tpl-dropdown-content-progress">
									<span class="task">
			                        	<span class="desc">新闻内容页 </span>
										<span class="percent">30%</span>
									</span>
									<span class="progress">
				                       	<div class="am-progress tpl-progress am-progress-striped"><div class="am-progress-bar am-progress-bar-secondary" style="width:30%"></div></div>
				                    </span>
								</a>
							</li>
							<li>
								<a href="javascript:;" class="tpl-dropdown-content-progress">
									<span class="task">
			                        	<span class="desc">管理中心 </span>
										<span class="percent">60%</span>
									</span>
									<span class="progress">
				                        <div class="am-progress tpl-progress am-progress-striped"><div class="am-progress-bar am-progress-bar-warning" style="width:60%"></div></div>
				                    </span>
								</a>
							</li>
		
						</ul>
					</li>
					<!-- 用户信息及功能菜单 -->
					<li class="am-dropdown" data-am-dropdown data-am-dropdown-toggle>
						<a class="am-dropdown-toggle tpl-header-list-link" href="javascript:;">
							<span class="tpl-header-list-user-nick">${loginName !''}</span><span class="tpl-header-list-user-ico"> <img src="/common/img/user01.png"></span>
						</a>
						<ul class="am-dropdown-content">
							<li>
								<a href="#"><span class="am-icon-bell-o"></span> 资料</a>
							</li>
							<li>
								<a href="#"><span class="am-icon-cog"></span> 设置</a>
							</li>
							<li>
								<a href="/logout"><span class="am-icon-power-off"></span> 退出</a>
							</li>
						</ul>
					</li>
					<li>
						<a href="/logout" class="tpl-header-list-link"><span class="am-icon-sign-out tpl-header-list-ico-out-size"></span></a>
					</li>
				</ul>
			</div>
		</header>
		<!-- 中间部分 -->
		<div class="tpl-page-container tpl-page-header-fixed">
			<!-- 菜单 -->
			<div class="menu-div">
				<@menu></@menu>
			</div>
				
			
			<!-- 内容区 -->
			<div class="tpl-content-wrapper" id="content">
				<iframe id="YeJR_iframe" name="YeJR_iframe" width="100%" height="100%" src="" ></iframe>
			</div>
			
		</div>
		
		<script type="text/javascript">
			$(document).ready(function() {
				if(self.frameElement && self.frameElement.tagName == "IFRAME"){
					top.layer.msg("记住我，权限不足。需要账号密码登录！", {icon : 2,offset : '40%',time : 2000}, function() {
						top.location = "/";
					});
				}
				// 重新计算内容区的高度
				var h = document.documentElement.clientHeight;//获取页面可见高度  
				$("#content").css("height", (h - 79) + "px");
				$(".menu").css("max-height", (h - 79) + "px");
			});
			//当浏览器窗口大小改变时，设置显示内容的高度  
			window.onresize=function(){
				// 重新计算内容区的高度
				var h = document.documentElement.clientHeight;//获取页面可见高度  
				$("#content").css("height", (h - 79) + "px");
				$(".menu").css("max-height", (h - 79) + "px");
			} 
		</script>
	</body>
</html>