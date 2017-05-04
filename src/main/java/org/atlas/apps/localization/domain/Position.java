package org.atlas.apps.localization.domain;

import java.sql.Timestamp;

/**
 * Representation of 2D poistion
 * @author esda
 *
 */
public class Position {

	private double x;
	private double y;
	private double z;
	private Timestamp timestamp;
	
	/**
	 * Default constrcutor
	 */
	public Position(){
        this.timestamp = new Timestamp(System.currentTimeMillis());
	}
	
	/**
	 * Constructor with known positions
	 * @param x - Coordinate X
	 * @param y - Coordinate Y
	 */
	public Position(double x, double y){
		this.timestamp = new Timestamp(System.currentTimeMillis());
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Constructor with known positions, on an old time
	 * @param x - Coordinate X
	 * @param y - Coordinate Y
	 */
	public Position(double x, double y, Timestamp timestamp){
		this.timestamp = timestamp;
		this.x = x;
		this.y = y;
	}
	
	public double getX() {
		return x;
	}
	public void setX(double x) {
		this.x = x;
	}
	public double getY() {
		return y;
	}
	public void setY(double y) {
		this.y = y;
	}
	public Timestamp getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}
	
	public double[] toArray(){
		return new double[]{this.x, this.y};
	}
	
	public long getTimestampToMilliseconds(){
		return this.timestamp.getTime();
	}
	
	public long getTimestampToSeconds(){
		return this.getTimestampToMilliseconds() / 1000;
	}

	public double getZ() {
		return z;
	}

	public void setZ(double z) {
		this.z = z;
	}

	@Override
	public String toString() {
		return "Position [x=" + x + ", y=" + y + ", z=" + z + ", timestamp="
				+ timestamp + "]";
	}
	
	public String toUnityPayload(){
		return "RobotMove,"+this.x+","+this.y+","+this.z;
	}
}
