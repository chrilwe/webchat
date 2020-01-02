package com.wechat.base.model.user;

import java.util.Date;

import lombok.Data;
import lombok.ToString;

/**
 * 好友分组
 * @author chrilwe
 *
 */
@Data
@ToString
public class FriendGroup {
	private int id;
	private String groupName;//分组名称
	private Date createTime;
	private Date updateTime;
	private int userId;
}
