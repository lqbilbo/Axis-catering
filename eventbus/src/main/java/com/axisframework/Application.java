package com.axisframework;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import com.axisframework.event.Event;
import com.axisframework.event.EventBus;
import com.axisframework.process.Processor;
import com.axisframework.process.ProcessorAware;
import org.apache.log4j.Logger;

import sun.misc.Signal;

/**
 * Application bootstrap entry point. It provides functions to:
 * <ul>
 * <li> Register, startup and shutdown all configured processors.
 * <li> Shutdown all configured processors gracefully.
 * </ul>
 * @author luoqi
 */
@SuppressWarnings("restriction")
public class Application {

	private static Logger logger = Logger.getLogger(Application.class);

	@SuppressWarnings("rawtypes")
	private static List<ProcessorAware> processorContexts = new ArrayList<>();
	@SuppressWarnings("rawtypes")
	private static List<Processor> processors = new ArrayList<>();
	private static CountDownLatch latch = new CountDownLatch(1);

	public static void startup() {
		long start = System.currentTimeMillis();
		logger.info("[Application] Start system time is " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
		startProcessors();
		logger.info("[Application] Start system successfully, cost=" + (System.currentTimeMillis() - start) + "ms");
		waitForShutdown();
	}

	public static void shutdown() {
		stopProcessors();
	}

	public static <T extends Event> void registerProcessor(ProcessorAware<T> ctx) {
		processorContexts.add(ctx);
	}

	@SuppressWarnings("unchecked")
	private static <T extends Event> void startProcessors() {
		for (ProcessorAware<T> ctx : processorContexts) {
			EventBus<T> eventBus = new EventBus<>(ctx.getEventBusCapacity());
			Processor<T> processor = new Processor<>(ctx, eventBus);
			processor.start();
			processors.add(processor);
			logger.info("[Application] Start processor " + processor.getProcessorName() + " successfully");
		}
	}

	private static void waitForShutdown() {
		registerSignalHandler();
		registerShutdownHook();
		try {
			latch.await();
		} catch (InterruptedException e) {
			logger.error("[Application] Error while waiting for shutdown");
		}
	}

	private static void registerSignalHandler() {
		Signal signal = new Signal(System.getProperties().getProperty("os.name").toLowerCase().startsWith("win") ? "INT" : "USR2");
		Signal.handle(signal, new AppShutdownHandler());
	}

	private static void registerShutdownHook() {
		Runtime.getRuntime().addShutdownHook(new Thread("Shutdown hook") {
			@Override
			public void run() {
				latch.countDown();
			}
		});
	}

	@SuppressWarnings("unchecked")
	private static <T extends Event> void stopProcessors() {
		for (Processor<T> processor : processors) {
			logger.info("[Application] Stop processor " + processor.getProcessorName() + " by sending stop signal...");
			boolean stopResult = processor.stop();
			String stopStr = "[Application] Stop processor " + processor.getProcessorName() + (stopResult ? "successfully" : "failed");
			logger.info(stopStr);
		}
	}

}
