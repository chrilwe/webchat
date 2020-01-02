package com.wechat.base.model.im;

import java.io.Serializable;

import lombok.Data;
import lombok.ToString;

/**
 * 通讯系统消息model
 * @author chrilwe
 *
 */
@Data
@ToString
public class ImMessageModel implements Serializable {
	private String accessToken;//连接server认证用户
	private int senderId;//发送消息方id
	private int receiverId;//接收消息方id
	private String message;//消息内容
	private String messageType;//消息类型：连接消息，聊天消息
}
