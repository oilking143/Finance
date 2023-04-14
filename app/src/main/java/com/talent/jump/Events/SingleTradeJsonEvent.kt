package com.talent.jump.Events

import com.talent.jump.data.SingleTradeResponse


class SingleTradeJsonEvent internal constructor(singleTradeResponse: SingleTradeResponse){
    private var singleTradeResponse: SingleTradeResponse = singleTradeResponse

    init {
        this.singleTradeResponse = singleTradeResponse
    }

    fun GetTradeListResponse(): SingleTradeResponse
    {
        return singleTradeResponse
    }
}