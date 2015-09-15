/**

 */
package com.guoku.guokuv4.entity.test;

/**
 * @zhangyao
 * @Description: TODO
 * @date 2015-9-8 下午3:00:29 
 * 商品一级分类
 */
public class Categories {
	
	Category category;
	
	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public class Category{
		
		String cover_url;
		
		String id;
		
		String status;
		
		String title;

		public String getCover_url() {
			return cover_url;
		}

		public void setCover_url(String cover_url) {
			this.cover_url = cover_url;
		}

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public String getStatus() {
			return status;
		}

		public void setStatus(String status) {
			this.status = status;
		}

		public String getTitle() {
			return title;
		}

		public void setTitle(String title) {
			this.title = title;
		}
		
	}

}
