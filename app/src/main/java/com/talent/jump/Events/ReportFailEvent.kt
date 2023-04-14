package com.talent.jump.Events

import com.talent.jump.data.FailTradeData


class ReportFailEvent internal constructor(failResponse: FailTradeData)  {

    private var failResponse: FailTradeData = failResponse
    init {
        this.failResponse = failResponse
    }
    internal fun getMsg(): FailTradeData {
        return failResponse
    }


}