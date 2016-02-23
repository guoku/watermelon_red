/**

 */
package com.guoku.guokuv4.bean;

import java.util.ArrayList;

import com.guoku.guokuv4.entity.test.UserBean;

/**
 * @zhangyao
 * @Description: TODO
 * @date 2016年2月23日 上午11:43:45 全部推荐用户list
 */
public class AuthorizedUserListBean {

	int count;

	int page;

	ArrayList<UserBean> authorized_user;

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public ArrayList<UserBean> getAuthorized_user() {
		return authorized_user;
	}

	public void setAuthorized_user(ArrayList<UserBean> authorized_user) {
		this.authorized_user = authorized_user;
	}

}
