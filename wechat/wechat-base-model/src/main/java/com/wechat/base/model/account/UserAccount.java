package com.wechat.base.model.account;

import java.util.Date;

import lombok.Data;
import lombok.ToString;

/**
 * 用户账户
 * @author chrilwe
 *
 */
@Data
@ToString
public class UserAccount {
	private String accountNo;
	private int userId;//账号用户id
	private int status;//账号状态 1.正常   0.冻结
	private long money;//当前账号余额(分)
	private Date createTime;//创建账号日期
	private String payPassword;//支付密码 
	private int errorPasswordTimesLimit;//一天输入密码错误次数
	private Date firstInputPasswordTime;//第一次输入密码时间
}
