package com.guoku.guokuv4.entity.test;

import java.util.ArrayList;

public class JIngxuanBean {
	private String id, name, select;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSelect() {
		return select;
	}

	public void setSelect(String select) {
		this.select = select;
	}

	public static ArrayList<JIngxuanBean> getlist() {
		ArrayList<JIngxuanBean> list = new ArrayList<JIngxuanBean>();
		JIngxuanBean bean = new JIngxuanBean();

		// @"all", @"woman", @"man", @"kid",
		// @"accessories",@"beauty",@"tech",@"living",@"outdoors",@"culture",@"food",@"fun",
		bean.setName("所有");
		bean.setId("all");
		list.add(bean);
		bean = new JIngxuanBean();
		bean.setName("女装");
		bean.setId("woman");
		list.add(bean);

		bean = new JIngxuanBean();
		bean.setName("男装");
		bean.setId("man");
		list.add(bean);

		bean = new JIngxuanBean();
		bean.setName("孩童");
		bean.setId("kid");
		list.add(bean);

		bean = new JIngxuanBean();
		bean.setName("配饰");
		bean.setId("accessories");
		list.add(bean);

		bean = new JIngxuanBean();
		bean.setName("美容");
		bean.setId("beauty");
		list.add(bean);

		bean = new JIngxuanBean();
		bean.setName("科技");
		bean.setId("tech");
		list.add(bean);

		bean = new JIngxuanBean();
		bean.setName("居家");
		bean.setId("living");
		list.add(bean);

		bean = new JIngxuanBean();
		bean.setName("户外");
		bean.setId("outdoors");
		list.add(bean);

		bean = new JIngxuanBean();
		bean.setName("文化");
		bean.setId("culture");
		list.add(bean);

		bean = new JIngxuanBean();
		bean.setName("美食");
		bean.setId("food");
		list.add(bean);

		bean = new JIngxuanBean();
		bean.setName("玩乐");
		bean.setId("fun");
		list.add(bean);

		return list;
	}

}
