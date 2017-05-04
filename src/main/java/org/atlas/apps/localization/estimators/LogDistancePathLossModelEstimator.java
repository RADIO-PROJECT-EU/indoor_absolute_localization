package org.atlas.apps.localization.estimators;

/**
 * Implementation of the Log-Distance Path Loss Model (https://en.wikipedia.org/wiki/Log-distance_path_loss_model)
 * @author kasnot
 *
 */
public class LogDistancePathLossModelEstimator implements DistanceEstimator{

	/**
	 * Path Loss Exponent
	 * [Srinivasa2009] Srinivasan, S.; Haenggi, M. “Path loss exponent estimation in large wireless networks”, Information Theory and Applications Workshop, On pages 124 – 129, Feb 2009.
	 * http://www.gaussianwaves.com/2013/09/log-distance-path-loss-or-log-normal-shadowing-model/
	 */
	private final double PATH_LOSS_EXPONENT_FREE_SPACE = 2.0;
	private final double PATH_LOSS_EXPONENT_URBAN_AREA_CELLULAR_RADIO = 3.1;// 2.7 up to 3.5
	private final double PATH_LOSS_EXPONENT_SHADOWED_URBAN_CELLULAR_RADIO = 4.0;// 3.0 up to 4.0
	private final double PATH_LOSS_EXPONENT_INSIDE_BUILDING_LINE_OF_SIGHT = 1.7;// 1.6 up to 1.8
	private final double PATH_LOSS_EXPONENT_OBSTRUCTED_IN_BUILDING = 5.0;// 4.0 up to 6.0
	private final double PATH_LOSS_EXPONENT_OBSTRUCTED_IN_FACTORY = 2.5; // 2.0 up to 5.0
	
	//Is the referenced RSSI value at d0
	private final double A0 = -42;
	private final double D0 = 1;
	
	@Override
	public double calculateDistance(int txpower, double rssi) {

		double lookingValue = 1.5;
		//The above equation gives the RSSI; Transform to give the distance
		double myValue = (-10) * PATH_LOSS_EXPONENT_INSIDE_BUILDING_LINE_OF_SIGHT * Math.log(lookingValue / this.D0) + this.A0;
		return myValue;
	}

}
