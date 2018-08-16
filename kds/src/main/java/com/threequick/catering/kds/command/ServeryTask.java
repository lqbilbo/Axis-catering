package com.threequick.catering.kds.command;

import com.threequick.catering.api.kds.servery.task.ServeryTaskId;
import org.axonframework.commandhandling.model.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;

/**
 * @author luoqi04
 * @version $Id: ServeryTask.java, v 0.1 2018/8/16 下午11:20 luoqi Exp $
 */
@Aggregate(repository = "serveryTaskAggregateRepository")
public class ServeryTask {

    @AggregateIdentifier
    private ServeryTaskId serveryTaskId;
}
