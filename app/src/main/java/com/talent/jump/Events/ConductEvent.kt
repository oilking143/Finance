package com.talent.jump.Events

import com.google.gson.JsonObject


class ConductEvent internal constructor(conductResponse: JsonObject)  {

    private var conductResponse: JsonObject
    init {
        this.conductResponse = conductResponse
    }
    internal fun getConduct(): JsonObject {
        return conductResponse
    }


}