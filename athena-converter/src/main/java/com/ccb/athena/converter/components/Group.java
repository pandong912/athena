package com.ccb.athena.converter.components;

import java.util.ArrayList;
import java.util.List;

public class Group extends Entry {
	
	private List<Entry> entries;
	private int times;

	public Group() {
		entries = new ArrayList<Entry>();
		times = 0;
	}

	public List<Entry> getEntries() {
		return entries;
	}

	public void setEntries(List<Entry> entries) {
		this.entries = entries;
	}

	public int getTimes() {
		return times;
	}

	public void setTimes(int times) {
		this.times = times;
	}

}
