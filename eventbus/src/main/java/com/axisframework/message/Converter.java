package com.axisframework.message;

import com.axisframework.event.Event;

import javax.jms.Message;

/**
 * Converter to convert JMS message to internal event.
 * @author luoqi
 */
public abstract class Converter<T extends Event> {

	public abstract T convert(Message message);

}
