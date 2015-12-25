/**

 */
package com.guoku.guokuv4.bean;

/**
 * @zhangyao
 * @Description: TODO
 * @date 2015年12月25日 上午11:25:31 
 * 喜爱&取消喜爱eventbus实体类
 */
public class LikesBean {
	
	public boolean isLike;//true:喜欢  false：取消喜欢
	
	public LikesBean(boolean like){
		setLike(like);
	}

	public boolean isLike() {
		return isLike;
	}

	public void setLike(boolean isLike) {
		this.isLike = isLike;
	}

}
