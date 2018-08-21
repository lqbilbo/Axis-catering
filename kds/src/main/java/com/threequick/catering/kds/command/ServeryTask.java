package com.threequick.catering.kds.command;

import com.threequick.catering.api.kds.servery.ServeryId;
import com.threequick.catering.api.kds.servery.task.CreateServeryTaskCommand;
import com.threequick.catering.api.kds.servery.task.ServeryTaskCreatedEvent;
import com.threequick.catering.api.kds.servery.task.ServeryTaskId;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.model.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;

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

    @CommandHandler
    public ServeryTask(CreateServeryTaskCommand cmd) {
        apply(new ServeryTaskCreatedEvent(cmd.getServeryTaskId(), cmd.getCookingId(), cmd.getCookingTime(),
                cmd.getAmountOfCooking()));
    }




}
