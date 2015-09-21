package com.ekwing.students.entity;

public class WordSelectBean {
	private String book_content_id, id, select, text, units_id;
	private chooses chooses;

	public WordSelectBean(String book_content_id, String select, String text,
			String units_id, String id, chooses chooses) {
		this.book_content_id = book_content_id;
		this.id = id;
		this.select = select;
		this.text = text;
		this.chooses = chooses;
		this.chooses.getA().setBook_content_id(book_content_id);
		this.chooses.getB().setBook_content_id(book_content_id);
		this.chooses.getC().setBook_content_id(book_content_id);
		this.chooses.getD().setBook_content_id(book_content_id);

		this.chooses.getA().setUnits_id(units_id);
		this.chooses.getB().setUnits_id(units_id);
		this.chooses.getC().setUnits_id(units_id);
		this.chooses.getD().setUnits_id(units_id);
		this.units_id = units_id;
	}

	private WordSelectBean() {
	}

	public chooses getChooses() {
		return chooses;
	}

	public void setChooses(chooses chooses) {
		this.chooses = chooses;
	}

	public String getBook_content_id() {
		return book_content_id;
	}

	public void setBook_content_id(String book_content_id) {
		this.book_content_id = book_content_id;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSelect() {
		return select;
	}

	public void setSelect(String select) {
		this.select = select;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getUnits_id() {
		return units_id;
	}

	public void setUnits_id(String units_id) {
		this.units_id = units_id;
	}

}
