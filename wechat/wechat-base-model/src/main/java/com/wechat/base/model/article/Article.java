package com.wechat.base.model.article;

import java.util.Date;

import lombok.Data;
import lombok.ToString;

/**
 * 用户发表的文章
 * @author chrilwe
 *
 */
@Data
@ToString
public class Article {
	private String id;
	//文章发表人
	private int userId;
	//文章内容
	private String articleContent;
	//文章来源
	private int originUserId;
	//文章发表时间
	private Date createTime;
	//文章被转发次数
	private int removeTimes;
	//文章被点赞次数
	private int highOpinion;
	//文章被踩次数
	private int badOpinion;
	
}
