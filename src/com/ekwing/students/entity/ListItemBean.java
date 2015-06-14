package com.ekwing.students.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ListItemBean implements Serializable {
	private String grade; // 选择每一项的答案
	private String id; // 题目id
	private String question; // 题目标题
	private String selected, text;
	// private List<AnswerBean> answer = new ArrayList<AnswerBean>(); // ABCD选项

	// 听力理解返回查看结果
	private String answer;
	private String pid;
	private String partTitle;
	private String qtitle;
	private String result;
	private List<AnswerBean> options = new ArrayList<AnswerBean>();

	public String getAnswer() {
		if (answer == null) {
			answer = "";
		}
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public String getQtitle() {
		if (qtitle == null) {
			qtitle = "";
		}
		return qtitle;
	}

	public String getSelected() {
		return selected;
	}

	public void setSelected(String selected) {
		this.selected = selected;
	}

	public String getText() {
		if (text == null) {
			text = "";
		}
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public void setQtitle(String qtitle) {
		this.qtitle = qtitle;
	}

	public String getPid() {
		if (pid == null) {
			pid = "";
		}
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getPartTitle() {
		if (partTitle == null) {
			partTitle = "";
		}
		return partTitle;
	}

	public void setPartTitle(String partTitle) {
		this.partTitle = partTitle;
	}

	public String getResult() {
		if (result == null) {
			result = "";
		}
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public List<AnswerBean> getOptions() {
		if (options == null || "".equals(options)) {
			options = new ArrayList<AnswerBean>();
		}
		return options;
	}

	public void setOptions(List<AnswerBean> options) {
		this.options = options;
	}

	public String getGrade() {
		if (grade == null) {
			grade = "";
		}
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public String getId() {
		if (id == null) {
			id = "";
		}
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getQuestion() {
		if (question == null) {
			question = "";
		}
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	// public List<AnswerBean> getAnswer() {
	// if (answer == null) {
	// answer = new ArrayList<AnswerBean>();
	// }
	// return answer;
	// }
	//
	// public void setAnswer(List<AnswerBean> answer) {
	// this.answer = answer;
	// }
	//
	// public ListItemBean(String grade, String id, String question,
	// List<AnswerBean> answer) {
	// super();
	// this.grade = grade;
	// this.id = id;
	// this.question = question;
	// this.answer = answer;
	// }

	// @Override
	// public String toString() {
	// return "ListItemBean [grade=" + grade + ", id=" + id + ", question=" +
	// question + ", answer=" + answer + ", pid=" + pid + ", partTitle="
	// + partTitle + ", qtitle=" + qtitle + ", result=" + result + ", options="
	// + options + "]";
	// }

}
