package org.atlas.apps.localization.executors;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExecutorServiceHolder {
	
	private static final Logger logger = LoggerFactory.getLogger(ExecutorServiceHolder.class);
	
	static int availableThreads = 1;
	static ExecutorService ex = null;

	private ExecutorServiceHolder(){};

	public static void setNThreads(int availableThreads){
		ExecutorServiceHolder.availableThreads = availableThreads;
		logger.info("Updating executor threads to "+availableThreads);
	}

	public static ExecutorService getExecutorService(){
		logger.info("Initialize Executor with "+availableThreads+" threads");
		if (ex == null) {
			ex = Executors.newFixedThreadPool(availableThreads);
		}
		return ex;
	}

	public static void destroy() {
		if (ex != null) {
			ex.shutdownNow();
		}
	}

}
