package com.ccb.athena.converter.config.entities;

import java.io.Serializable;

import com.ccb.athena.converter.components.Segment;

public class SegmentMapping implements Serializable {

	protected String name;
	protected Segment segment;

	public SegmentMapping(String name, Segment segment) {
		this.name = name;
		this.segment = segment;
	}

	public SegmentMapping(SegmentMapping orig) {
		this.name = orig.name;
		this.segment = orig.segment;
	}

	public String getName() {
		return name;
	}

	public Segment getSegment() {
		return segment;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((segment == null) ? 0 : segment.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		if (!(obj instanceof SegmentMapping)) {
			return false;
		}
		SegmentMapping other = (SegmentMapping) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (segment == null) {
			if (other.segment != null)
				return false;
		} else if (!segment.equals(other.segment))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "SegmentMapping [name=" + name + ", segment=" + segment + "]";
	}
}
