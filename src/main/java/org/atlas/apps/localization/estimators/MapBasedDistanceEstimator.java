package org.atlas.apps.localization.estimators;

import java.util.List;

import org.atlas.apps.localization.utils.MapUtils;

public class MapBasedDistanceEstimator implements DistanceEstimator{
	
	private List<DistanceDecision> distances;
	
	public MapBasedDistanceEstimator(){
		this.distances = MapUtils.loadDistanceDecisionTree();
	}
	
	/**
	 * Find from a simple decision tree the value corresponding distance according to rssi specified
	 * @param rssi - The RSSI values measured
	 * @return - The distance
	 */
	@Override
	public double calculateDistance(int txpower, double rssi){
		for( DistanceDecision attr: this.distances ){
			if( (attr.getMinRssi() >= rssi) && (attr.getMaxRssi() <= rssi) ){
				return attr.getDistance();
			}
		}
		return 0;
	}

}
