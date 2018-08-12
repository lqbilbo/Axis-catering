package com.threequick.catering.api.kds.servery

import com.threequick.catering.api.kds.stall.StallId
import org.axonframework.commandhandling.TargetAggregateIdentifier
import javax.validation.constraints.Min

abstract class ServeryCommand(@TargetAggregateIdentifier open val serveryId: ServeryId)

data class CreateServeryCommand(
        override val serveryId: ServeryId,
        val poiId: Long,
        val serveryName: String,
        val remark: String,
        val online: Boolean,
        val offline: Boolean
) : ServeryCommand(serveryId)

data class AddStallsToServeryCommand(
        override val serveryId: ServeryId,
        val stallId: StallId,
        @Min(1) val amountOfStallsToAdd: Long
) : ServeryCommand(serveryId)

data class RemoveStallsToServeryCommand(
        override val serveryId: ServeryId,
        val stallId: StallId
) : ServeryCommand(serveryId)

data class DeleteServeryCommand(
        override val serveryId: ServeryId
) : ServeryCommand(serveryId)