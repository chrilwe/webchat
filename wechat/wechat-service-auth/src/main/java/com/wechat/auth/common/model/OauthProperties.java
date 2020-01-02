package com.wechat.auth.common.model;

import lombok.Data;
import lombok.ToString;

/**
 * 配置参数
 * @author chrilwe
 *
 */
@Data
@ToString
public class OauthProperties {
	private String applay_token_uri;
	private String client_id;
	private String client_secret;
	private long token_expire_time;
	private String cookie_name;
	private int cookie_max_age;
	private String domain;
}
