package com.threequick.catering.kds.command;

import com.threequick.catering.api.kds.stall.StallId;
import com.threequick.catering.api.kds.stall.task.*;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.model.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;

import static org.axonframework.commandhandling.model.AggregateLifecycle.apply;

@Aggregate(repository = "stallTaskAggregateRepository")
public class StallTask {

    @AggregateIdentifier
    private StallTaskId stallTaskId;
    private StallId stallId;    //一个档口任务对应一个档口

    @SuppressWarnings("UnusedDeclaration")
    public StallTask() {
    }

    @CommandHandler
    public StallTask(CreateStallTaskCommand cmd) {
        apply(new StallTaskCreatedEvent(cmd.getStallTaskId(), cmd.getServeryTaskId(), cmd.getStallTaskName(),
                cmd.getStallId(), cmd.getSeq(), cmd.getEstimateTime()));
    }

    @CommandHandler
    public void handle(WriteStallTaskLogCommand cmd) {
        apply(new StallTaskLogWritedEvent(cmd.getStallTaskId(), cmd.getPoiId(),
                cmd.getOrderId(), cmd.getDeliveryId(),
                cmd.getServeryId(), cmd.getStallId(),
                cmd.getStatus(), cmd.getCreateTime()));
    }

    @CommandHandler
    public void handle(PrintTaskNoteCommand cmd) {
        apply(new TaskNotePrintedEvent(cmd.getStallTaskId(), cmd.getPoiId(),
                cmd.getOrderId(), cmd.getStallTaskName(), cmd.getRemark()));
    }
}
