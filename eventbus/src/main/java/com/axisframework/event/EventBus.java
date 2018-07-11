package com.axisframework.event;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import org.apache.log4j.Logger;

/**
 * Internal event bus to provide buffer for message, which is a typical producer-consumer pattern.
 * <p>Producer will listen message middleware, then convert to event and deliver to event bus.
 * <p>Consumer will concurrently fetch event from event bus and process business logic.
 * <p>The underline of event bus is blocking queue, so put event will be blocked if queue is full, 
 * and take event is blocked if queue is empty.
 * @author luoqi
 * @param <T>
 */
public class EventBus<T extends Event> {

	private static Logger logger = Logger.getLogger(EventBus.class);
	private BlockingQueue<T> dataQueue;

	public EventBus(int capacity) {
		dataQueue = new ArrayBlockingQueue<>(capacity);
	}

	public void putData(T event) {
		try {
			dataQueue.put(event);
		} catch (InterruptedException e) {
			logger.error("[EventBus] Error while put data to event bus", e);
		}
	}

	public T getData() {
		try {
			return dataQueue.take();
		} catch (InterruptedException e) {
			logger.error("[EventBus] Error while take data from event bus", e);
		}
		return null;
	}

	public boolean isEmpty() {
		return dataQueue.isEmpty();
	}

}
