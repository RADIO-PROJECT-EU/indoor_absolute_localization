package org.atlas.apps.localization.estimators;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DistanceDecision {
	
	@JsonProperty("min-rssi")
	private int minRssi;
	
	@JsonProperty("max-rssi")
	private int maxRssi;
	
	private double distance;
	
	public int getMinRssi() {
		return minRssi;
	}
	public void setMinRssi(int minRssi) {
		this.minRssi = minRssi;
	}
	public int getMaxRssi() {
		return maxRssi;
	}
	public void setMaxRssi(int maxRssi) {
		this.maxRssi = maxRssi;
	}
	public double getDistance() {
		return distance;
	}
	public void setDistance(double distance) {
		this.distance = distance;
	}

}
