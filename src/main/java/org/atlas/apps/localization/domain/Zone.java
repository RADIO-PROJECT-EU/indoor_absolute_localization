package org.atlas.apps.localization.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Zone {
	
	/**
	 * Determine the Zone Identifier
	 */
	@JsonProperty("zone_id")
	private String zoneId;
	
	/**
	 * The min distance for the zone
	 */
	@JsonProperty("min_distance")
	private double minDistance;
	
	/**
	 * The max distance for the zone
	 */
	@JsonProperty("max_distance")
	private double maxDistance;
	
	/**
	 * Determine if the current Zone, will include to the Position estimation using the triangulation process
	 */
	private boolean include;
	
	public String getZoneId() {
		return zoneId;
	}
	public void setZoneId(String zoneId) {
		this.zoneId = zoneId;
	}
	
	public boolean isInclude() {
		return include;
	}
	public void setInclude(boolean include) {
		this.include = include;
	}
	public double getMinDistance() {
		return minDistance;
	}
	public void setMinDistance(double minDistance) {
		this.minDistance = minDistance;
	}
	public double getMaxDistance() {
		return maxDistance;
	}
	public void setMaxDistance(double maxDistance) {
		this.maxDistance = maxDistance;
	}
	@Override
	public String toString() {
		return "Zone [zoneId=" + zoneId + ", minDistance=" + minDistance
				+ ", maxDistance=" + maxDistance + ", include=" + include + "]";
	}
	
	

}
