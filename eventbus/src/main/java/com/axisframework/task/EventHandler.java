package com.axisframework.task;

import com.axisframework.event.Event;

/**
 * Abstract event handler, users need to implement the template method.
 * @author luoqi
 */
public abstract class EventHandler<T extends Event> {

	/**
	 * Handle event, do business logic here.
	 * @param event
	 * @return
	 */
	public abstract boolean handleEvent(T event);

	/**
	 * Handler exception if event process is failed.
	 * @param event
	 * @return
	 */
	public abstract boolean handleException(T event);

}
