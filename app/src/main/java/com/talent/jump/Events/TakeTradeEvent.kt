package com.talent.jump.Events

import com.talent.jump.data.TakeTradeResponse


class TakeTradeEvent internal constructor(takeTradeResponse: TakeTradeResponse){
    private var takeTradeResponse: TakeTradeResponse = takeTradeResponse

    init {
        this.takeTradeResponse = takeTradeResponse
    }

    fun GetTakeResponse(): TakeTradeResponse
    {
        return takeTradeResponse
    }
}