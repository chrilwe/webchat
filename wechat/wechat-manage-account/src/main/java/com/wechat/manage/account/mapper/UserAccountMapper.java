package com.wechat.manage.account.mapper;

import com.wechat.base.model.account.UserAccount;

/**
 * 
 * @author chrilwe
 *
 */
public interface UserAccountMapper {
	// add UserAccount
	public void addUserAccount(UserAccount userAccount);
	
	// update UserAccount
	public void updateUserAccount(UserAccount userAccount);
	
	// select UserAccount by account_no
	public UserAccount selectByAccountNo(String accountNo);
}
