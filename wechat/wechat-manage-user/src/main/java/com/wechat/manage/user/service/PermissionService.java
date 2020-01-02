package com.wechat.manage.user.service;

import java.util.List;

import com.wechat.base.model.user.Permission;

/**
 * 
 * @author chrilwe
 *
 */
public interface PermissionService {
	//根据用户id查询权限
	public List<Permission> queryPermissionsByUserId(int userId);
}
