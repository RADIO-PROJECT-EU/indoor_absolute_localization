package org.atlas.apps.localization.utils;

public class DataUtils {
	
	/**
	 * TODO What is this???
	 */
	private static int peakVoteThrethold = 3;
	
	/**
	 * https://en.wikipedia.org/wiki/Mean_of_circular_quantities
	 * @param angles - Received Signal Angle
	 * @return 
	 */
	public static double computeAngleAverage(double[] angles){
		int n = angles.length;
		double x=0;
		double y=0;
		for(int i=0; i<n; i++){
			double angle = angles[i];
			x += Math.cos(angle);
			y += Math.sin(angle);
		}
		x/=n;
		y/=n;
		double angleAve = Math.atan2(y, x);
		return angleAve;
	}
	
	/**
	 * Substract a constant value c from array x
	 * @param x - The array from which to substract all value
	 * @param c - The Value to substract from all values in the array.
	 * @return	- The resulted array
	 */
	public static double[] subtract(double[] x, double c){
		int n= x.length;
		double[] y = new double[n];
		for(int i=0; i<n; i++){
			y[i] = x[i] -c;
		}
		return y;
	}
	
	/**
	 * http://www.statisticshowto.com/moving-average/
	 * Computing moving average 
	 * @param amps - ????? (RSSI, Velocity, Position)
	 * @param winsize - The window size for the calculation
	 * @return - Array of Moving average values
	 */
	public static double[] computeMovingAverage(double[] amps, int winsize){
		int n = amps.length - winsize + 1;
		if(n < 1){
			winsize = amps.length;
			n = 1;
		}
		double[] ma = new double[n];
		for(int i=0; i<n ;i++){
			double mean = 0;
			for(int j=0; j<winsize; j++){
				mean += amps[i+j];
			}
			mean /= winsize;
			ma[i] = mean;
		}
		return ma;
	}
	
	/**
	 * http://www.eng.fsu.edu/~dommelen/courses/eml3100/aids/intpol/
	 * Calculate the linear interpolation
	 * @param x - Array to find the linear interpolation.
	 * @return
	 */
	public static double[] linearInterpolate(double[] x){
		int n = x.length;
		double[] intered = new double[n];
		for(int i=0; i<n ;i++){
			intered[i] = x[i];
		}

		double ref = x[0];
		int idx_ref = 0;
		double incre = 0;
		for(int i=0; i<n ;i++){
			double current = x[i];
			if(current > ref){
				double diff = current -ref;
				int dc = i-idx_ref;
				incre = diff/((double) dc);
				for(int j=idx_ref; j<i ;j++){
					intered[j] = intered[j] + incre* (double)(j-idx_ref);
				}
				ref = x[i];
				idx_ref = i;
			}
		}
		for(int i=idx_ref; i<n; i++) {
			intered[i] = intered[i] + incre * (double)(i-idx_ref);
		}
		return intered;
	}
	
	public static double[] computeSlidingWindowDeviation(double[] amps, int winsize){
		int n = amps.length - winsize + 1;
		if(n < 1){
			winsize = amps.length;
			n = 1;
		}
		double[] devs = new double[n];
		for(int i=0; i<n ;i++){
			double mean = 0;
			for(int j=0; j<winsize; j++){
				mean += amps[i+j];
			}
			mean /= winsize;
			double sqsum = 0;
			for(int j=0; j<winsize; j++){
				sqsum += (amps[i+j]-mean)*(amps[i+j]-mean);
			}
			double stdev = Math.sqrt(sqsum/winsize);
			devs[i] = stdev;
		}
		return devs;
	}

	/**
	 * TODO Understand
	 * @param deviations
	 * @param sigmaThresh
	 * @return
	 */
	public static double[] thresholding(double[] deviations, double sigmaThresh){
		int n = deviations.length;
		double[] isWalkings = new double[n];
		for(int i=0; i<n; i++){
			isWalkings[i] = (deviations[i] > sigmaThresh) ? 1:0;
		}
		return isWalkings;
	}

	/**
	 * TODO Understand
	 * @param x
	 * @return
	 */
	public static double[] derivative(double[] x){
		int n= x.length - 1;
		double[] dx = new double[n];
		for(int i=0; i<n; i++){
			dx[i] = x[i+1] - x[i];
		}
		return dx;
	}

	/**
	 * TODO Understand
	 * @param x
	 * @return
	 */
	public static double[] accumulate(double[] x){
		int n=x.length;
		double[] cum = new double[n];
		cum[0] = x[0];
		for(int i=1; i<n ; i++){
			cum[i] = cum[i-1]+x[i];
		}
		return cum;
	}

	/**
	 * TODO Understand
	 * @param x
	 * @param winsize
	 * @return
	 */
	public static double[] findLocalMaximum(double[] x, int winsize){
		int n = x.length;
		double[] isLocMax = new double[n];
		int[] locMaxVotes = new int[n];



		for(int i=0; i<n-winsize+1; i++){
			int idxMax = 0;
			double max = -Double.MAX_VALUE;
			for(int j=0; j<winsize; j++){
				int idx = i+j;
				if(max<x[idx]){
					idxMax = idx;
					max=x[idx];
				}
			}
			locMaxVotes[idxMax]++;
		}
		for(int i=0; i<n-winsize; i++){
			isLocMax[i] = (locMaxVotes[i] >= winsize) ? 1:0;
		}
		int max = 0;
		int maxi = 0;
		for(int i=n-winsize; i<n; i++) {
			if (max < locMaxVotes[i]) {
				max = locMaxVotes[i];
				maxi = i;
			}
		}
		if (maxi < n-1 && max > peakVoteThrethold) {
			isLocMax[maxi] = 1;
		}
		return isLocMax;
	}

}
