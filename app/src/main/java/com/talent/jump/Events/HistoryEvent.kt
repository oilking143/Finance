package com.talent.jump.Events

import com.talent.jump.data.HistoryResponse


class HistoryEvent internal constructor(historyResponse: HistoryResponse)  {

    private var historyResponse: HistoryResponse
    init {
        this.historyResponse = historyResponse
    }
    internal fun getHistory(): HistoryResponse {
        return historyResponse
    }


}