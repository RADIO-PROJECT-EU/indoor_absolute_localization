package org.atlas.apps.localization.filters.impl;

import org.apache.commons.math3.filter.DefaultMeasurementModel;
import org.apache.commons.math3.filter.DefaultProcessModel;
import org.apache.commons.math3.filter.KalmanFilter;
import org.apache.commons.math3.filter.MeasurementModel;
import org.apache.commons.math3.filter.ProcessModel;
import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.RealVector;

public class KalmanRssiFilter {
	
	private double rssiConstant = 10d;
	private double measurementNoise = 0.1d;
	private double processNoise = 1e-5d;

	private RealMatrix A = new Array2DRowRealMatrix(new double[] { 1d });
	private RealMatrix B = null;
	private RealMatrix H = new Array2DRowRealMatrix(new double[] { 1d });
	private RealVector x = new ArrayRealVector(new double[] { rssiConstant });
	private RealMatrix Q = new Array2DRowRealMatrix(new double[] { processNoise });
	private RealMatrix P0 = new Array2DRowRealMatrix(new double[] { 1d });
	private RealMatrix R = new Array2DRowRealMatrix(new double[] { measurementNoise });
	private KalmanFilter filter;
	
	public KalmanRssiFilter(){
		ProcessModel pm = new DefaultProcessModel(this.A, this.B, this.Q, this.x, this.P0);
		MeasurementModel mm = new DefaultMeasurementModel(this.H, this.R);
		this.filter = new KalmanFilter(pm, mm);
	}

	public double calculateRSSI(double rssi){
		this.filter.predict();
		this.filter.correct(new ArrayRealVector(new double[] { rssi }));
	    return this.filter.getStateEstimation()[0];
	}
	
}
