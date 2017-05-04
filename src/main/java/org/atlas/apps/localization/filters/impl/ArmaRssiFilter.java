package org.atlas.apps.localization.filters.impl;

import org.atlas.apps.localization.filters.RssiFilter;

/**
 * This filter calculates its rssi on base of an auto regressive moving average (ARMA)
 * It needs only the current value to do this; the general formula is  n(t) = n(t-1) - c * (n(t-1) - n(t))
 * where c is a coefficient, that denotes the smoothness - the lower the value, the smoother the average
 * Note: a smoother average needs longer to "settle down"
 * Note: For signals, that change rather frequently (say, 1Hz or faster) and tend to vary more a recommended
 *       value would be 0,1 (that means the actual value is changed by 10% of the difference between the
 *       actual measurement and the actual average)
 *       For signals at lower rates (10Hz) a value of 0.25 to 0.5 would be appropriate
 */
public class ArmaRssiFilter implements RssiFilter {

    private double DEFAULT_ARMA_SPEED = 0.1;     //How likely is it that the RSSI value changes?
                                                        //Note: the more unlikely, the higher can that value be
                                                        //      also, the lower the (expected) sending frequency,
                                                        //      the higher should that value be
    private double armaMeasurement;
    private double armaSpeed = 0.1;
    private boolean isInitialized = false;

    public ArmaRssiFilter() {
        this.armaSpeed = DEFAULT_ARMA_SPEED;
    }

    @Override
    public void addMeasurement(double rssi) {
        if (!isInitialized) {
            armaMeasurement = rssi;
            isInitialized = true;
        };
        armaMeasurement = Double.valueOf(armaMeasurement - armaSpeed * (armaMeasurement - rssi)).intValue();
    }

    @Override
    public double calculateRssi() {
        return armaMeasurement;
    }

	@Override
	public void resetFilter() {
		this.isInitialized = false;
		armaMeasurement = 0;
	}
}
