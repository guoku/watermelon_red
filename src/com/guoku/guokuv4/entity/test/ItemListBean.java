package com.guoku.guokuv4.entity.test;

import java.io.Serializable;

import android.util.Log;

public class ItemListBean implements Serializable {
	private String entity_id, buy_link, cid, price, origin_source, rank,
			volume, origin_id, id;

	public String getEntity_id() {
		return entity_id;
	}

	public void setEntity_id(String entity_id) {
		this.entity_id = entity_id;
	}

	public String getBuy_link() {
		// Log.e("url", buy_link);
		return buy_link;
	}

	public void setBuy_link(String buy_link) {
		this.buy_link = buy_link;
	}

	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getOrigin_source() {
		return origin_source;
	}

	public void setOrigin_source(String origin_source) {
		this.origin_source = origin_source;
	}

	public String getRank() {
		return rank;
	}

	public void setRank(String rank) {
		this.rank = rank;
	}

	public String getVolume() {
		return volume;
	}

	public void setVolume(String volume) {
		this.volume = volume;
	}

	public String getOrigin_id() {
		return origin_id;
	}

	public void setOrigin_id(String origin_id) {
		this.origin_id = origin_id;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
