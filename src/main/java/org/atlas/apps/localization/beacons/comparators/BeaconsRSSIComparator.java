package org.atlas.apps.localization.beacons.comparators;

import java.util.Comparator;

import org.atlas.apps.localization.beacons.Beacon;

public class BeaconsRSSIComparator implements Comparator<Beacon>{

	@Override
	public int compare(Beacon o1, Beacon o2) {
		return o2.getRssi()-o1.getRssi();
	}

}
