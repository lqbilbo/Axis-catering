package com.threequick.catering.kds.config;

import com.threequick.catering.kds.command.Servery;
import com.threequick.catering.kds.command.ServeryTask;
import com.threequick.catering.kds.command.StallTask;
import org.axonframework.commandhandling.model.Repository;
import org.axonframework.common.caching.Cache;
import org.axonframework.common.transaction.Transaction;
import org.axonframework.eventsourcing.*;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KdsConfig {

    private static final int SNAPSHOT_THRESHOLD = 50;

    @Bean(name = "serveryAggregateRepository")
    public Repository<Servery> serveryAggregateRepository(AggregateFactory<Servery> serveryAggregateFactory,
                                                          EventStore eventStore,
                                                          Cache cache,
                                                          SnapshotTriggerDefinition snapshotTriggerDefinition) {
        return new CachingEventSourcingRepository<>(serveryAggregateFactory,
                                                    eventStore,
                                                    cache,
                                                    snapshotTriggerDefinition);
    }

    @Bean(name = "serveryTaskAggregateRepository")
    public Repository<Servery> serveryTaskAggregateRepository(AggregateFactory<ServeryTask> serveryTaskAggregateFactory,
                                                          EventStore eventStore,
                                                          Cache cache,
                                                          SnapshotTriggerDefinition snapshotTriggerDefinition) {
        return new CachingEventSourcingRepository<>(serveryTaskAggregateFactory,
                eventStore,
                cache,
                snapshotTriggerDefinition);
    }

    @Bean(name = "stallTaskAggregateRepository")
    public Repository<Servery> stallTaskAggregateRepository(AggregateFactory<StallTask> stallTaskAggregateFactory,
                                                          EventStore eventStore,
                                                          Cache cache,
                                                          SnapshotTriggerDefinition snapshotTriggerDefinition) {
        return new CachingEventSourcingRepository<>(stallTaskAggregateFactory,
                eventStore,
                cache,
                snapshotTriggerDefinition);
    }

    @Bean(name = "transactionAggregateRepository")
    public Repository<Transaction> transactionAggregateRepository(
            AggregateFactory<Transaction> transactionAggregateFactory,
            EventStore eventStore,
            Cache cache,
            SnapshotTriggerDefinition snapshotTriggerDefinition) {
        return new CachingEventSourcingRepository<>(transactionAggregateFactory,
                eventStore,
                cache,
                snapshotTriggerDefinition);
    }

    @Bean
    public SnapshotTriggerDefinition snapshotTriggerDefinition(Snapshotter snapshotter) {
        return new EventCountSnapshotTriggerDefinition(snapshotter, SNAPSHOT_THRESHOLD);
    }
}
