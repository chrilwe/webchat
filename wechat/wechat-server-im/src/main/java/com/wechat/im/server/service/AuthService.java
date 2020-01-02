package com.wechat.im.server.service;

import com.wechat.base.model.im.ImMessageModel;

public interface AuthService {
	public boolean auth(ImMessageModel model);
}
