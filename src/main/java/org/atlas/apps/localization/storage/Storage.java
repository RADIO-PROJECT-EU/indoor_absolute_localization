package org.atlas.apps.localization.storage;

import java.util.ArrayList;
import java.util.concurrent.LinkedBlockingQueue;

import org.atlas.apps.localization.beacons.Measurement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Storage {
	
	private static final Logger logger = LoggerFactory.getLogger(Storage.class);
	private static LinkedBlockingQueue<Measurement> measurementSamples;
	
	static{
		logger.info("Initializing Storage (LinkedBlockingQueue) for RSSI Samples");
		measurementSamples = new LinkedBlockingQueue<Measurement>(10000);
	}
	
	/**
	 * Adding a new measurement to the Queue
	 * @param measurement
	 */
	public static void addMeasurement(Measurement measurement){
		logger.debug(measurement.toString());
		measurementSamples.add(measurement);
	}
	
	/**
	 * Return all the availables elements from the queue and return them for further parsing.
	 */
	public static ArrayList<Measurement> getMeasurements(){
		ArrayList<Measurement> measurements = new ArrayList<Measurement>();
		int transfered = measurementSamples.drainTo(measurements);
		logger.debug("Total measurements transferred main storage to the observable storage are " + transfered);
		return measurements;
	}

}
