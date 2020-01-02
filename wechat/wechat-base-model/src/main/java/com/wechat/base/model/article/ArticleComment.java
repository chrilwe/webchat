package com.wechat.base.model.article;

import java.util.Date;

import lombok.Data;
import lombok.ToString;

/**
 * 文章评论
 * @author chrilwe
 *
 */
@Data
@ToString
public class ArticleComment {
	private int id;
	//评论的文章
	private int originArticleId;
	//评论的人
	private int commentPeopleId;
	//评论内容
	private String commentContent;
	//评论时间
	private Date commentTime;
	//被回复的对象
	private int callbackUserId;
	//父类评论id
	private int pid;
}
