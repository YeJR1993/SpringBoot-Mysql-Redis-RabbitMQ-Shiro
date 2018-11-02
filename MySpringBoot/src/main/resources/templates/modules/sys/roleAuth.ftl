<!DOCTYPE html>
<html lang="zh">

	<head>
		<#include "../../include/zTree.ftl"/>
	</head>

	<body class="bodyColor">
		<ul id="menuTree" class="ztree"></ul>
		<form class="am-form am-form-horizontal" id="saveForm" action="/sys/role/authSave">
			<input type="hidden" name="id" value="${role.id}">
			<input type="hidden" name="menuIds" id="menuIds">
		</form>
		<script type="text/javascript">
		//zTree对象
		var tree;
		$(document).ready(function() {
			//zTree配置
			var setting = {
				view: {
					addHoverDom: false,
					removeHoverDom: false,
					selectedMulti: false
				},
				check: {
					enable: true
				},
				data: {
					simpleData: {
						enable: true
					}
				},
				edit: {
					enable: false
				}
			};
			// zTree节点
			var zNodes = [<#list menuList as menu>{id:"${menu.id}", pId:"${menu.parentId}", name:"${menu.name}"},</#list>];
			// 初始化树结构
			tree = $.fn.zTree.init($("#menuTree"), setting, zNodes);
			// 默认选择节点
			var ids = "${role.menuIds}".split(",");
			for(var i=0; i<ids.length; i++) {
				var node = tree.getNodeByParam("id", ids[i]);
				try{tree.checkNode(node, true, false);}catch(e){}
			}
			// 默认展开全部节点
			tree.expandAll(true);
		});
		
		function doSubmit () {
			var ids = [], nodes = tree.getCheckedNodes(true);
			for(var i=0; i<nodes.length; i++) {
				ids.push(nodes[i].id);
			}
			$("#menuIds").val(ids);
			$("#saveForm").submit();
			return true;
		}
		
		
		</script>
	</body>
</html>