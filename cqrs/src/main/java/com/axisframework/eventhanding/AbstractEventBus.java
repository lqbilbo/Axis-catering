package com.axisframework.eventhanding;

import com.axisframework.common.Registration;
import com.axisframework.messaging.CurrentUnitOfWork;
import com.axisframework.messaging.UnitOfWork;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.function.Consumer;

import static com.axisframework.messaging.UnitOfWork.Phase.AFTER_COMMIT;
import static com.axisframework.messaging.UnitOfWork.Phase.COMMIT;
import static com.axisframework.messaging.UnitOfWork.Phase.PREPARE_COMMIT;

/**
 * {@link EventBus} 的实现,直接应对处理所有的发布事件到订阅时间方的处理器。
 * TODO:事件处理器可以进行异步处理或者使用 {@code TrackingToken} 开启一个事件流
 *
 * TODO:1.0不加入intercept与monitor逻辑,后续根据业务需求加入
 * @author luoqi
 * @see EventListener
 * @since 1.0
 */
public abstract class AbstractEventBus implements EventBus {

    private static final Logger logger = LoggerFactory.getLogger(AbstractEventBus.class);

    private final String eventsKey = this + "_EVENTS";
    private final Set<Consumer<List<? extends EventMessage<?>>>> eventProcessors = new CopyOnWriteArraySet<>();

    @Override
    public Registration subscribe(Consumer<List<? extends EventMessage<?>>> eventProcessor) {
        this.eventProcessors.add(eventProcessor);
        return () -> eventProcessors.remove(eventProcessor);
    }

    @Override
    public void publish(List<? extends EventMessage<?>> events) {

        if (CurrentUnitOfWork.isStarted()) {
            UnitOfWork<?> unitOfWork = CurrentUnitOfWork.get();
            //monitor ingest
            eventsQueue(unitOfWork).addAll(events);
        } else {
            try {
                //intercept
                commit(events);
                afterCommit(events);
                afterCommit(events);
                //monitor
            } catch (Exception e) {
                //monitor report failure
                throw e;
            }
        }

    }

    private List<EventMessage<?>> eventsQueue(UnitOfWork<?> unitOfWork) {
        return unitOfWork.getOrComputeResource(eventsKey, r -> {

            List<EventMessage<?>> eventQueue = new ArrayList<>();

            unitOfWork.onPrepareCommit(u -> {
                if (u.parent().isPresent() && !u.parent().get().phase().isAfter(PREPARE_COMMIT)) {
                    eventsQueue(u.parent().get()).addAll(eventQueue);
                } else {
                    int processedItems = eventQueue.size();
                    //intercept
                    //在发布预提交期间也会发布
                    while (processedItems < eventQueue.size()) {
                        //intercept
                        List<? extends EventMessage<?>> newMessages = new ArrayList<>();
                        processedItems = eventQueue.size();
                        doWithEvents(this::prepareCommit, newMessages);
                    }
                }
            });
            unitOfWork.onCommit(u -> {
                if (u.parent().isPresent() && !u.root().phase().isAfter(COMMIT)) {
                    u.root().onCommit(w -> doWithEvents(this::commit, eventQueue));
                } else {
                    doWithEvents(this::commit, eventQueue);
                }
            });
            unitOfWork.afterCommit(u -> {
                if (u.parent().isPresent() && !u.root().phase().isAfter(AFTER_COMMIT)) {
                    u.root().afterCommit(w -> doWithEvents(this::afterCommit, eventQueue));
                } else {
                    doWithEvents(this::afterCommit, eventQueue);
                }
            });
            unitOfWork.onCleanup(u -> u.resources().remove(eventsKey));
            return eventQueue;
        });
    }

    private void doWithEvents(Consumer<List<? extends EventMessage<?>>> eventsConsumer,
                              List<? extends EventMessage<?>> events) {
        eventsConsumer.accept(events);
    }

    protected void prepareCommit(List<? extends EventMessage<?>> events) {
        eventProcessors.forEach(eventProcessor -> eventProcessor.accept(events));
    }

    protected void commit(List<? extends EventMessage<?>> events) {
    }

    protected void afterCommit(List<? extends EventMessage<?>> events) {
    }
}
