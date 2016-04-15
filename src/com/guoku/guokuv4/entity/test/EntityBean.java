package com.guoku.guokuv4.entity.test;

import java.io.Serializable;

import com.guoku.guokuv4.utils.StringUtils;

public class EntityBean implements Serializable {
	private String entity_id, weight, note_count, price, intro, created_time,
			old_category_id, chief_image, entity_hash, score_count, novus_time,
			title, mark, brand, status, item_list, total_score, item_id_list,
			mark_value, like_already, old_root_category_id, updated_time,
			creator_id, like_count, category_id;

	private String detail_images;

	public String getDetail_images() {
		return detail_images;
	}

	public String get240() {
		if(!StringUtils.isEmpty(chief_image)){
			if (chief_image.contains("images")) {
				return chief_image.replaceFirst("images", "images/240");
			}
		}
		return chief_image;
	}
	
	public String get640() {
		if (chief_image.contains("images")) {
			return chief_image.replaceFirst("images", "images/640");
		}
		return chief_image;
	}

	public String get800() {
		if (chief_image.contains("images")) {
			return chief_image.replaceFirst("images", "images/800");
		}
		return chief_image;
	}

	public String get50() {
		if (chief_image.contains("images")) {
			return chief_image.replaceFirst("images", "images/100");
		}
		return chief_image;
	}

	public void setDetail_images(String detail_images) {
		this.detail_images = detail_images;
	}

	public String getEntity_id() {
		return entity_id;
	}

	public void setEntity_id(String entity_id) {
		this.entity_id = entity_id;
	}

	public String getWeight() {
		return weight;
	}

	public void setWeight(String weight) {
		this.weight = weight;
	}

	public String getNote_count() {
		return note_count;
	}

	public void setNote_count(String note_count) {
		this.note_count = note_count;
	}
	
	public String getNote_countAdd() {
		if (getNote_count().equals("")) {
			note_count = "0";
		}
		int buf = Integer.parseInt(note_count) + 1;
		note_count = buf + "";
		return note_count;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getIntro() {
		return intro;
	}

	public void setIntro(String intro) {
		this.intro = intro;
	}

	public String getCreated_time() {
		return created_time;
	}

	public void setCreated_time(String created_time) {
		this.created_time = created_time;
	}

	public String getOld_category_id() {
		return old_category_id;
	}

	public void setOld_category_id(String old_category_id) {
		this.old_category_id = old_category_id;
	}

	public String getChief_image() {
		return chief_image;
	}

	public void setChief_image(String chief_image) {
		this.chief_image = chief_image;
	}

	public String getEntity_hash() {
		return entity_hash;
	}

	public void setEntity_hash(String entity_hash) {
		this.entity_hash = entity_hash;
	}

	public String getScore_count() {
		return score_count;
	}

	public void setScore_count(String score_count) {
		this.score_count = score_count;
	}

	public String getNovus_time() {
		return novus_time;
	}

	public void setNovus_time(String novus_time) {
		this.novus_time = novus_time;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getMark() {
		return mark;
	}

	public void setMark(String mark) {
		this.mark = mark;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getItem_list() {
		return item_list;
	}

	public void setItem_list(String item_list) {
		this.item_list = item_list;
	}

	public String getTotal_score() {
		return total_score;
	}

	public void setTotal_score(String total_score) {
		this.total_score = total_score;
	}

	public String getItem_id_list() {
		return item_id_list;
	}

	public void setItem_id_list(String item_id_list) {
		this.item_id_list = item_id_list;
	}

	public String getMark_value() {
		return mark_value;
	}

	public void setMark_value(String mark_value) {
		this.mark_value = mark_value;
	}

	public String getLike_already() {
		return like_already;
	}

	public void setLike_already(String like_already) {
		this.like_already = like_already;
	}

	public String getOld_root_category_id() {
		return old_root_category_id;
	}

	public void setOld_root_category_id(String old_root_category_id) {
		this.old_root_category_id = old_root_category_id;
	}

	public String getUpdated_time() {
		return updated_time;
	}

	public void setUpdated_time(String updated_time) {
		this.updated_time = updated_time;
	}

	public String getCreator_id() {
		return creator_id;
	}

	public void setCreator_id(String creator_id) {
		this.creator_id = creator_id;
	}

	public String getLike_count() {
		if (like_count != null && "0".equals(like_count)) {
			return "";
		}
		return like_count;
	}

	public String getLike_countAdd() {
		if (getLike_count().equals("")) {
			like_count = "0";
		}
		int buf = Integer.parseInt(like_count) + 1;
		like_count = buf + "";
		return like_count;
	}

	public String getLike_countCut() {
		if (getLike_count().equals("")) {
			like_count = "0";
		}
		int buf = Integer.parseInt(like_count) - 1;
		if (buf < 0) {
			buf = 0;
		}
		like_count = buf + "";
		return like_count;
	}

	public void setLike_count(String like_count) {
		this.like_count = like_count;
	}

	public String getCategory_id() {
		return category_id;
	}

	public void setCategory_id(String category_id) {
		this.category_id = category_id;
	}

	@Override
	public String toString() {
		return "EntityBean [detail_images=" + detail_images + ", entity_id="
				+ entity_id + ", weight=" + weight + ", note_count="
				+ note_count + ", price=" + price + ", intro=" + intro
				+ ", created_time=" + created_time + ", old_category_id="
				+ old_category_id + ", chief_image=" + chief_image
				+ ", entity_hash=" + entity_hash + ", score_count="
				+ score_count + ", novus_time=" + novus_time + ", title="
				+ title + ", mark=" + mark + ", brand=" + brand + ", status="
				+ status + ", item_list=" + item_list + ", total_score="
				+ total_score + ", item_id_list=" + item_id_list
				+ ", mark_value=" + mark_value + ", like_already="
				+ like_already + ", old_root_category_id="
				+ old_root_category_id + ", updated_time=" + updated_time
				+ ", creator_id=" + creator_id + ", like_count=" + like_count
				+ ", category_id=" + category_id + "]";
	}

}
