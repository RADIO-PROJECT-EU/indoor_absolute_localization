package org.atlas.apps.localization.filters;

public interface RssiFilter {
	
	/**
	 * Adding a new Measurements to the filter.
	 * @param rssi
	 */
	public void addMeasurement(double rssi);
	
	/**
	 * Calculate the RSSI according to the past and new one measurements.
	 * @return
	 */
	public double calculateRssi();
	public void resetFilter();

}
