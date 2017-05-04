package org.atlas.apps.localization.utils;

import org.apache.commons.math3.stat.descriptive.SummaryStatistics;

public class StatUtils {
	
	/**
	 * Calculates the Gaussian Normal Distribution
	 * @param x - The actual value
	 * @param m - Is the mean value
	 * @param stdev - Standard Deviation
	 * @return
	 */
	public static double calcNormalProbability(double x, double m, double stdev){
		return 1.0/(Math.sqrt(2.0*Math.PI)*stdev)*Math.exp(-Math.pow(x-m, 2)/(2*stdev*stdev));
	}
	
	/**
	 * Determine the variation Cooefficient
	 * @param val - Are the real values
	 * @param predVal - Are the values predicted 
	 * @return - The coefficient
	 */
	public static double coeffDetermination(double[] val, double[] predVal){
		SummaryStatistics statistics = new SummaryStatistics();
		for( int i=0; i<val.length;i++ ){
			statistics.addValue(val[i]);
		}
		double valMean = statistics.getMean();
		double deno = 0;
		double nume = 0;
		int valSize = val.length;
		for(int i=0; i<valSize; i++){
			nume += (val[i]-predVal[i])*(val[i]-predVal[i]);
			deno += (val[i]-valMean)*(val[i]-valMean);
		}
		double r = 1.0 - nume/deno;
		return r;
	}

}
