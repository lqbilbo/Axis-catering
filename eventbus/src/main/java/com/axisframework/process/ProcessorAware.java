package com.axisframework.process;

import com.axisframework.event.Event;
import com.axisframework.message.Converter;
import com.axisframework.task.EventHandler;

/**
 * Synchronize processor aware to provide a extension point for user custom processor settings.
 * @author luoqi
 */
public interface ProcessorAware<T extends Event> {

	/**
	 * Get sync processor name.
	 * @return
	 */
    String getProcessorName();

	/**
	 * Get message->event converter.
	 * @return
	 */
    Converter<T> getConverter();

	/**
	 * Get event handler which is the core component of sync.
	 * Users should put business logic here.
	 * @return
	 */
    EventHandler<T> getEventHandler();

	/**
	 * Get message consumer size, 
	 * it determines the message consume concurrency level. 
	 * @return
	 */
    int getConsumerSize();

	/**
	 * Get message broker URL, for ActiveMQ, it typically uses failover transport protocol.
	 * @return
	 */
    String getBroker();

	/**
	 * Get message queue name.
	 * @return
	 */
    String getQueue();

	/**
	 * Get message broker user name.
	 * @return
	 */
    String getUsername();

	/**
	 * Get messgae broker password.
	 * @return
	 */
    String getPassword();

	/**
	 * Get event bus capacity,
	 * it determines the capacity of event bus buffer. 
	 * @return
	 */
    int getEventBusCapacity();

	/**
	 * Get event bus consumers size,
	 * it determines the event process concurrency level.
	 * @return
	 */
    int getTaskSize();

	/**
	 * Get retry times threshold if the event process is failed.
	 * @return
	 */
    int getRetryTimes();

}
