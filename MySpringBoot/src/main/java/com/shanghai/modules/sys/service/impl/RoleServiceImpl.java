package com.shanghai.modules.sys.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.shanghai.common.utils.PageInfo;
import com.shanghai.modules.sys.dao.RoleDao;
import com.shanghai.modules.sys.entity.Role;
import com.shanghai.modules.sys.service.RoleService;

/**
 * @author YeJR
 * @version 2018年6月21日 上午21:19:22
 */
@Service
public class RoleServiceImpl implements RoleService{
	
	@Autowired
	private RoleDao roleDao;
	
	@Override
	public Role getRoleById(Integer id) {
		Role role = new Role();
		role.setId(id);
		return roleDao.get(role);
	}
	
	@Override
	public PageInfo<Role> findUserByPage(int pageNo, int pageSize, Role role) {
		PageHelper.startPage(pageNo, pageSize);
		return new PageInfo<>(roleDao.findList(role));
	}
	
	@Override
	public List<Role> findAllList(Role role) {
		return roleDao.findList(role);
	}

	@Override
	public List<Role> findUserAllRole(Integer userId) {
		List<Role> list = new ArrayList<Role>();
		if (userId != null) {
			list = roleDao.findUserAllRole(userId);
		}
		return list;
	}

	@Override
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public Integer saveRole(Role role) {
		return roleDao.insert(role);
	}

	@Override
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public Integer updateRole(Role role) {
		return roleDao.update(role);
	}

	@Override
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public void deleteRoleById(Integer id) {
		Role role = new Role(id);
		roleDao.delete(role);
		// 删除角色--菜单关系
		roleDao.deleteRoleMenu(role);
		// 删除角色--用户关系
		roleDao.deleteRoleUser(role);
	}

	@Override
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public void deleteRoleByIds(Integer[] ids) {
		if (ids != null) {
			for (Integer id : ids) {
				deleteRoleById(id);
			}
		}
	}

	@Override
	public boolean verifyRoleName(String roleName, String oldRoleName) {
		if (StringUtils.isNotBlank(roleName)) {
			if (roleName.equals(oldRoleName)) {
				return true;
			}
			Role role = roleDao.getByRoleName(roleName);
			if (role == null) {
				return true;
			}
		}
		return false;
	}

	@Override
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public void authSave(Role role) {
		// 先删除角色原有的对应的菜单
		roleDao.deleteRoleMenu(role);
		//接着插入新的对应关系
		if (role.getMenus() != null && role.getMenus().size() > 0) {
			roleDao.insertRoleMenu(role);
		}
	}


}
