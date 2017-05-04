package org.atlas.apps.localization.filters;

import java.util.HashMap;
import java.util.List;

import org.atlas.apps.localization.beacons.Beacon;
import org.atlas.apps.localization.domain.Position;
import org.atlas.apps.localization.utils.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BeaconsFilter {
	
	private static HashMap<String, Beacon> beaconsMap;
	private static final Logger logger = LoggerFactory.getLogger(BeaconsFilter.class);
	static{
		beaconsMap = new HashMap<String, Beacon>();
		List<Beacon> beacons = MapUtils.loadBeacons();
		logger.info("Total beacons found: " + beacons.size());
		for( Beacon beacon : beacons ){
			beacon.setPosition(new Position(beacon.getLocation_x(), beacon.getLocation_y()));
			beaconsMap.put(beacon.getAddress(), beacon);
		}
	}
	
	public static boolean isLocalizationBeacon(String address){
		if( beaconsMap.get(address) != null ) return true;
		logger.warn("Unable to find beacon with address "+address+".");
		return false;
	}

	public static HashMap<String, Beacon> getBeacons(){
		return beaconsMap;
	}
	
	public static Beacon getBeacon(String address){
		return beaconsMap.get(address);
	}
	
}
