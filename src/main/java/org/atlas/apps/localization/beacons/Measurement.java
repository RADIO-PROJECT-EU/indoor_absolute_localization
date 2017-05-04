package org.atlas.apps.localization.beacons;

public class Measurement implements Comparable<Measurement>{

	/**
	 * Is MAC of the Beacon, For performance reasons hold the beaconId and not the Beacon Object.
	 */
	private String beaconId;
	private int rssi;
	private long timestamp;
	
	public Measurement(){
		this.timestamp = System.currentTimeMillis();
	};
	
	public Measurement(int rssi){
		this.rssi = rssi;
		this.timestamp = System.currentTimeMillis();
	}
	
	public Measurement(String beaconId, int rssi){
		this.beaconId = beaconId;
		this.rssi = rssi;
		this.timestamp = System.currentTimeMillis();
	}
	
	@Override
	public int compareTo(Measurement measurement) {
		return measurement.getRssi() - this.rssi;
	}

	public int getRssi() {
		return rssi;
	}

	public void setRssi(int rssi) {
		this.rssi = rssi;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	public String getBeaconId() {
		return beaconId;
	}

	public void setBeaconId(String beaconId) {
		this.beaconId = beaconId;
	}

	@Override
	public String toString() {
		return "Measurement [beaconId=" + beaconId + ", rssi=" + rssi
				+ ", timestamp=" + timestamp + "]";
	}
	
}
