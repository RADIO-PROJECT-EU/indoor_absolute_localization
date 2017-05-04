package org.atlas.apps.localization.utils;

import java.util.Arrays;

import org.apache.commons.math3.ml.distance.EuclideanDistance;
import org.atlas.apps.localization.domain.Position;
import org.atlas.apps.localization.exceptions.LocalizationDataMismatchException;

public class LocalizationUtils {
	
	/**
	 * This value determines the interval between the calculation of the Positions. 
	 * The Localization algorithm will run every 1000 milliseconds as defined below.
	 */
	public static long POSITION_CALCULATION_INTERVAL = 1000; //In milliseconds
	
	/**
	 * This function calcudate distance between the Beacons and Watching node in the 2D Enviroment, 
	 * 	assuming that Height that the Beacons are is bigger than the height of the Watching node.
	 * @param actualDistance - 
	 * @param height
	 * @return
	 */
	public static double normalizeDistance(double actualDistance, double height){
		return Math.sqrt((actualDistance*actualDistance)-(height*height));
	}
	
	/**
	 * Find the position between to positions
	 * @param first - First position
	 * @param second - Second Position
	 * @return - The actual Distance
	 */
	public static double getDistance(Position first, Position second){
		EuclideanDistance distance = new EuclideanDistance();
		return distance.compute(first.toArray(), second.toArray());
	}
	
	/**
	 * Calcuate the speed in which an object moved from first place to another in meters/second
	 * @param first - The first position
	 * @param second - The second position
	 * @param time - The time passed from first position  to second position in seconds
	 * @return - The calculated average speed 
	 */
	public static double calculateSpeed(Position first, Position second, long time){
		return getDistance(first, second) / time;
	}

	/**
	 * The metric that is used to evaluate the accuracy in localization process is Root Mean Square Error (RMSE), Smaller RMSE is better.
	 * @param actuals - Is the real position
	 * @param estimates -Is the position estimated from our algorithm.
	 * @return	- RSME
	 * @throws LocalizationDataMismatchException 
	 */
	public static double calculateRSME(Position[] actuals, Position[] estimates) throws LocalizationDataMismatchException{
		double rmse = 0;
		if( actuals.length != estimates.length ) throw new LocalizationDataMismatchException("Actuals data and Estimates do not have the same size.");
		int length = actuals.length; 
		for (int i=0; i<length; i++) {
			rmse += Math.pow((actuals[i].getX()-estimates[i].getX()), 2) + Math.pow((actuals[i].getY()-estimates[i].getY()), 2);
		}
		return Math.sqrt(rmse / length);
	}
	
	public static double calculateWeightedMedian(double[] values, double[] weights) throws LocalizationDataMismatchException{
		
		if( values.length != weights.length ) throw new LocalizationDataMismatchException("Values and Weights length are not the same.");
		int length = weights.length;
		double weights_sum = 0;
		for( int i=0; i<length; i++ ){weights_sum += weights[i];}
		
		if( weights_sum != 1 ) throw new LocalizationDataMismatchException("Weights summary must be equals to 1");
		
		double weightedMedian = 0;
		Arrays.sort(values);
		Arrays.sort(weights);
		
		double iLowerFromkSum = 0;
		double iGreaterFromkSum = 0;
		for( int i=0; i<length; i++ ){
			if( iLowerFromkSum < 0.5 ){
				for( int y=(i+1);y<length; y++ ){
					iGreaterFromkSum += weights[y];
				}
				if( iGreaterFromkSum <= 0.5 ){
					weightedMedian = weights[i];
					break;
				}else{
					iGreaterFromkSum = 0;
				}
			}
			iLowerFromkSum += weights[i];
		}
		return weightedMedian;
	}
	
}
