package com.threequick.catering.api.kds.stall.task

import org.axonframework.common.IdentifierFactory
import java.io.Serializable

data class StallTaskId(val identifier: String = IdentifierFactory.getInstance().generateIdentifier()) : Serializable {

    companion object {
        private const val serialVersionUID = -2521069612056157076L
    }
}