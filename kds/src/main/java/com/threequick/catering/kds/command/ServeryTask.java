package com.threequick.catering.kds.command;

import com.threequick.catering.api.kds.servery.ServeryId;
import com.threequick.catering.api.kds.servery.task.*;
import com.threequick.catering.api.kds.stall.task.StallTaskId;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.model.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;

import java.util.List;

import static org.axonframework.commandhandling.model.AggregateLifecycle.apply;

/**
 * @author luoqi04
 * @version $Id: ServeryTask.java, v 0.1 2018/8/16 下午11:20 luoqi Exp $
 */
@Aggregate(repository = "serveryTaskAggregateRepository")
public class ServeryTask {

    @AggregateIdentifier
    private ServeryTaskId serveryTaskId;
    private ServeryId serveryId;    //一个备餐任务只属于一个流程
    private List<StallTaskId> reservedStallTaskIds;

    @SuppressWarnings("UnusedDeclaration")
    public ServeryTask() {
    }

    @CommandHandler
    public ServeryTask(CreateServeryTaskCommand cmd) {
        apply(new ServeryTaskCreatedEvent(cmd.getServeryTaskId(), cmd.getCookingId(), cmd.getCookingTime(),
                cmd.getAmountOfCooking()));
    }

    @CommandHandler
    public void handle(WriteServeryTaskLogCommand cmd) {
        apply(new ServeryTaskLogWriteEvent(cmd.getServeryTaskId(), cmd.getPoiId(), cmd.getOrderId(),
                cmd.getDeliveryId(), cmd.getServeryId(), cmd.getStatus(), cmd.getCreateTime()));
    }
}
