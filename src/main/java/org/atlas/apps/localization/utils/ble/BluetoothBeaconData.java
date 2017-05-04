package org.atlas.apps.localization.utils.ble;


import java.io.Serializable;

public class BluetoothBeaconData implements Serializable{
	
	public BluetoothBeaconData(){}

    /**
	 * 
	 */
	private static final long serialVersionUID = 2599792661427599383L;
	public String uuid;
    public String address;
    public int major, minor;
    public int rssi;
    public int txpower;

    @Override
    public String toString() {
        return "BluetoothBeaconData [uuid=" + this.uuid + ", address=" + this.address + ", major=" + this.major
                + ", minor=" + this.minor + ", rssi=" + this.rssi + ", txpower=" + this.txpower + "]";
    }

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
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

	public int getRssi() {
		return rssi;
	}

	public void setRssi(int rssi) {
		this.rssi = rssi;
	}

	public int getTxpower() {
		return txpower;
	}

	public void setTxpower(int txpower) {
		this.txpower = txpower;
	}
}
