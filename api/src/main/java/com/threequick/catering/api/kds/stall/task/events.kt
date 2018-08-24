package com.threequick.catering.api.kds.stall.task

import com.threequick.catering.api.kds.servery.task.ServeryTaskId
import com.threequick.catering.api.kds.stall.StallId

abstract class StallTaskEvent(open val stallTaskId: StallTaskId)

class StallTaskCreatedEvent(
        override val stallTaskId: StallTaskId,
        val serveryTaskId: ServeryTaskId,
        val stallId: StallId,
        val seq: Int,
        val estimateTime: Long
) : StallTaskEvent(stallTaskId)

data class StallTaskLogWritedEvent(
        override val stallTaskId: StallTaskId,
        val poiId: Long,
        val orderId: Long,
        val deliveryId: Long,
        val serveryId: Long,
        val stallId: Long,
        val status: Int,
        val createTime: Long
) : StallTaskEvent(stallTaskId)

data class TaskNotePrintedEvent(
        override val stallTaskId: StallTaskId,
        val poiId: Long,
        val orderId: Long,
        val stallTaskName: String,
        val remark: String
) : StallTaskEvent(stallTaskId)