package com.threequick.catering.kds.config;

import com.threequick.catering.api.kds.stall.task.StallTaskId;
import org.axonframework.commandhandling.model.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;

@Aggregate(repository = "stallTaskViewRepository")
public class StallTask {

    @AggregateIdentifier
    private StallTaskId stallTaskId;


}
