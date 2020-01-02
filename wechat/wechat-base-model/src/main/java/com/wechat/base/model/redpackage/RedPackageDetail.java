package com.wechat.base.model.redpackage;

import java.util.Date;

import lombok.Data;
import lombok.ToString;

/**
 * 红包详情
 * @author chrilwe
 *
 */
@Data
@ToString
public class RedPackageDetail {
	private int id;
	//红包描述
	private String des;
	//红包金额(分)
	private int total;
	//红包发送者
	private int senderId;
	//红包发送时间
	private Date createTime;
	//红包过期时间
	private Date endTime;
}
