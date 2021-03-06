package com.threequick.catering.api.kds.stall.task

import com.threequick.catering.api.kds.servery.task.ServeryTaskId
import com.threequick.catering.api.kds.stall.StallId
import org.axonframework.commandhandling.TargetAggregateIdentifier

abstract class StallTaskCommand(@TargetAggregateIdentifier open val stallTaskId: StallTaskId)

data class CreateStallTaskCommand(
        override val stallTaskId: StallTaskId,
        val serveryTaskId: ServeryTaskId,
        val stallTaskName: String,
        val stallId: StallId,
        val seq: Int,
        val estimateTime: Long
) : StallTaskCommand(stallTaskId)

data class WriteStallTaskLogCommand(
        override val stallTaskId: StallTaskId,
        val poiId: Long,
        val orderId: Long,
        val deliveryId: Long,
        val serveryId: Long,
        val stallId: Long,
        val status: Int,
        val createTime: Long
) : StallTaskCommand(stallTaskId)

data class PrintTaskNoteCommand(
        override val stallTaskId: StallTaskId,
        val poiId: Long,
        val orderId: Long,
        val stallTaskName: String,
        val remark: String
) : StallTaskCommand(stallTaskId)