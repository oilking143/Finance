package com.talent.jump.data

data class TransactionReportGetMe(
    val deposit_count: Int,
    val deposit_total_amount: Int,
    val withdraw_count: Int,
    val withdraw_total_amount: Int
)