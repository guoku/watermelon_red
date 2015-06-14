package com.ekwing.students.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.lidroid.xutils.db.annotation.Table;

@Table(name = "level")
public class TestBean extends WordsBean implements Serializable {
	// public static ArrayList<TestBean> getTestBean(String json)
	// throws JSONException {
	// ArrayList<TestBean> list = new ArrayList<TestBean>();
	// JSONArray array = new JSONArray(json);
	// JSONObject object;
	// TestBean beam = new TestBean();
	// for (int i = 0; i < array.length(); i++) {
	// object = array.getJSONObject(i);
	// if (object.has("id")) {
	// beam.setId(object.getString("id"));
	// }
	// if (object.has("select")) {
	// beam.setAnswer(object.getString("select"));
	// }
	// if (object.has("qtype")) {
	// beam.setQtype(object.getString("qtype"));
	// }
	// if (object.has("option")) {
	// beam.setOption(object.getString("option"));
	// }
	// if (object.has("text")) {
	// beam.setText(object.getString("text"));
	// }
	// if (object.has("qtypetext")) {
	// beam.setQtypetext(object.getString("qtypetext"));
	// }
	// list.add(beam);
	// }
	//
	// return list;
	// }
	private String ret;
	private String id, answer, qtype, qtypetest, text, select;
	private List<OptionBean> option = new ArrayList<OptionBean>();

	public String getRet() {
		return ret;
	}

	public void setRet(String ret) {
		this.ret = ret;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public String getQtype() {
		return qtype;
	}

	public void setQtype(String qtype) {
		this.qtype = qtype;
	}

	public String getQtypetest() {
		if(qtypetest ==null){
			qtypetest="";
		}
		return qtypetest;
	}

	public void setQtypetest(String qtypetest) {
		this.qtypetest = qtypetest;
	}

	public String getText() {
		if(text == null){
			text ="";
		}
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getSelect() {
		return select;
	}

	public void setSelect(String select) {
		this.select = select;
	}

	public List<OptionBean> getOption() {
		if(option==null || "".equals(option)) {
			option = new ArrayList<OptionBean>();
		}
		return option;
	}

	public void setOption(List<OptionBean> option) {
		this.option = option;
	}

	@Override
	public String toString() {
		return "TestBean [ret=" + ret + ", id=" + id + ", answer=" + answer + ", qtype=" + qtype + ", qtypetest=" + qtypetest + ", text=" + text
				+ ", select=" + select + ", option=" + option + "]";
	}

}
