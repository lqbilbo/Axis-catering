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