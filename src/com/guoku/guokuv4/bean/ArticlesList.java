package com.guoku.guokuv4.bean;

import java.io.Serializable;

/**
 * zhangyao zhangyao@guoku.com 15/9/15 13:26
 */
public class ArticlesList implements Serializable{

	/**
	 * read_count : 11 pub_time : 1.44228973E9 showcover : False title :
	 * 超级酷，终于可以用乐高搭建自己的家了！ url : /articles/669/ creator :
	 * {"is_censor":false,"bio":"写不完了写不完了写不完了","avatar_large":
	 * "http://imgcdn.guoku.com/avatar/1c4c5f8b1a9e892cc46b0a00913be6cd.jpg"
	 * ,"user_id"
	 * :132096,"following_count":10,"fan_count":19,"city":"朝阳","gender"
	 * :"F","avatar_small"
	 * :"http://imgcdn.guoku.com/avatar/1c4c5f8b1a9e892cc46b0a00913be6cd.jpg"
	 * ,"is_active"
	 * :"3","entity_note_count":0,"taobao_token_expires_in":604800,"tag_count"
	 * :0,"website":"","taobao_nick":"flora92688","like_count":58,"relation":0,
	 * "location":"北京","nickname":"爱福","email":"min-246@163.com"} cover :
	 * images/5267eb1a07357a65784998d4c8a2dedb.jpeg publish : 2 content :
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 说乐高是世界上最受欢迎的玩具应该没有人质疑，它的无穷变化可以满足全年龄段人的需要，看着那些不断的被刷新的搭建神作，可以感觉的到，
	 * 乐高迷们恨不得把这个世界都用乐高来搭建。没错，记得谁说过：好想法不会永远孤单的，美国的EverBlock公司创始人Arnon
	 * Rosan与他的团队便基于这种想法，终于将积木开发成建材，开始真正的用于居家建造用途上。
	 * EverBlock积木模组系统让使用者能够利用真人大小的积木砖块随心打造自己的居住环境
	 * 。正如同乐高积木的创造性一般，人们可以将这些通用的积木砖堆叠拼接在一起，建造出任何形状、任何图形、大小的物品。
	 * 他们目前提供12英寸长的全砖、6英寸长的半砖
	 * 、3英寸长的四分之一砖以及12英寸长的封顶砖等四种尺寸，并且期盼在不断的改善、适应下，将应用范围从家具逐渐扩展到门窗
	 * 、外墙等结构，最后建造出真正可供居住的积木屋。
	 * EverBlock无论是作为餐桌的底座、房间的隔板或是墙面，甚至想搭建出一栋建物，都可以实现，并且不会需要动用任何的工具
	 * 、黏着剂，它的材料选择则是耐高温、耐冲击、抗侵蚀的聚合物材质。甚至钢筋的大型基座，让建设大型建物时可以更加的牢靠。
	 * 当然，使用EverBlock搭建出的所有物品都如同把玩积木一般
	 * ，可以再次拆解开并重新组装。也正是这种可多次利用的特性，使得EverBlock相较于传统建材成为了一种独一无二
	 * 、可重复使用的的绿色建造方式。但一个挑战是
	 * ，人们很容易就能够观察出积木之间应该如何拼接，但是面对真人大小的积木，这不但不简单，而且富有挑战性。但无论怎样
	 * ，在未来，没准你真的可以自己搭建一个乐高的家
	 * ，尤其是在需要快速搭建的情况下，EverBlock应该明智之选，比如说应急避难所。所以，如果你是个乐高的超级粉，那么从现在开始就动手练习起来吧。
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * Lego 乐高站 10218 Pet Shop 宠物商店
	 * 
	 * ￥1248.00
	 * 
	 * 
	 * 查看详情
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * article_id : 669
	 */

	private String read_count;
	private long pub_time;
	private String showcover;
	private String title;
	private String url;
	private CreatorEntity creator;
	private String cover;
	private String publish;
	private String content;
	private int article_id;
	private boolean is_dig;//是否赞过
	private int dig_count;//赞的数量

	public int getDig_count() {
		return dig_count;
	}

	public void setDig_count(int dig_count) {
		this.dig_count = dig_count;
	}

	public boolean isIs_dig() {
		return is_dig;
	}

	public void setIs_dig(boolean is_dig) {
		this.is_dig = is_dig;
	}

	public void setRead_count(String read_count) {
		this.read_count = read_count;
	}

	public void setPub_time(long pub_time) {
		this.pub_time = pub_time;
	}

