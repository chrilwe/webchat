package com.wechat.base.model.im;

import java.util.Date;

import lombok.Data;
import lombok.ToString;

/**
 * im tcp服务器详细信息
 * @author chrilwe
 *
 */
@Data
@ToString
public class ImServerDetail {
	//tcp服务器地址
	private String serverAddress;
	//上一次发送心跳时间
	private Date lastHeartBeatTime;
	//客户端口号
	private String clientId;
}
