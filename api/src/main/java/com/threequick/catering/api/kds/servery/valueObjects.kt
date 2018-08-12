package com.threequick.catering.api.kds.servery

import org.axonframework.common.IdentifierFactory
import java.io.Serializable

data class ServeryId(val identifier: String = IdentifierFactory.getInstance().generateIdentifier()) : Serializable {

    companion object {
        private const val serialVersionUID = -2521069653478157076L
    }
}