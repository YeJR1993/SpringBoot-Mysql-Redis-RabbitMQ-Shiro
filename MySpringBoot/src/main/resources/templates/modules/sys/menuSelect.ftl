<!DOCTYPE html>
<html lang="zh">

	<head>
		<#include "../../include/zTree.ftl"/>
	</head>

	<body class="bodyColor">
		<ul id="menuTree" class="ztree"></ul>
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
					enable: false
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
		});
		
		function menuChoose () {
			var result = [];
			var nodes = tree.getSelectedNodes();
			if(nodes.length > 0){
				result.push(nodes[0].id);
				result.push(nodes[0].name);
			}
			return result;
		}
		</script>
	</body>
</html>