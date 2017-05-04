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
import org.atlas.apps.localization.domain.Position;

//TODO Need more implementation, Check if needed to be used.
public class KalmanPositionFilter {
	
	// discrete time interval
	private double dt = 0.1d;
	// position measurement noise (meter)
	private double measurementNoise = 10d;
	// acceleration noise (meter/sec^2)
	private double accelNoise = 0.2d;
	
	// A = [ 1 dt ]
	//     [ 0  1 ]
	private RealMatrix A = new Array2DRowRealMatrix(new double[][] { { 1, dt }, { 0, 1 } });
	
	// B = [ dt^2/2 ]
	//  [ dt     ]
	private RealMatrix B = new Array2DRowRealMatrix(new double[][] { { Math.pow(dt, 2d) / 2d }, { dt } });
	
	// H = [ 1 0 ]
	private RealMatrix H = new Array2DRowRealMatrix(new double[][] { { 1d, 0d } });

	//Pedestrian Starting Point Vector
	private RealVector x = new ArrayRealVector(new double[] { 2, 2 });
	
	// P0 = [ 1 1 ]
	//  [ 1 1 ]
	private RealMatrix P0 = new Array2DRowRealMatrix(new double[][] { { 1, 1 }, { 1, 1 } });
	
	private RealMatrix tmp = new Array2DRowRealMatrix(new double[][] {
		    { Math.pow(dt, 4d) / 4d, Math.pow(dt, 3d) / 2d },
		    { Math.pow(dt, 3d) / 2d, Math.pow(dt, 2d) } });
	
	// Q = [ dt^4/4 dt^3/2 ]
	//  [ dt^3/2 dt^2   ]
	private RealMatrix Q = tmp.scalarMultiply(Math.pow(accelNoise, 2));
	private RealMatrix R = new Array2DRowRealMatrix(new double[] { Math.pow(measurementNoise, 2) });
	
	//I do not think is wanted.
	private RealVector u = null;//new ArrayRealVector(new double[] { 0.1d });
	private KalmanFilter filter;
	
	public KalmanPositionFilter(){
		ProcessModel pm = new DefaultProcessModel(A, B, Q, x, P0);
		MeasurementModel mm = new DefaultMeasurementModel(H, R);
		this.filter = new KalmanFilter(pm, mm);
	}

	public Position calculatePosition(Position pos){
		this.filter.predict(this.u);
	    this.x = new ArrayRealVector(new double[] { pos.getX(), pos.getY() });
	    RealVector z = this.H.operate(this.x);
	    this.filter.correct(z);
	    return new Position(this.filter.getStateEstimation()[0], this.filter.getStateEstimation()[1]);
	}
	
}
