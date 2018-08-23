package com.threequick.catering.api.kds.servery.task

abstract class ServeryTaskEvent(open val serveryTaskId: ServeryTaskId)

class ServeryTaskCreatedEvent(
        override val serveryTaskId: ServeryTaskId,
        val cookingId: String,
        val cookingTime: String,
        val amountOfCooking: Long
) : ServeryTaskEvent(serveryTaskId)

data class ServeryTaskLogWriteEvent(
        override val serveryTaskId: ServeryTaskId,
        val poiId: Long,
        val orderId: Long,
        val deliveryId: Long,
        val serveryId: Long,
        val status: Int,
        val createTime: Long
) : ServeryTaskEvent(serveryTaskId)