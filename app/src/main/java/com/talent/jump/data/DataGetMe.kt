package com.talent.jump.data

data class DataGetMe(
    val current_income: Int,
    val cut: Int,
    val invite_traders_count: Int,
    val lower_trader_month_income: Int,
    val month_income: Int,
    val payment_count: Int,
    val quota: Int,
    val response: ResponseGetMe,
    val transaction_report: TransactionReportGetMe
)