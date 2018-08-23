package com.threequick.catering.api.kds.servery.task

import org.axonframework.commandhandling.TargetAggregateIdentifier

abstract class ServeryTaskCommand(@TargetAggregateIdentifier open val serveryTaskId: ServeryTaskId)

data class CreateServeryTaskCommand(
        override val serveryTaskId: ServeryTaskId,
        val cookingId: String,
        val cookingTime: String,
        val amountOfCooking: Long
) : ServeryTaskCommand(serveryTaskId)

data class WriteServeryTaskLogCommand(
        override val serveryTaskId: ServeryTaskId,
        val poiId: Long,
        val orderId: Long,
        val deliveryId: Long,
        val serveryId: Long,
        val status: Int,
        val createTime: Long
) : ServeryTaskCommand(serveryTaskId)