package com.ekwing.students.entity;

/**
 * 阅读理解提交每个问题的答案详情
 * 
 * @author ytbnkkf
 * 
 */
public class UnderCommitDetailBean {
	private String id;
	private String answer;
	private String user_answer;

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

	public String getUser_answer() {
		return user_answer;
	}

	public void setUser_answer(String user_answer) {
		this.user_answer = user_answer;
	}

	@Override
	public String toString() {
		return "UnderCommitDetailBean [id=" + id + ", answer=" + answer + ", user_answer=" + user_answer + "]";
	}

}
