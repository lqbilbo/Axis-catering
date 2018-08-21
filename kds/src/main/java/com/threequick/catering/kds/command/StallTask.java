package com.threequick.catering.kds.command;

import com.threequick.catering.api.kds.stall.StallId;
import com.threequick.catering.api.kds.stall.task.CreateStallTaskCommand;
import com.threequick.catering.api.kds.stall.task.StallTaskCreatedEvent;
import com.threequick.catering.api.kds.stall.task.StallTaskId;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.model.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;

import static org.axonframework.commandhandling.model.AggregateLifecycle.apply;

@Aggregate(repository = "stallTaskAggregateRepository")
public class StallTask {

    @AggregateIdentifier
    private StallTaskId stallTaskId;
    private StallId stallId;    //一个档口任务对应一个档口

    @CommandHandler
    public StallTask(CreateStallTaskCommand cmd) {
        apply(new StallTaskCreatedEvent(cmd.getStallTaskId(), cmd.getServeryTaskId(), cmd.getStallId(),
                cmd.getSeq(), cmd.getEstimateTime()));
    }


}
