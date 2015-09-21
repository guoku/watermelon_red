package com.ekwing.students.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ListenQueryBean implements Serializable {
	private List<SoundListBean> soundList = new ArrayList<SoundListBean>();
	private List<RecordListBean> recordList = new ArrayList<RecordListBean>();
	private List<RetListBean> retList = new ArrayList<RetListBean>();

	public List<SoundListBean> getSoundList() {
		if (soundList == null || "".equals(soundList)) {
			soundList = new ArrayList<SoundListBean>();
		}
		return soundList;
	}

	public void setSoundList(List<SoundListBean> soundList) {
		this.soundList = soundList;
	}

	public List<RecordListBean> getRecordList() {
		if (recordList == null || "".equals(recordList)) {
			recordList = new ArrayList<RecordListBean>();
		}
		return recordList;
	}

	public void setRecordList(List<RecordListBean> recordList) {
		this.recordList = recordList;
	}

	public List<RetListBean> getRetList() {
		if (retList == null || "".equals(retList)) {
			retList = new ArrayList<RetListBean>();
		}
		return retList;
	}

	public void setRetList(List<RetListBean> retList) {
		this.retList = retList;
	}

	@Override
	public String toString() {
		return "ListenQueryBean [soundList=" + soundList + ", recordList="
				+ recordList + ", retList=" + retList + "]";
	}

}
