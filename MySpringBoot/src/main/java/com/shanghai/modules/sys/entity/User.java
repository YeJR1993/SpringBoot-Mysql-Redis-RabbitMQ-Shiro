package com.shanghai.modules.sys.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.shanghai.common.utils.excel.ExcelField;
import com.shanghai.common.validator.IsMobile;

/**
* @author: YeJR 
* @version: 2018年4月27日 下午3:28:03
* 系统用户
*/
public class User implements Serializable{
	
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 * @JestId: 在存入ES时，就会默认使用该Id
	 */
	private Integer id;
	
	/**
	 * 用户名
	 * validation校验
	 */
	private String username;
	
	/**
	 * 密码
	 */
	@Length(max=64)
	private String password;
	
	/**
	 * 电话
	 */
	@Length(max=11)
	@IsMobile
	private String phone;
	
	/**
	 * 用户头像
	 */
	private String headImg;
	
	/**
	 * 用户开始时间
	 */
	private Date startTime;
	
	/**
	 * 用户结束时间
	 */
	private Date endTime;
	
	/**
	 * 新增属性用于：用户保存并保存角色
	 */
	private List<Integer> roleIds;
	
	/**
	 * 角色
	 */
	private List<Role> roles = new ArrayList<Role>();
	
	
	public User() {
		super();
	}
	
	public User(Integer id) {
		super();
		this.id = id;
	}

	/**
	 * @ExcelField, 使用自定义的注解
	 * @return
	 */
	@ExcelField(title="ID", type=1, align=2, sort=1)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@ExcelField(title="用户名", align=2, sort=2)
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@ExcelField(title="手机号", align=2, sort=3)
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	@ExcelField(title="头像", align=2, sort=4)
	public String getHeadImg() {
		return headImg;
	}

	public void setHeadImg(String headImg) {
		this.headImg = headImg;
	}

	@ExcelField(title="开始时间", align=2, sort=5)
	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	@ExcelField(title="结束时间", align=2, sort=6)
	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	
	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	public List<Integer> getRoleIds() {
		return roleIds;
	}

	public void setRoleIds(List<Integer> roleIds) {
		this.roleIds = roleIds;
	}

	/**
	 * 该用户是否有管理员角色
	 * @return
	 */
	@JsonIgnore
	public Boolean isAdmin() {
		for (Role role : roles) {
			if (role.getIsAdmin() == 1) {
				return true;
			}
		}
		return false;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", password=" + password + ", phone=" + phone
				+ ", headImg=" + headImg + ", startTime=" + startTime + ", endTime=" + endTime + ", roleIds=" + roleIds
				+ ", roles=" + roles + "]";
	}

	
}
