<!DOCTYPE html>
<html lang="zh">

	<head>
		<#include "../../include/head.ftl"/>
	</head>

	<body class="contentBody">
		<div class="animated fadeIn fullHeight">
			<div class="tpl-portlet-components contentMinHight">
				<div class="portlet-title">
					<div class="caption font-green bold">
						<span class="am-icon-code"></span> 角色列表
					</div>
				</div>
				<div class="tpl-block">
					<div class="am-g">
						<div class="am-u-sm-12 am-u-md-6">
							<div class="am-btn-toolbar">
								<div class="am-btn-group am-btn-group-xs">
									<@shiro.hasPermission name="sys:add:role">
										<button type="button" class="am-btn am-btn-default am-btn-success" onclick="openDialogSave('添加角色', '/sys/role/form','690px', '330px')"><span class="am-icon-plus"></span> 添加</button>
									</@shiro.hasPermission>
									<@shiro.hasPermission name="sys:delete:role">
										<button type="button" class="am-btn am-btn-default am-btn-danger" onclick="deleteMultIterm('确认要删除吗？','/sys/role/deleteAll')"><span class="am-icon-trash-o"></span> 删除</button>
									</@shiro.hasPermission>
								</div>
							</div>
						</div>
						<form id="searchForm" action="/sys/role/list">
							<div class="am-u-sm-12 am-u-md-3">
	                            <div class="am-form-group">
	                                <select data-am-selected="{btnSize: 'sm'}" name="isAdmin">
	                                	<#if !role.isAdmin??>
	                                		<option value="-1" selected>全部</option>
								        	<option value="1" >管理员</option>
								        	<option value="0">非管理员</option>
								        <#else>
								        	<option value="-1" >全部</option>
								        	<option value="1" ${(role.isAdmin==1) ? string('selected','')}>管理员</option>
								        	<option value="0" ${(role.isAdmin==0) ? string('selected','')}>非管理员</option>
	                                	</#if>		
						            </select>
	                            </div>
	                        </div>
							<div class="am-u-sm-12 am-u-md-3">
								<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
								<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
								<div class="am-input-group am-input-group-sm">
									<input autocomplete="off" type="text" class="am-form-field" placeholder="输入查询的角色名" name="roleName" value="${role.roleName !''}">
									<span class="am-input-group-btn">
							            <button class="am-btn  am-btn-default am-btn-success tpl-am-btn-success am-icon-search" type="submit"></button>
							         </span>
								</div>
							</div>
						</form>
					</div>
					<div class="am-g">
						<div class="am-u-sm-12">
							<table class="am-table am-table-striped am-table-hover table-main">
								<thead>
									<tr>
										<th class="table-check"><input type="checkbox" class="tpl-table-fz-check checkboxAll"></th>
										<th>ID</th>
										<th>角色名</th>
										<th>管理员</th>
										<th>状态</th>
										<th>操作</th>
									</tr>
								</thead>
								<tbody>
									<#list page.list as role>
										<tr>
											<td> <input type="checkbox" id="${role.id}" name="box" class="tpl-table-fz-check"></td>
											<td>${role.id}</td>
											<td>${role.roleName}</td>
											<td><#if role.isAdmin == 1>是<#elseif role.isAdmin == 0>否</#if></td>
											<td><#if role.status == 1>启用<#elseif role.status == 0>禁用</#if></td>
											<td>
												<div class="am-btn-toolbar">
                                                    <div class="am-btn-group am-btn-group-xs">
	                                                    <@shiro.hasPermission name="sys:view:role">
	                                                    	<button class="am-btn am-btn-default am-btn-xs am-hide-sm-only" onclick="openDialogView('查看角色', '/sys/role/form?id=${role.id}','690px', '330px')"><span class="am-icon-copy"></span> 查看</button>
	                                                    </@shiro.hasPermission>
	                                                    <@shiro.hasPermission name="sys:edit:role">
															<button class="am-btn am-btn-default am-btn-xs am-text-secondary"  onclick="openDialogSave('编辑角色', '/sys/role/form?id=${role.id}','690px', '330px')"><span class="am-icon-pencil-square-o"></span> 编辑</button>
	                                                    </@shiro.hasPermission>
	                                                    <@shiro.hasPermission name="sys:allocation:role">
															<button class="am-btn am-btn-default am-btn-xs am-text-thirdly"  onclick="openDialogSave('权限设置', '/sys/role/auth?id=${role.id}','350px', '700px')"><span class="am-icon-pencil-square-o"></span> 权限设置</button>
	                                                    </@shiro.hasPermission>
	                                                    <@shiro.hasPermission name="sys:delete:role">
			                                            	<button class="am-btn am-btn-default am-btn-xs am-text-danger am-hide-sm-only"  onclick="deleteItem('确认要删除该角色吗？', '/sys/role/delete?id=${role.id}')"><span class="am-icon-trash-o"></span> 删除</button>
	                                                    </@shiro.hasPermission>
												    </div>
                                                </div>
											</td>
										</tr>
									</#list>
								</tbody>
							</table>
							${page.html}
						</div>
					</div>
				</div>
			</div>
		</div>
		<input id="message" type="hidden" value="${msg!''}" icon=1>
	</body>
</html>