	public void setShowcover(String showcover) {
		this.showcover = showcover;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void setCreator(CreatorEntity creator) {
		this.creator = creator;
	}

	public void setCover(String cover) {
		this.cover = cover;
	}

	public void setPublish(String publish) {
		this.publish = publish;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void setArticle_id(int article_id) {
		this.article_id = article_id;
	}

	public String getRead_count() {
		return read_count;
	}

	public long getPub_time() {
		return pub_time;
	}

	public String getShowcover() {
		return showcover;
	}

	public String getTitle() {
		return title;
	}

	public String getUrl() {
		return url;
	}

	public CreatorEntity getCreator() {
		return creator;
	}

	public String getCover() {
		return cover;
	}
	
	public String get240() {
		if (cover.contains("images")) {
			return cover.replaceFirst("images", "images/240");
		}
		return cover;
	}
	
	public String get50() {
		if (cover.contains("images")) {
			return cover.replaceFirst("images", "images/50");
		}
		return cover;
	}

	public String getPublish() {
		return publish;
	}

	public String getContent() {
		return content;
	}
	
	public String getContent50() {
		if(content.length() > 50){
			content.substring(0, 50);
		}
		return content;
	}

	public int getArticle_id() {
		return article_id;
	}

	public static class CreatorEntity  implements Serializable{
		/**
		 * is_censor : false bio : 写不完了写不完了写不完了 avatar_large :
		 * http://imgcdn.guoku.com/avatar/1c4c5f8b1a9e892cc46b0a00913be6cd.jpg
		 * user_id : 132096 following_count : 10 fan_count : 19 city : 朝阳 gender
		 * : F avatar_small :
		 * http://imgcdn.guoku.com/avatar/1c4c5f8b1a9e892cc46b0a00913be6cd.jpg
		 * is_active : 3 entity_note_count : 0 taobao_token_expires_in : 604800
		 * tag_count : 0 website : taobao_nick : flora92688 like_count : 58
		 * relation : 0 location : 北京 nickname : 爱福 email : min-246@163.com
		 */

		private boolean is_censor;
		private String bio;
		private String avatar_large;
		private int user_id;
		private int following_count;
		private int fan_count;
		private String city;
		private String gender;
		private String avatar_small;
		private String is_active;
		private int entity_note_count;
		private int taobao_token_expires_in;
		private int tag_count;
		private String website;
		private String taobao_nick;
		private int like_count;
		private int relation;
		private String location;
		private String nickname;
		private String email;

		public void setIs_censor(boolean is_censor) {
			this.is_censor = is_censor;
		}

		public void setBio(String bio) {
			this.bio = bio;
		}

		public void setAvatar_large(String avatar_large) {
			this.avatar_large = avatar_large;
		}

		public void setUser_id(int user_id) {
			this.user_id = user_id;
		}

		public void setFollowing_count(int following_count) {
			this.following_count = following_count;
		}

		public void setFan_count(int fan_count) {
			this.fan_count = fan_count;
		}

		public void setCity(String city) {
			this.city = city;
		}

		public void setGender(String gender) {
			this.gender = gender;
		}

		public void setAvatar_small(String avatar_small) {
			this.avatar_small = avatar_small;
		}

		public void setIs_active(String is_active) {
			this.is_active = is_active;
		}

		public void setEntity_note_count(int entity_note_count) {
			this.entity_note_count = entity_note_count;
		}

		public void setTaobao_token_expires_in(int taobao_token_expires_in) {
			this.taobao_token_expires_in = taobao_token_expires_in;
		}

		public void setTag_count(int tag_count) {
			this.tag_count = tag_count;
		}

		public void setWebsite(String website) {
			this.website = website;
		}

		public void setTaobao_nick(String taobao_nick) {
			this.taobao_nick = taobao_nick;
		}

		public void setLike_count(int like_count) {
			this.like_count = like_count;
		}

		public void setRelation(int relation) {
			this.relation = relation;
		}

		public void setLocation(String location) {
			this.location = location;
		}

		public void setNickname(String nickname) {
			this.nickname = nickname;
		}

		public void setEmail(String email) {
			this.email = email;
		}

		public boolean getIs_censor() {
			return is_censor;
		}

		public String getBio() {
			return bio;
		}

		public String getAvatar_large() {
			return avatar_large;
		}

		public int getUser_id() {
			return user_id;
		}

		public int getFollowing_count() {
			return following_count;
		}

		public int getFan_count() {
			return fan_count;
		}

		public String getCity() {
			return city;
		}

		public String getGender() {
			return gender;
		}

		public String getAvatar_small() {
			return avatar_small;
		}

		public String getIs_active() {
			return is_active;
		}

		public int getEntity_note_count() {
			return entity_note_count;
		}

		public int getTaobao_token_expires_in() {
			return taobao_token_expires_in;
		}

		public int getTag_count() {
			return tag_count;
		}

		public String getWebsite() {
			return website;
		}

		public String getTaobao_nick() {
			return taobao_nick;
		}

		public int getLike_count() {
			return like_count;
		}

		public int getRelation() {
			return relation;
		}

		public String getLocation() {
			return location;
		}

		public String getNickname() {
			return nickname;
		}

		public String getEmail() {
			return email;
		}
	}
}
