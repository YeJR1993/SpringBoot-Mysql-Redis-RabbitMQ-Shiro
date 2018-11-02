<!DOCTYPE html>
<html lang="zh">

	<head>
		<meta charset="UTF-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<title>登录</title>
		<link rel="stylesheet" type="text/css" href="/bootstrap/css/bootstrap.min.css">
		<link rel="stylesheet" type="text/css" href="/common/css/login.css">
	</head>

	<body>
		<div class="htmleaf-container">
			<div class="demo form-bg">
				<div class="container">
					<div class="row">
						<div class="col-md-offset-3 col-md-6 center">
							<form class="form-horizontal" id="loginForm" method="POST" action="/authorize">
								<span class="heading">用户登录</span>
								<div class="form-group">
									<input type="text" class="form-control" id="username" name="username" autocomplete="off" placeholder="用户名" required="required">
								</div>
								<div class="form-group help">
									<input type="password" class="form-control" id="password" name="password" placeholder="密　码" required="required"> 
								</div>
								<div class="form-group">
									<input type="text" class="form-control verifyInput" id="validateCode" name="validateCode" autocomplete="off" placeholder="验证码" required="required">
									<a href="javascript:void(0);" title="点击更换验证码">
							            <img id="imgVerify" src="${baseUrl!''}/getVerify" alt="更换验证码"  onclick="getVerify()">
							        </a>
								</div>
								<div class="form-group">
									<div class="main-checkbox">
										<input type="checkbox" id="rememberMe" name="rememberMe" />
										<label for="rememberMe"></label>
									</div>
									<span class="text">记住我</span>
									<button type="submit" class="btn btn-default">提交</button>
								</div>
							</form>
						</div>
					</div>
				</div>
			</div>
		</div>
		<input id="message" icon=2 type="hidden" value="${msg!''}" hidden="hidden">
		<input id="baseUrl" type="hidden" value="${baseUrl!''}">
		
		<script type="text/javascript" src="/webjars/jquery/3.3.1-1/jquery.min.js"></script>
		<script type="text/javascript" src="/layer/layer.js"></script>
		<script type="text/javascript" src="/common/js/common.js"></script>
		<script type="text/javascript">
			$(document).ready(function() {
				// 防止session过期之后登录页在iframe中出现
				if(self.frameElement && self.frameElement.tagName == "IFRAME"){
					top.location = "/";
				}
			});
		
			//获取验证码
			function getVerify(){
				var src = $("#baseUrl").val() + "/getVerify?" + Math.random()
				$("#imgVerify").attr("src", src); 
			}
		</script>
		
	</body>

</html>