package com.threequick.catering.api.kds.servery.task

import org.axonframework.common.IdentifierFactory
import java.io.Serializable

data class ServeryTaskId(val identifier: String = IdentifierFactory.getInstance().generateIdentifier()) : Serializable {

    companion object {
        private const val serialVersionUID = -2521069343200157076L
    }
}