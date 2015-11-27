/**

 */
package com.guoku.guokuv4.bean;

/**
 * @zhangyao
 * @Description: TODO
 * @date 2015年11月26日 下午1:52:40 
 * 引导页数据
 */
public class LaunchBean {
	
	   /**
     * action_title : test
     * description : test
     * title : test
     * launch_image_url : http://imgcdn.guoku.com/images/f1d67fadae3164e45f87ec2f4e84bcb6.jpg
     * action : guoku://entity/1555044/
     * launch_id : 1
     */

    private String action_title;
    private String description;
    private String title;
    private String launch_image_url;
    private String action;
    private int launch_id;

    public void setAction_title(String action_title) {
        this.action_title = action_title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setLaunch_image_url(String launch_image_url) {
        this.launch_image_url = launch_image_url;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public void setLaunch_id(int launch_id) {
        this.launch_id = launch_id;
    }

    public String getAction_title() {
        return action_title;
    }

    public String getDescription() {
        return description;
    }

    public String getTitle() {
        return title;
    }

    public String getLaunch_image_url() {
        return launch_image_url;
    }

    public String getAction() {
        return action;
    }

    public int getLaunch_id() {
        return launch_id;
    }

}
