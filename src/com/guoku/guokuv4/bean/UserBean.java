/**

 */
package com.guoku.guokuv4.bean;

import java.util.ArrayList;

import com.guoku.guokuv4.entity.test.EntityBean;
import com.guoku.guokuv4.entity.test.TabNoteBean;

/**
 * @zhangyao
 * @Description: TODO
 * @date 2016年2月25日 下午2:34:44 
 * 用户信息
 */
public class UserBean {
	
	ArrayList<EntityBean> last_user_like;//喜爱
	
	ArrayList<TabNoteBean> last_post_note;//评论
	
	UserBean user;//基本信息
	
	ArrayList<Articles> last_post_article;//图文

	public ArrayList<EntityBean> getLast_user_like() {
		return last_user_like;
	}

	public void setLast_user_like(ArrayList<EntityBean> last_user_like) {
		this.last_user_like = last_user_like;
	}

	public ArrayList<TabNoteBean> getLast_post_note() {
		return last_post_note;
	}

	public void setLast_post_note(ArrayList<TabNoteBean> last_post_note) {
		this.last_post_note = last_post_note;
	}

	public UserBean getUser() {
		return user;
	}

	public void setUser(UserBean user) {
		this.user = user;
	}

	public ArrayList<Articles> getLast_post_article() {
		return last_post_article;
	}

	public void setLast_post_article(ArrayList<Articles> last_post_article) {
		this.last_post_article = last_post_article;
	}
	
	

}
