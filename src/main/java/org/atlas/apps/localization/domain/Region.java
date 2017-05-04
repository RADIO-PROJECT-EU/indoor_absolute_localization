package org.atlas.apps.localization.domain;

import java.util.HashMap;

import org.atlas.apps.localization.beacons.Beacon;

public class Region {
	
	private String regionName;
	private HashMap<String, Beacon> beacons;
	
	public Region(){}
	
	public Region(String regionName){
		this.regionName = regionName;
		this.beacons = new HashMap<String, Beacon>();
	}
	
	public Region(String regionName, HashMap<String, Beacon> beacons){
		this.regionName = regionName;
		this.beacons = beacons;
	}
	
	public String getRegionName() {
		return regionName;
	}
	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}
	public HashMap<String, Beacon> getBeacons() {
		return beacons;
	}
	public void setBeacons(HashMap<String, Beacon> beacons) {
		this.beacons = beacons;
	}
	

}
