package org.atlas.apps.localization.ui;

import java.util.HashMap;

import org.atlas.apps.localization.beacons.Beacon;
import org.atlas.apps.localization.exceptions.RoomMapException;

public class Room {

	private double granulatiry = 0.25;
	private HashMap<String, RoomObject> roomObjects;
	private HashMap<String, Beacon> beacons;
	private double squareMeters;
	private double xFrom;
	private double xTo;
	private double yFrom;
	private double yTo;
	
	public Room(double[] points) throws RoomMapException{
		if( points.length < 4 ){
			throw new RoomMapException("Unable to initialize Room Map, Room points must be at least 4");
		}
		this.xFrom = points[0];
		this.xTo = points[1];
		this.yFrom = points[2];
		this.yTo = points[3];
		this.calculateSquareMeters();
	}
	
	public Room(double xFrom, double xTo, double yFrom, double yTo){
		this.xFrom = xFrom;
		this.xTo = xTo;
		this.yFrom = yFrom;
		this.yTo = yTo;
		this.calculateSquareMeters();
	}
	
	public HashMap<String, RoomObject> getRoomObjects() {
		return roomObjects;
	}
	public void setRoomObjects(HashMap<String, RoomObject> roomObjects) {
		this.roomObjects = roomObjects;
	}
	public double getSquareMeters() {
		return squareMeters;
	}
	public void setSquareMeters(double squareMeters) {
		this.squareMeters = squareMeters;
	}
	public double getxFrom() {
		return xFrom;
	}
	public void setxFrom(double xFrom) {
		this.xFrom = xFrom;
	}
	public double getxTo() {
		return xTo;
	}
	public void setxTo(double xTo) {
		this.xTo = xTo;
	}
	public double getyFrom() {
		return yFrom;
	}
	public void setyFrom(double yFrom) {
		this.yFrom = yFrom;
	}
	public double getyTo() {
		return yTo;
	}
	public void setyTo(double yTo) {
		this.yTo = yTo;
	}
	
	private void calculateSquareMeters(){
		double distX = this.xTo - this.xFrom;
		double distY = this.yTo - this.yFrom;
		this.setSquareMeters(distX*distY);
	}

	public double getGranulatiry() {
		return granulatiry;
	}

	public void setGranulatiry(double granulatiry) {
		this.granulatiry = granulatiry;
	}

	public HashMap<String, Beacon> getBeacons() {
		return beacons;
	}

	public void setBeacons(HashMap<String, Beacon> beacons) {
		this.beacons = beacons;
	}
	
}
