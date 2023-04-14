package com.talent.jump.Events

import com.talent.jump.data.HistorySingleData


class SingleHistoryEvent internal constructor(singleTradeResponse: HistorySingleData){
    private var singleTradeResponse: HistorySingleData = singleTradeResponse

    init {
        this.singleTradeResponse = singleTradeResponse
    }

    fun GetTradeListResponse(): HistorySingleData
    {
        return singleTradeResponse
    }
}