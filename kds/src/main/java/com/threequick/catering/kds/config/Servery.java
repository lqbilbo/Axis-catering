package com.threequick.catering.kds.config;

import com.threequick.catering.api.kds.servery.*;
import com.threequick.catering.api.kds.stall.StallId;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.model.AggregateIdentifier;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.spring.stereotype.Aggregate;

import java.util.HashMap;
import java.util.Map;

import static org.axonframework.commandhandling.model.AggregateLifecycle.apply;

@Aggregate(repository = "serveryViewRepository")
public class Servery {

    private static final long DEFAULT_STALL_VALUE = 0L;

    @AggregateIdentifier
    private ServeryId serveryId;
    private Map<StallId, Long> availableItems;
    private Map<StallId, Long> reservedItems;

    public Servery() {
    }

    @CommandHandler
    public Servery(CreateServeryCommand cmd) {
        apply(new ServeryCreatedEvent(cmd.getServeryId(), cmd.getPoiId(), cmd.getServeryName(),
                cmd.getRemark(), cmd.getOnline(), cmd.getOffline()));
    }

    @CommandHandler
    public Servery(AddStallsToServeryCommand cmd) {
        apply(new StallsAddedToServeryEvent(serveryId, cmd.getStallId(), cmd.getAmountOfStallsToAdd()));
    }

    @CommandHandler
    public Servery(RemoveStallsToServeryCommand cmd) {
        apply(new StallsRemovedFromServeryEvent(serveryId, cmd.getStallId()));
    }

    @EventSourcingHandler
    public void on(ServeryCreatedEvent event) {
        serveryId = event.getServeryId();
        availableItems = new HashMap<>();
        reservedItems = new HashMap<>();
    }

    @EventSourcingHandler
    public void on(StallsAddedToServeryEvent event) {
        long available = obtainCurrentAvailableItems(event.getStallId());
        availableItems.put(event.getStallId(), available + event.getAmountOfStallsToAdd());
    }

    @EventSourcingHandler
    public void on(StallsRemovedFromServeryEvent event) {
        availableItems.put(event.getStallId(), DEFAULT_STALL_VALUE);
    }

    private long obtainCurrentAvailableItems(StallId stallId) {
        return availableItems.getOrDefault(stallId, DEFAULT_STALL_VALUE);
    }

}
