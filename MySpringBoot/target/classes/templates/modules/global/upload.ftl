<!DOCTYPE html>
<html lang="zh">

	<head>
		<#include "../../include/webuploader.ftl"/>
	</head>

	<body>
		<div id="wrapper">
			<div id="container">
				<!--头部，相册选择和格式选择-->
				<div id="uploader">
					<div class="queueList">
						<div id="dndArea" class="placeholder">
							<div id="filePicker"></div>
						</div>
					</div>
					<div class="statusBar" style="display:none;">
						<div class="progress">
							<span class="text">0%</span>
							<span class="percentage"></span>
						</div>
						<div class="info"></div>
						<div class="btns">
							<div id="filePicker2"></div>
							<div class="uploadBtn">开始上传</div>
						</div>
					</div>
				</div>
			</div>
		</div>	
		<input type="hidden" value="${upType}" id="upType">	
		<input type="hidden" value="${objId}" id="objId">	
		<input type="hidden" value="${upNum}" id="upNum">	
		<input type="hidden" value="${parentName}" id="parentName">	
	</body>

</html>