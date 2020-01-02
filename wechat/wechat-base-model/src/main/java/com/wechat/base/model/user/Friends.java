package com.wechat.base.model.user;

import lombok.Data;
import lombok.ToString;

/**
 * 用户好友列表
 * @author chrilwe
 *
 */
@Data
@ToString
public class Friends {
	private int id;
	private int groupId;//分组
	private int friendId;//好友id
}
