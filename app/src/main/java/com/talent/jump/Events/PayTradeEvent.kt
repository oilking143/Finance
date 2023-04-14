package com.talent.jump.Events

import com.google.gson.JsonObject


class PayTradeEvent internal constructor(payResponse: JsonObject){
    private var payResponse: JsonObject = payResponse

    init {
        this.payResponse = payResponse
    }

    fun GetPayResponse(): JsonObject
    {
        return payResponse
    }
}