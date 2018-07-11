package com.axisframework.task;

import com.axisframework.event.Event;
import com.axisframework.event.EventBus;
import org.apache.log4j.Logger;

/**
 * Event bus consumer to consume event from event bus, and do internal business logic.
 * @author luoqi
 * @param <T>
 */
public class EventBusConsumer<T extends Event> implements Runnable {

	private static Logger logger = Logger.getLogger(EventBusConsumer.class);

	private EventBus<T> eventBus;
	private EventHandler<T> eventHandler;
	private int RETRY_TIME_THRESHOLD;

	public EventBusConsumer(EventBus<T> eventBus, EventHandler<T> eventHanlder, int retryTimes) {
		this.eventBus = eventBus;
		this.eventHandler = eventHanlder;
		this.RETRY_TIME_THRESHOLD = retryTimes;
	}

	@Override
	public void run() {
		while (true) {
			T event = null;
			try {
				event = eventBus.getData();
				eventHandler.handleEvent(event);
			} catch (Throwable e) {
				logger.error("[Task] Error occurs while handle event", e);
				if (event.getRetryTimes() < RETRY_TIME_THRESHOLD) {
					reDeliverEvent(event);
				} else {
					eventHandler.handleException(event);
				}
			}
		}
	}

	private void reDeliverEvent(T event) {
		try {
			int retryTimes = event.getRetryTimes() + 1;
			event.setRetryTimes(retryTimes);
			eventBus.putData(event);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
