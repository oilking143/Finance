package com.talent.jump.data

data class TransactionReport(
    val auto_deposit_count: Int,
    val auto_deposit_total_amount: Int,
    val auto_withdraw_count: Int,
    val auto_withdraw_total_amount: Int,
    val deposit_count: Int,
    val deposit_total_amount: Int,
    val withdraw_count: Int,
    val withdraw_total_amount: Int
)