$(function(){
	uploadFileShow();
});  
/**
 * 打开上传页面
 * @param upType  需要上传的类型
 * @param objId 上传控件ID
 * @param upNum 总的上传数量
 * @returns
 */
function openUploadPage(upType, objId, upNum) {
	// 定义弹出框的title
	var title = "";
	if (upType == 'image') {
		title = "上传图片";
	} else if (upType == 'music') {
		title = "上传音乐";
	} else if (upType == 'video') {
		title = "上传视频";
	}
	
	// 实际能够上传的文件的数量
	var id = "#" + objId;
	var filePaths = $(id).val();
	if (filePaths != "") {
		var arr = filePaths.split('|');
		upNum = upNum - arr.length;
	}
	
	//获取当前窗口名称， 用于子页面js向父页面元素传值
	var parentName= window.name;
	
	if (upNum != 0) {
		top.layer.open({
			type : 2,
			title : title,
			area : [ '690px', '550px' ],
			fixed : false, // 不固定
			maxmin : true,
			content : '/webuploader/openUploadPage?upType='+upType+'&objId='+objId+'&upNum='+upNum+'&parentName='+parentName// 打开子页面
		});
	} else {
		top.layer.msg("已达到上传上限！", {icon : 0, offset : '40%', time : 2000});
	}
}

/**
 * 显示上传的文件
 */
function uploadFileShow(){
	// 当前页面所有上传元素
	var uploadFileObjs = $(".uploadFile");
	for (var i = 0; i < uploadFileObjs.length; i++) {
		var uploadFileObj = uploadFileObjs[i];
		// 文件上传的路径
		var uploadFilePaths = uploadFileObj.defaultValue;
		// 文件上传类型
		var uploadFileType = uploadFileObj.attributes.uploadType.nodeValue;
		// 若已经有上传过图片
		if (uploadFilePaths != "") {
			// 原有的上传元素清除
			var id = "#uploadFile" + uploadFileObj.attributes.id.nodeValue;
			$(id).remove();
			// 若上传的是图片
			if (uploadFileType == "image") {
				imageShow(uploadFileObj, uploadFilePaths);
			} else {
				fileShow(uploadFileObj, uploadFilePaths);
			}
		}
		
	}
}

/**
 * 显示图片
 * @param uploadFileObj 当前对象
 * @param uploadFilePaths 文件路径
 * @returns
 */
function imageShow(uploadFileObj, uploadFilePaths) {
	var id = uploadFileObj.attributes.id.nodeValue;
	var uploadFilePathArr = uploadFilePaths.split("|");
	var uploadFileHtml = '<div style="margin-top:5px;" id="uploadFile'+ id +'">';
	for (var i = 0; i < uploadFilePathArr.length; i++) {
		uploadFileHtml = uploadFileHtml + '<div class="upfile-div" filePath="'+ uploadFilePathArr[i] +'" id="uploadFile'+ id + i +'"><img class="upfile-img" src="'+ uploadFilePathArr[i] +'"/><a class="upfile-a" href="javascript:void(0);" onclick = "fileDel(\''+id+'\','+i+')">×</a></div>'
	}
	uploadFileHtml = uploadFileHtml + '</div>';
	uploadFileObj.parentNode.insertAdjacentHTML('afterend', uploadFileHtml);
}

/**
 * 文件显示
 * @param uploadFileObj
 * @param uploadFilePaths
 * @returns
 */
function fileShow(uploadFileObj, uploadFilePaths) {
	var id = uploadFileObj.attributes.id.nodeValue;
	var uploadFilePathArr = uploadFilePaths.split("|");
	var uploadFileHtml = '<div style="margin-top:5px;" id="uploadFile'+ id +'">';
	for (var i = 0; i < uploadFilePathArr.length; i++) {
		var uploadFilePath = uploadFilePathArr[i];
		var fileArray = uploadFilePath.split("/");
		var fileName = fileArray[fileArray.length - 1];
		uploadFileHtml = uploadFileHtml + '<div class="upfile-div" filePath="'+ uploadFilePathArr[i] +'" id="uploadFile'+ id + i +'"><span class="upfile-span">'+ fileName +'</span><a class="upfile-a" href="javascript:void(0);" onclick = "fileDel(\''+id+'\','+i+')">×</a></div>'
	}
	uploadFileHtml = uploadFileHtml + '</div>';
	uploadFileObj.parentNode.insertAdjacentHTML('afterend', uploadFileHtml);
}

/**
 * 页面 X 删除功能
 * @param objId
 * @param index
 * @returns
 */
function fileDel (objId, index) {
	var id = "#" + objId;
	var fileDivId = "#uploadFile" + objId + index;
	var uploadFilePaths = $(id).val();
	var uploadFilePathArr = uploadFilePaths.split("|");
	// 获取需要删除的路径
	var delPath = $(fileDivId).attr("filePath");
	// 数组中删除该元素
	for (var i = 0; i < uploadFilePathArr.length; i++) {
		if (uploadFilePathArr[i] == delPath) {
			uploadFilePathArr.splice(i,1);
		}
	}
	// 重新组合字符串
	var uploadFileNewPaths = "";
	for (var i = 0; i < uploadFilePathArr.length; i++) {
		uploadFileNewPaths = uploadFileNewPaths + "|" + uploadFilePathArr[i];
	}
	// 若最前面有“|”
	if (uploadFileNewPaths.indexOf("|") == 0) {
		uploadFileNewPaths = uploadFileNewPaths.substr(1, uploadFileNewPaths.length);
	}
	// 替换删除
	$(id).val(uploadFileNewPaths);
	$(fileDivId).remove();
}
