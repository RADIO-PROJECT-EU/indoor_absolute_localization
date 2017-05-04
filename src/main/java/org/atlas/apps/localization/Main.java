package org.atlas.apps.localization;

import org.atlas.apps.localization.executors.ExecutorServiceHolder;
import org.atlas.apps.localization.simulator.RssiSimulator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {

	private static final Logger logger = LoggerFactory.getLogger(Main.class);
	
	public static void main(String[] args) {
		logger.info("Starting up Indoor localization application");
		ExecutorServiceHolder.getExecutorService().execute(new Localizer());		
		new RssiSimulator();
	}

}
