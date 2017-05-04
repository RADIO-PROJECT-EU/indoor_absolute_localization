package org.atlas.apps.localization.utils.ble;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class BluetoothUtil {

    /**
     * Parse EIR data from a BLE advertising report,
     * extracting UUID, major and minor number.
     *
     * See Bluetooth Core 4.0; 8 EXTENDED INQUIRY RESPONSE DATA FORMAT
     *
     * @param b
     *            Array containing EIR data
     * @param i
     *            Index of first byte of EIR data
     * @return BeaconInfo or null if no beacon data present
     */
    private static BluetoothBeaconData parseEIRData(byte[] b, int payloadPtr, int len, String companyName) {

    	//System.out.println("Get Beacon data (length: "+b.length+") for company+ " + companyName);
    	//System.out.println("payloadPtr: " + payloadPtr);
    //	System.out.println("len: " + len);
        for (int ptr = payloadPtr; ptr < payloadPtr + len;) {

            int structSize = b[ptr];
            if (structSize == 0) {
                break;
            }

            byte dataType = b[ptr + 1];

            if (dataType == (byte) 0xFF) { // Data-Type: Manufacturer-Specific

                int prefixPtr = ptr + 2;
                byte[] prefix = new byte[4];
                prefix[0] = new Integer(Integer.parseInt(companyName.substring(2, 4), 16)).byteValue();
                prefix[1] = new Integer(Integer.parseInt(companyName.substring(0, 2), 16)).byteValue();
                prefix[2] = 0x02;
                prefix[3] = 0x15;

                if (Arrays.equals(prefix, Arrays.copyOfRange(b, prefixPtr, prefixPtr + prefix.length))) {
                    BluetoothBeaconData bi = new BluetoothBeaconData();

                    int uuidPtr = ptr + 2 + prefix.length;
                    int majorPtr = uuidPtr + 16;
                    int minorPtr = uuidPtr + 18;

                    bi.uuid = "";
                    for (byte ub : Arrays.copyOfRange(b, uuidPtr, majorPtr)) {
                        bi.uuid += String.format("%02X", ub);
                    }

                    int majorl = b[majorPtr + 1] & 0xFF;
                    int majorh = b[majorPtr] & 0xFF;
                    int minorl = b[minorPtr + 1] & 0xFF;
                    int minorh = b[minorPtr] & 0xFF;
                    bi.major = majorh << 8 | majorl;
                    bi.minor = minorh << 8 | minorl;
                    bi.txpower = b[minorPtr + 2];
                    // Can't fill this in from here
                    bi.address = "";
                    return bi;
                }
            }

            ptr += structSize + 1;
        }

        return null;
    }


    /**
     * Parse BLE beacons out of an HCL LE Advertising Report Event
     *
     * See Bluetooth Core 4.0; 7.7.65.2 LE Advertising Report Event
     *
     * @param b
     * @return
     */
    public static List<BluetoothBeaconData> parseLEAdvertisingReport(byte[] b, String companyName) {

    	//System.out.println("Parse advertisment data(length: "+b.length+") for company+ " + companyName);
        List<BluetoothBeaconData> results = new LinkedList<BluetoothBeaconData>();

        // Packet Type: Event OR Event Type: LE Advertisement Report
        if (b[0] != 0x04 || b[1] != 0x3E) {
            return results;
        }

        // LE Advertisement Subevent Code: 0x02
        if (b[3] != 0x02) {
            return results;
        }

        int numReports = b[4];

        int ptr = 5;
        for (int i = 0; i < numReports; i++) {
            ptr++;
            ptr++;

            // Extract remote address
            String address = String.format("%02X:%02X:%02X:%02X:%02X:%02X", b[ptr + 5], b[ptr + 4], b[ptr + 3],
                    b[ptr + 2], b[ptr + 1], b[ptr + 0]);
            ptr += 6;

            int len = b[ptr++];

            BluetoothBeaconData bi = parseEIRData(b, ptr, len, companyName);
            if (bi != null) {

                bi.address = address;
                bi.rssi = b[ptr + len];
                results.add(bi);
            }

            ptr += len;
        }
     //   System.out.println("Bluetooth beacons found "+results.size());
        return results;
    }
}
