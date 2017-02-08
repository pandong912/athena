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
public class RequestConfig {
	
	protected boolean convert;
	protected List<SegmentMapping> segments;

	public RequestConfig() {
		this.segments = new LinkedList<>();
	}

	public RequestConfig(RequestConfig orig) {
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
		if (!(obj instanceof RequestConfig)) {
			return false;
		}
		RequestConfig other = (RequestConfig) obj;
		if (convert != other.convert)
			return false;
		if (segments == null) {
			if (other.segments != null)
				return false;
		} else if (!segments.equals(other.segments))
			return false;
		return true;
	}

	public static final class Builder {
		protected RequestConfig target;

		public Builder() {
			this.target = new RequestConfig();
		}

		public Builder(RequestConfig orig) {
			this.target = new RequestConfig(orig);
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

		public RequestConfig build() {
			embalmTarget();
			RequestConfig result = this.target;
			this.target = new RequestConfig(this.target);
			return result;
		}

		protected void embalmTarget() {

		}
	}
}
