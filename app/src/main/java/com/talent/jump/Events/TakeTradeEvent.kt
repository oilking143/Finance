package com.talent.jump.Events

import com.google.gson.JsonObject


class TakeTradeEvent internal constructor(takeTradeResponse: JsonObject){
    private var takeTradeResponse: JsonObject = takeTradeResponse

    init {
        this.takeTradeResponse = takeTradeResponse
    }

    fun GetTakeResponse(): JsonObject
    {
        return takeTradeResponse
    }
}