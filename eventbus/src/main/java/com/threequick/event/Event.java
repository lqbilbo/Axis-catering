package com.threequick.event;

/**
 * High level event wrapper in internal event bus.
 * All customized event must inherit this abstract Event.
 * @author luoqi
 * @see com.threequick.process.ProcessorAware#getRetryTimes()
 */
public abstract class Event {

	/**
	 * Retry times for current event, will not retry if reach a threshold 
	 * which is specified in {@code ProcessorAware}.
	 */
	private int retryTimes;

	public int getRetryTimes() {
		return retryTimes;
	}

	public void setRetryTimes(int retryTimes) {
		this.retryTimes = retryTimes;
	}

}
