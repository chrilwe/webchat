package com.wechat.base.model.user.ext;

import java.util.List;

import com.wechat.base.model.user.Permission;
import com.wechat.base.model.user.User;

import lombok.Data;
import lombok.ToString;

/**
 * user信息扩展
 * @author chrilwe
 *
 */
@Data
@ToString
public class UserExt extends User {
	private List<Permission> permissions;//权限
}
