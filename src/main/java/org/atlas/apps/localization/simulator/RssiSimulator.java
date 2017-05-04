package org.atlas.apps.localization.simulator;

import java.util.List;

import org.atlas.apps.localization.beacons.Measurement;
import org.atlas.apps.localization.filters.BeaconsFilter;
import org.atlas.apps.localization.storage.Storage;
import org.atlas.apps.localization.utils.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RssiSimulator {
	
	private static final Logger logger = LoggerFactory.getLogger(RssiSimulator.class);
	
	public RssiSimulator(){
		logger.info("Starting RSSI Simulator for already gathered RSSI Measurements");
		this.loadSimulationSamples();
	}

	private void loadSimulationSamples() {
		List<Measurement> simulationData = FileUtils.getSampleData("storage/measurements/1m-d0-measurement.csv",1);
		try {
			for( Measurement measurement : simulationData ){
				if( BeaconsFilter.isLocalizationBeacon(measurement.getBeaconId()) ){
					Storage.addMeasurement(measurement);
				}else{
					logger.warn("Beacon ("+measurement.getBeaconId()+") is not configured to work with Localization Simulator");
				}
				Thread.sleep(100);
			}
		}catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
