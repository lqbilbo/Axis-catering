package com.threequick;

import org.apache.log4j.Logger;

import sun.misc.Signal;
import sun.misc.SignalHandler;

/**
 * Application shutdown handler which receives signal and do shutdown gracefully.
 * @author luoqi
 */
@SuppressWarnings("restriction")
public class AppShutdownHandler implements SignalHandler {

	private static Logger logger = Logger.getLogger(Application.class);

	@Override
	public void handle(Signal signal) {
		logger.info("[Application] Shutdown application gracefully");
		Application.shutdown();
		logger.info("[Application] Terminates currently running Java virtual machine");
		Runtime.getRuntime().exit(0);
	}

}
