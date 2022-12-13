package com.talent.jump.Events

import com.talent.jump.data.TradeListResponse


class TradeListEvent internal constructor(tradeListResponse: TradeListResponse){
    private var tradeListResponse: TradeListResponse = tradeListResponse

    init {
        this.tradeListResponse = tradeListResponse
    }

    fun GetTradeListResponse(): TradeListResponse
    {
        return tradeListResponse
    }
}