package com.threequick.catering.api.kds.servery

import com.threequick.catering.api.kds.stall.StallId

abstract class ServeryEvent(open val serveryId: ServeryId)

data class ServeryCreatedEvent(
        override val serveryId: ServeryId,
        val poiId: Long,
        val serveryName: String,
        val remark: String,
        val online: Boolean,
        val offline: Boolean
) : ServeryEvent(serveryId)

data class ServeryUpdatedEvent(
        val serveryName: String,
        val remark: String,
        val online: Boolean,
        val offline: Boolean
)

data class StallsAddedToServeryEvent(
        override val serveryId: ServeryId,
        val stallId: StallId,
        val amountOfStallsToAdd: Long
) : ServeryEvent(serveryId)

data class StallsRemovedFromServeryEvent(
        override val serveryId: ServeryId,
        val stallId: StallId
) : ServeryEvent(serveryId)

data class ServeryDeletedEvent(
        override val serveryId: ServeryId
) : ServeryEvent(serveryId)

