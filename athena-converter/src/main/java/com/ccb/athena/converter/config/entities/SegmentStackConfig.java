package com.ccb.athena.converter.config.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SegmentStackConfig implements Serializable {

	protected String name;
	protected List<SegmentMapping> segments;

	public SegmentStackConfig() {
		this.segments = new ArrayList<>();
	}

	public SegmentStackConfig(SegmentStackConfig orig) {
		this.segments = new ArrayList<>(orig.segments);
		this.name = orig.name;
	}

	public String getName() {
		return this.name;
	}

	public List<SegmentMapping> getSegments() {
		return this.segments;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((segments == null) ? 0 : segments.hashCode());
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
		if (!(obj instanceof SegmentStackConfig)) {
			return false;
		}
		SegmentStackConfig other = (SegmentStackConfig) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (segments == null) {
			if (other.segments != null)
				return false;
		} else if (!segments.equals(other.segments))
			return false;
		return true;
	}

	public static class Builder {
		protected SegmentStackConfig target;

		public Builder(String name) {
			this.target = new SegmentStackConfig();
			this.target.name = name;
		}

		public Builder name(String name) {
			this.target.name = name;
			return this;
		}

		public Builder addSegment(SegmentMapping segmentMapping) {
			this.target.segments.add(segmentMapping);
			return this;
		}

		public Builder addSegments(List<SegmentMapping> segmentMappings) {
			this.target.segments.addAll(segmentMappings);
			return this;
		}

		public SegmentStackConfig build() {
			embalmTarget();
			SegmentStackConfig result = this.target;
			this.target = new SegmentStackConfig(this.target);
			return result;
		}

		protected void embalmTarget() {

		}
	}
}
