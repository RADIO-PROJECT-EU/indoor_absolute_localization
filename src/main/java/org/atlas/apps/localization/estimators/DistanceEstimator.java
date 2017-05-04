package org.atlas.apps.localization.estimators;

public interface DistanceEstimator {
	public double calculateDistance(int txpower, double rssi);
}
