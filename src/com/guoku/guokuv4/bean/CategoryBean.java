/**

 */
package com.guoku.guokuv4.bean;

import java.io.Serializable;
import java.util.List;

/**
 * @zhangyao
 * @Description: TODO
 * @date 2015年11月19日 下午4:57:05 
 * 品类（包含二级品类）
 */
public class CategoryBean implements Serializable{
	
    /**
     * category_count : 30
     * content : [{"category_icon_large":"","category_icon_small":"","category_id":162,"category_title":"避孕套","status":1}]
     * group_id : 37
     * status : 1
     * title : 保健 HEALTH
     */

    private int category_count;
    private int group_id;
    private int status;
    private String title;
    /**
     * category_icon_large :
     * category_icon_small :
     * category_id : 162
     * category_title : 避孕套
     * status : 1
     */

    private List<ContentEntity> content;

    public void setCategory_count(int category_count) {
        this.category_count = category_count;
    }

    public void setGroup_id(int group_id) {
        this.group_id = group_id;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(List<ContentEntity> content) {
        this.content = content;
    }

    public int getCategory_count() {
        return category_count;
    }

    public int getGroup_id() {
        return group_id;
    }

    public int getStatus() {
        return status;
    }

    public String getTitle() {
        return title;
    }

    public List<ContentEntity> getContent() {
        return content;
    }

    public static class ContentEntity implements Serializable{
        private String category_icon_large;
        private String category_icon_small;
        private int category_id;
        private String category_title;
        private int status;

        public void setCategory_icon_large(String category_icon_large) {
            this.category_icon_large = category_icon_large;
        }

        public void setCategory_icon_small(String category_icon_small) {
            this.category_icon_small = category_icon_small;
        }

        public void setCategory_id(int category_id) {
            this.category_id = category_id;
        }

        public void setCategory_title(String category_title) {
            this.category_title = category_title;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getCategory_icon_large() {
            return category_icon_large;
        }

        public String getCategory_icon_small() {
            return category_icon_small;
        }

        public int getCategory_id() {
            return category_id;
        }

        public String getCategory_title() {
            return category_title;
        }

        public int getStatus() {
            return status;
        }
    }

}
