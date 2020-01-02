package com.wechat.base.model.account;

import java.util.Date;

import lombok.Data;
import lombok.ToString;

/**
 * 交易详情
 * @author chrilwe
 *
 */
@Data
@ToString
public class TransactionDetails {
	private int id;
	private String accountNo;//账户id
	private int status;//交易状态      1.交易成功   0.交易失败
	private Date transactionStartTime;//交易开始时间
	private Date transactionEndTime;//交易结束时间
	private int transactionType;//交易类型    0.支出    1.收入
	private String costToAccountNo;//支出到账号
	private String receiveFromAccountNo;//收入的来源账号
	private String transactionDesc;//交易信息描述
	private long transactionMoney;//交易金额
}
