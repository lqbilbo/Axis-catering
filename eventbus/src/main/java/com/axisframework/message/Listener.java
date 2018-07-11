package com.axisframework.message;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.jms.Message;
import javax.jms.MessageListener;

import com.axisframework.event.Event;
import com.axisframework.event.EventBus;
import org.apache.log4j.Logger;

/**
 * Message listener to listen activeMQ.
 * It converts message to event, and deliver to event bus.
 * @author luoqi
 */
public class Listener<T extends Event> implements MessageListener {

	private static Logger logger = Logger.getLogger(Listener.class);
	private static CountDownLatch latch = new CountDownLatch(1);
	private AtomicBoolean stopped = new AtomicBoolean(Boolean.FALSE);

	private Converter<T> converter;
	private EventBus<T> eventBus;

	public Listener(Converter<T> converter, EventBus<T> eventBus) {
		this.converter = converter;
		this.eventBus = eventBus;
	}

	@Override
	public void onMessage(Message message) {
		try {
			checkListenerStatus();
			T event = converter.convert(message);
			eventBus.putData(event);
			message.acknowledge();
		} catch (Exception e) {
			logger.error("[Listener] Exception in message listener", e);
		}
	}

	private void checkListenerStatus() {
		if (stopped.get() == true) {
			try {
				latch.await();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public void stopListener() {
		this.stopped.compareAndSet(false, true);
	}

	public Converter<T> getConverter() {
		return converter;
	}

	public void setConverter(Converter<T> converter) {
		this.converter = converter;
	}

	public EventBus<T> getEventBus() {
		return eventBus;
	}

	public void setEventBus(EventBus<T> eventBus) {
		this.eventBus = eventBus;
	}

}
