package com.ekwing.students.utils;

import android.os.AsyncTask;

import com.handmark.pulltorefresh.library.PullToRefreshListView;

public class EmpDataTask extends AsyncTask<Void, Void, Void> {
	private PullToRefreshListView scrollView;

	public EmpDataTask(PullToRefreshListView scrollView) {
		this.scrollView = scrollView;
	}

	@Override
	protected Void doInBackground(Void... params) {
		try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
		}
		return null;
	}

	@Override
	protected void onPostExecute(Void result) {
		super.onPostExecute(result);
		scrollView.onRefreshComplete();
	}
}
