package com.threequick.catering.kds.command;

import com.threequick.catering.api.kds.stall.task.StallTaskId;
import org.axonframework.commandhandling.model.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;

@Aggregate(repository = "stallTaskAggregateRepository")
public class StallTask {

    @AggregateIdentifier
    private StallTaskId stallTaskId;


}
