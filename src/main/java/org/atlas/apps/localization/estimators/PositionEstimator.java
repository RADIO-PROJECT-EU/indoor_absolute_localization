package org.atlas.apps.localization.estimators;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.apache.commons.math3.fitting.leastsquares.LeastSquaresOptimizer.Optimum;
import org.apache.commons.math3.fitting.leastsquares.LevenbergMarquardtOptimizer;
import org.apache.commons.math3.util.Precision;
import org.atlas.apps.localization.beacons.Beacon;
import org.atlas.apps.localization.domain.Position;
import org.atlas.apps.localization.trilateration.NonLinearLeastSquaresSolver;
import org.atlas.apps.localization.trilateration.TrilaterationFunction;

public class PositionEstimator {
	
	private HashMap<String, Beacon> beacons; 
	private Random random;
	
	public PositionEstimator(){
		this.random = new Random();
	}
	
	public HashMap<String, Beacon> getBeacons() {
		return beacons;
	}

	public void setBeacons(HashMap<String, Beacon> beacons) {
		this.beacons = beacons;
	}

	public Position getPosition(){
		double[][] positions = new double[this.beacons.size()][2];
		double[] distances = new double[this.beacons.size()];
		int cnt = 0;
		for (Map.Entry<String, Beacon> entry : this.beacons.entrySet()) {
			Beacon beacon = entry.getValue();
			positions[cnt][0] = beacon.getPosition().getX();
			positions[cnt][1] = beacon.getPosition().getY();
			distances[cnt] = beacon.getDistance();
			cnt++;
		}
		NonLinearLeastSquaresSolver solver = new NonLinearLeastSquaresSolver(new TrilaterationFunction(positions, distances), new LevenbergMarquardtOptimizer());
		Optimum optimum = solver.solve();
		double[] centroid = optimum.getPoint().toArray();
		return new Position(Precision.round(centroid[0], 2),Precision.round(centroid[1], 2));
		
	}
	
	/**
	 * Will be used to add a random velocity to the pedestrian movements according to the previous velocity history
	 */
	private double nextTruncatedNormal(double mean, double stdev, double min, double max){
		double value = 0;
		while(true){
			value = mean + stdev*this.random.nextGaussian();
			if(min<value && value<max){
				break;
			}
		}
		return value;
	}

}
