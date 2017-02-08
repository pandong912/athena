/**
 * 
 */
package com.ccb.athena.converter.config.entities;

import java.util.LinkedList;
import java.util.List;

/**
 * @author congyang
 *
 */
public class ResponseConfig {

	protected boolean convert;
	protected List<SegmentMapping> segments;

	public ResponseConfig() {
		this.segments = new LinkedList<>();
	}

	public ResponseConfig(ResponseConfig orig) {
		this.segments = new LinkedList<>(orig.segments);
		this.convert = orig.convert;
	}

	public boolean isConvert() {
		return convert;
	}

	public void setConvert(boolean convert) {
		this.convert = convert;
	}

	public List<SegmentMapping> getSegments() {
		return segments;
	}

	public void setSegments(List<SegmentMapping> segments) {
		this.segments = segments;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (convert ? 1231 : 1237);
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
		if (!(obj instanceof ResponseConfig)) {
			return false;
		}
		ResponseConfig other = (ResponseConfig) obj;
		if (convert != other.convert)
			return false;
		if (segments == null) {
			if (other.segments != null)
				return false;
		} else if (!segments.equals(other.segments))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ResponseConfig [convert=" + convert + ", segments=" + segments + "]";
	}

	public static final class Builder {
		protected ResponseConfig target;

		public Builder() {
			this.target = new ResponseConfig();
		}

		public Builder(ResponseConfig orig) {
			this.target = new ResponseConfig(orig);
		}

		public Builder convert(boolean convert) {
			this.target.convert = convert;
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

		public ResponseConfig build() {
			embalmTarget();
			ResponseConfig result = this.target;
			this.target = new ResponseConfig(this.target);
			return result;
		}

		protected void embalmTarget() {

		}
	}
}
