package org.atlas.apps.localization.domain;

public class Factors {
	
	public static int WALL_ATTENUATION_FACTOR = 3; //In dBm
	
	/**
	 * Is the height difference(in meters) between the placed beacons and the sensor that watched for the location.
	 * E.g 
	 * 	Beacon is at height = 1m
	 * Watched sensor is at height 0.2m
	 * The value will be 0.8m
	 */
	public static double BEACON_WATCHER_HEIGHT_DIFFERENCE = 0.8;//In meters

}
