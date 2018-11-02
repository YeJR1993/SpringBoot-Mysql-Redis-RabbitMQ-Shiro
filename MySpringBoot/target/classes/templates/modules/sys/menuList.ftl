<!DOCTYPE html>
<html lang="zh">

	<head>
		<#include "../../include/treeTable.ftl"/>
	</head>

	<body class="contentBody">
		<div class="animated fadeIn fullHeight">
			<div class="tpl-portlet-components contentMinHight">
				<div class="portlet-title">
					<div class="caption font-green bold">
						<span class="am-icon-code"></span> 菜单列表
					</div>
				</div>
				<div class="tpl-block">
					<div class="am-g">
						<div class="am-u-sm-12 am-u-md-6">
							<div class="am-btn-toolbar">
								<div class="am-btn-group am-btn-group-xs">
									<@shiro.hasPermission name="sys:add:menu">
										<button type="button" class="am-btn am-btn-default am-btn-success" onclick="openDialogSave('添加菜单', '/sys/menu/form','730px', '620px')"><span class="am-icon-plus"></span> 添加</button>
									</@shiro.hasPermission>
									<@shiro.hasPermission name="sys:delete:menu">
										<button type="button" class="am-btn am-btn-default am-btn-danger" onclick="deleteMultIterm('确认要删除吗？','/sys/menu/deleteAll')"><span class="am-icon-trash-o"></span> 删除</button>
									</@shiro.hasPermission>
								</div>
							</div>
						</div>
					</div>
					<div class="am-g">
						<div class="am-u-sm-12">
							<table class="am-table am-table-hover table-main" id="treeTable">
								<thead>
									<tr>
										<th class="table-check"><input type="checkbox" class="tpl-table-fz-check checkboxAll"></th>
										<th>名称</th>
										<th>链接</th>
										<th>排序</th>
										<th>可见</th>
										<th>权限标识</th>
										<th>操作</th>
									</tr>
								</thead>
								<tbody>
									<#list list as menu>
										<tr data-tt-id="${menu.id}" data-tt-parent-id="${menu.parentId}">
											<td class="table-check"><input type="checkbox" id="${menu.id}" name="box" class="tpl-table-fz-check"></td>
											<td>${menu.name !''}</td>
											<td>${menu.href !''}</td>
											<td>${menu.sort !''}</td>
											<td>${(menu.isShow==0) ? string('隐藏','显示')}</td>
											<td>${menu.permission !''}</td>
											<td>
												<div class="am-btn-toolbar">
                                                    <div class="am-btn-group am-btn-group-xs">
	                                                    <@shiro.hasPermission name="sys:view:menu">
	                                                    	<button class="am-btn am-btn-default am-btn-xs am-hide-sm-only" onclick="openDialogView('查看菜单', '/sys/menu/form?id=${menu.id}','730px', '620px')"><span class="am-icon-copy"></span> 查看</button>
	                                                    </@shiro.hasPermission>
	                                                    <@shiro.hasPermission name="sys:edit:menu">
															<button class="am-btn am-btn-default am-btn-xs am-text-secondary"  onclick="openDialogSave('编辑菜单', '/sys/menu/form?id=${menu.id}','730px', '620px')"><span class="am-icon-pencil-square-o"></span> 编辑</button>
	                                                    </@shiro.hasPermission>
	                                                    <@shiro.hasPermission name="sys:add:menu">
															<button class="am-btn am-btn-default am-btn-xs am-text-thirdly"  onclick="openDialogSave('添加下级菜单', '/sys/menu/form?parentId=${menu.id}','730px', '620px')"><span class="am-icon-plus"></span> 添加下级菜单</button>
	                                                    </@shiro.hasPermission>
	                                                    <@shiro.hasPermission name="sys:delete:menu">
			                                            	<button class="am-btn am-btn-default am-btn-xs am-text-danger am-hide-sm-only"  onclick="deleteItem('确认要删除吗？', '/sys/menu/delete?id=${menu.id}')"><span class="am-icon-trash-o"></span> 删除</button>
	                                                    </@shiro.hasPermission>
												    </div>
                                                </div>
											</td>
										</tr>
									</#list>
								</tbody>
							</table>
						</div>
					</div>
				</div>
			</div>
		</div>
		<input id="message" type="hidden" value="${msg!''}" icon=1>
		
		<script type="text/javascript">
			// treeTable 初始化
			$("#treeTable").treetable({ expandable: true,column:1});
		</script>
	</body>

</html>