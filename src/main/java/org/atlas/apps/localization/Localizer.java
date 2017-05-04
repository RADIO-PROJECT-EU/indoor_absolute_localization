package org.atlas.apps.localization;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.math3.stat.StatUtils;
import org.atlas.apps.localization.beacons.Beacon;
import org.atlas.apps.localization.beacons.Measurement;
import org.atlas.apps.localization.connectors.MqttConnector;
import org.atlas.apps.localization.domain.Position;
import org.atlas.apps.localization.domain.Zone;
import org.atlas.apps.localization.estimators.MapBasedDistanceEstimator;
import org.atlas.apps.localization.estimators.PositionEstimator;
import org.atlas.apps.localization.filters.BeaconsFilter;
import org.atlas.apps.localization.filters.impl.ArmaRssiFilter;
import org.atlas.apps.localization.filters.impl.KalmanPositionFilter;
import org.atlas.apps.localization.filters.impl.KalmanRssiFilter;
import org.atlas.apps.localization.storage.Storage;
import org.atlas.apps.localization.utils.LocalizationUtils;
import org.atlas.apps.localization.utils.MapUtils;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Localizer implements Runnable{
	
	private static final Logger logger = LoggerFactory.getLogger(Localizer.class);
	private final long SLEEPING_PERIOD = 1000;
	
	//http://www.westernite.org/datacollectionfund/2005/psu_ped_summary.pdf
	//http://www.usroads.com/journals/p/rej/9710/re971001.htm
	private final double DEFAULT_AVERAGE_PEDESTRIAN_WALK_SPEED = 0.4;// m/s
	private final double MIN_PEDESTRIAN_WALK_SPEED = 0.1;// m/s
	private final double MAX_PEDESTRIAN_WALK_SPEED = 0.7;// m/s
	
	private ArmaRssiFilter filter;
	private KalmanRssiFilter kalman;
	private KalmanPositionFilter kalmanPos;
	private MapBasedDistanceEstimator estimator;
	private PositionEstimator positionEstimator;
	private MqttClient client;
	private HashMap<String, Beacon> beaconsMap;
	private List<Zone> locZones;
	private Position initialPos;
	private Position currentPosition;
	private ArrayList<Position> knownPositions;
	
	public Localizer(){
		this.initialPos = new Position(0,0);
		this.currentPosition = this.initialPos;
		logger.info("Starting Localizer, with initial Position on (0,0)");
		this.initialSubModules();
	}
	
	/**
	 * Constructor with initial position;
	 */
	public Localizer(Position initialPos){
		this.initialPos = initialPos;
		this.currentPosition = this.initialPos;
		logger.info("Starting Localizer, with initial Position on ("+this.currentPosition.getX()+","+this.currentPosition.getY()+")");
		this.initialSubModules();
	}
	
	/**
	 * Initialize all the corresponding modules for the Localizer
	 */
	private void initialSubModules(){
		this.filter = new ArmaRssiFilter();
		this.kalman = new KalmanRssiFilter();
		this.kalmanPos = new KalmanPositionFilter();
		this.estimator = new MapBasedDistanceEstimator();
		this.positionEstimator = new PositionEstimator();
		this.client = new MqttConnector(false).getClient();
		this.beaconsMap = BeaconsFilter.getBeacons();
		this.knownPositions = new ArrayList<Position>();
		this.knownPositions.add(this.currentPosition);
		this.locZones = MapUtils.loadZones();
		for( Zone zone : this.locZones ){
			System.out.println(zone);
		}
	}

	@Override
	public void run() {
		
		double filtered = 0;
		double prediction = 0;
		while(true){
			try {
				Thread.sleep(SLEEPING_PERIOD);
			} catch (InterruptedException e) {
				logger.error("Unable to sleep thread",e);
			}
			
			ArrayList<Measurement> measurements = Storage.getMeasurements();
			if( measurements.size() == 0 ) continue;
			for( Measurement measurement : measurements ){
				this.beaconsMap.get(measurement.getBeaconId()).addMeasurement(measurement.getRssi());
			}
			
			filtered = 0;
			prediction = 0;
			for (Map.Entry<String, Beacon> entry : this.beaconsMap.entrySet()) {
			    Beacon curBeacon = entry.getValue();
			    for( double rssi : curBeacon.getMeasurements() ){
			    	this.filter.addMeasurement(rssi);
			    	filtered = this.filter.calculateRssi();
			    	prediction = this.kalman.calculateRSSI(filtered);
				    curBeacon.setRssi((int)prediction);
			    }
			    this.filter.resetFilter();
			}			
			
			for (Map.Entry<String, Beacon> entry : this.beaconsMap.entrySet()) {
				Beacon curBeacon = entry.getValue();
				double distance = this.estimator.calculateDistance(curBeacon.getTxPower(), curBeacon.getRssi());
				curBeacon.setDistance(distance);
				logger.info("Beacon("+curBeacon.getAddress()+") : Distance calculated "+curBeacon.getDistance()+" with mean RSSI: " + curBeacon.getRssi());
			}
			
			this.positionEstimator.setBeacons(this.beaconsMap);
			Position pos = this.positionEstimator.getPosition();
			this.updatePosition(this.kalmanPos.calculatePosition(pos));			
			
			for (Map.Entry<String, Beacon> entry : this.beaconsMap.entrySet()) {
				entry.getValue().clearMeasurements();
			}
		}
	}
	
	/**
	 * Private Update position
	 */
	private void updatePosition(Position pos){
		logger.info("At time: "+pos.getTimestampToSeconds()+", Position: ("+pos.getX()+","+pos.getY()+")");
		double speed = LocalizationUtils.calculateSpeed(this.currentPosition, pos, (pos.getTimestampToSeconds()-this.currentPosition.getTimestampToSeconds()));
		logger.info("Speed: " + speed);
		this.currentPosition = pos;
		this.knownPositions.add(pos);
	}

}
