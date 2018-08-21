package com.threequick.catering.api.kds.stall.task

import com.threequick.catering.api.kds.servery.task.ServeryTaskId
import com.threequick.catering.api.kds.stall.StallId
import org.axonframework.commandhandling.TargetAggregateIdentifier

abstract class StallTaskCommand(@TargetAggregateIdentifier open val stallTaskId: StallTaskId)

data class CreateStallTaskCommand(
        override val stallTaskId: StallTaskId,
        val serveryTaskId: ServeryTaskId,
        val stallId: StallId,
        val seq: Int,
        val estimateTime: Long
) : StallTaskCommand(stallTaskId)