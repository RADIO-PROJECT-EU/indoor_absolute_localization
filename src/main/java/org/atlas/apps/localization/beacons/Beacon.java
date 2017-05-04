package org.atlas.apps.localization.beacons;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.atlas.apps.localization.domain.Position;
import org.atlas.apps.localization.domain.Zone;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Beacon {
	
	/**
	 * Beacon Bluetooth Address
	 */
	private String address;
	private double location_x;
	private double location_y;
	private double height;
	private int max_rssi = -1;
	private int min_rssi = -100;
	private int major;
	private int minor;
	private Zone zone;
	
	@JsonProperty("company_id")
	private String companyId;
	
	private List<Double> measurements;
	
	/**
	 * Last known distance of the Beacon.
	 */
	private double distance;
	
	/**
	 * Last known RSSI value
	 */
	private int rssi;
	
	/**
	 * Beacon position in the building
	 */
	private Position position;
	
	/**
	 * Beacon Tx Power
	 */
	@JsonProperty("tx_power")
	private int txPower;
	private boolean active;
	
	public Beacon(){
		this.measurements = new ArrayList<Double>();
	}

	public double getDistance() {
		return distance;
	}

	public void setDistance(double distance) {
		this.distance = distance;
	}

	public int getRssi() {
		return rssi;
	}

	public void setRssi(int rssi) {
		this.rssi = rssi;
	}

	public Position getPosition() {
		return position;
	}

	public void setPosition(Position position) {
		this.position = position;
	}

	public int getTxPower() {
		return txPower;
	}

	public void setTxPower(int txPower) {
		this.txPower = txPower;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public double getHeight() {
		return height;
	}

	public void setHeight(double height) {
		this.height = height;
	}

	public int getMax_rssi() {
		return max_rssi;
	}

	public void setMax_rssi(int max_rssi) {
		this.max_rssi = max_rssi;
	}

	public int getMin_rssi() {
		return min_rssi;
	}

	public void setMin_rssi(int min_rssi) {
		this.min_rssi = min_rssi;
	}

	public int getMajor() {
		return major;
	}

	public void setMajor(int major) {
		this.major = major;
	}

	public int getMinor() {
		return minor;
	}

	public void setMinor(int minor) {
		this.minor = minor;
	}

	public double getLocation_x() {
		return location_x;
	}

	public void setLocation_x(double location_x) {
		this.location_x = location_x;
	}

	public double getLocation_y() {
		return location_y;
	}

	public void setLocation_y(double location_y) {
		this.location_y = location_y;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public List<Double> getMeasurements() {
		Collections.sort(this.measurements);
		Collections.reverse(this.measurements);
		return this.measurements;
	}

	public double[] getMeasurementsAsArray(){
		Collections.sort(this.measurements);
		Collections.reverse(this.measurements);
		double[] d = new double[this.measurements.size()];
		int cnt = 0;
		for( double rssi : this.measurements ){
			d[cnt] = rssi;
		}
		return d;
	}
	
	public void setMeasurements(List<Double> measurements) {
		this.measurements = measurements;
	}
	
	public void addMeasurement(double rssi){
		this.measurements.add(rssi);
	}
	
	public void clearMeasurements(){
		this.measurements.clear();
	}
	
	public Zone getZone() {
		return zone;
	}

	public void setZone(Zone zone) {
		this.zone = zone;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}
}
