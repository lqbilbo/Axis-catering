package com.threequick.process;

import java.util.concurrent.TimeUnit;

import com.threequick.event.Event;
import com.threequick.event.EventBus;
import com.threequick.message.Listener;
import com.threequick.message.MessageDelegate;
import com.threequick.task.EventBusConsumer;

/**
 * Class for synchronize processor. The framework will create a new processor for each type of message.
 * <p>One processor will contain one or more message delegates (message listeners), and one event bus. 
 * <p>One processor also contain one or more task to consume event from event bus.
 * @author luoqi
 * @param <T>
 */
public class Processor<T extends Event> {

	private ProcessorAware<T> ctx;
	private EventBus<T> eventBus;

	private MessageDelegate[] msgDelegates;
	private Thread[] eventBusTasks = null;

	public Processor(ProcessorAware<T> context, EventBus<T> eventBus) {
		this.ctx = context;
		this.eventBus = eventBus;
	}

	public boolean start() {
		return startHandle() & startListen();
	}

	public boolean startListen() {
		msgDelegates = new MessageDelegate[ctx.getConsumerSize()];
		for (int i = 0; i < ctx.getConsumerSize(); i++) {
			MessageDelegate msgDelegate = new MessageDelegate(ctx.getBroker(), ctx.getQueue(), ctx.getUsername(), ctx.getPassword());
			Listener<T> msgListener = new Listener<>(ctx.getConverter(), eventBus);
			msgDelegate.setListener(msgListener);
			msgDelegates[i] = msgDelegate;
		}
		return true;
	}

	public boolean startHandle() {
		eventBusTasks = new Thread[ctx.getTaskSize()];
		for (int i = 0; i < ctx.getTaskSize(); i++) {
			eventBusTasks[i] = new Thread(new EventBusConsumer<>(eventBus, ctx.getEventHandler(), ctx.getRetryTimes()));
			eventBusTasks[i].setName(this.getProcessorName() + "_consumer_" + i);
			eventBusTasks[i].start();
		}
		return true;
	}

	public boolean stop() {
		return stopListen() & stopHandle();
	}

	@SuppressWarnings("unchecked")
	public boolean stopListen() {
		for (int i = 0; i < ctx.getConsumerSize(); i++) {
			MessageDelegate msgDelegate = msgDelegates[i];
			if (msgDelegate != null) {
				((Listener<T>) msgDelegate.getListener()).stopListener();
			}
		}
		return true;
	}

	public boolean stopHandle() {
		waitForEventBus();
		return true;
	}

	private void waitForEventBus() {
		while (!eventBus.isEmpty()) {
			try {
				TimeUnit.SECONDS.sleep(1L);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public String getProcessorName() {
		return ctx.getProcessorName();
	}
}
