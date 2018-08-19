package com.threequick.catering.api.kds.servery.task

abstract class ServeryTaskEvent(open val serveryTaskId: ServeryTaskId)

class ServeryTaskCreatedEvent(
        override val serveryTaskId: ServeryTaskId,
        val cookingId: String,
        val cookingTime: String,
        val amountOfCooking: Long
) : ServeryTaskEvent(serveryTaskId)