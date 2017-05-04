package org.atlas.apps.localization.beacons.comparators;

import java.util.Comparator;

import org.atlas.apps.localization.beacons.Beacon;

public class BeaconsDistanceComparator implements Comparator<Beacon>{

	@Override
	public int compare(Beacon o1, Beacon o2) {
		double result = (o2.getDistance()-o1.getDistance());
		if( result > 0) return 1;
		if(result < 0 ) return -1;
		return 0;
	}

}
