package com.talent.jump.Events

import com.talent.jump.data.ReportResponse


class ReportTradeEvent internal constructor(reportTradeResponse: ReportResponse){
    private var reportTradeResponse: ReportResponse

    init {
        this.reportTradeResponse = reportTradeResponse
    }

    fun GetTakeResponse(): ReportResponse
    {
        return reportTradeResponse
    }
}