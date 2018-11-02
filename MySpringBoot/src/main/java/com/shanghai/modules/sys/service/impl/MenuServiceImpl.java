package com.shanghai.modules.sys.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shanghai.modules.sys.dao.MenuDao;
import com.shanghai.modules.sys.entity.Menu;
import com.shanghai.modules.sys.service.MenuService;

/**
 * @author: YeJR
 * @version: 2018年6月25日 下午5:08:58
 * 
 */
@Service
public class MenuServiceImpl implements MenuService {

	@Autowired
	private MenuDao menuDao;
	
	
	@Override
	public List<Menu> getUserMenuList(Integer userId, Boolean isAdmin, Integer isShow) {
		List<Menu> menus = new ArrayList<Menu>();
		if (isAdmin) {
			Menu menu = new Menu();
			menu.setIsShow(isShow);
			menus = menuDao.findList(menu);
		} else {
			menus = menuDao.findByUserId(userId, isShow);
		}
		return menus;
	}


	@Override
	public List<Menu> findAllMenus(Menu menu) {
		// 查询并进行排序
		List<Menu> list = new ArrayList<Menu>();
		List<Menu> sourceList = menuDao.findList(menu);
		Menu.sortList(list, sourceList, 1);
		return list;
	}

	@Override
	public Menu getMenuById(Menu menu) {
		return menuDao.get(menu);
	}

	@Override
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public Integer saveMenu(Menu menu) {
		return menuDao.insert(menu);
	}

	@Override
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public Integer updateMenu(Menu menu) {
		return menuDao.update(menu);
	}
	
	@Override
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public void deleteMenuById(Integer id) {
		// 因为需要删除该菜单下的所有子菜单以及子子菜单等等，先查询
		Menu menu = menuDao.get(new Menu(id));
		// 递归删除子菜单、子子菜单等等
		if (menu != null && menu.getChildren() != null && menu.getChildren().size() > 0) {
			for(Menu childMenu : menu.getChildren()){
				deleteMenuById(childMenu.getId());
			}
		}
		menuDao.delete(menu);
	}

	@Override
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public void deletemenuByIds(Integer[] ids) {
		if (ids != null) {
			for (Integer id : ids) {
				deleteMenuById(id);
			}
		}
	}

}
