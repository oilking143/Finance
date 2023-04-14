package com.talent.jump.data

data class SingleTradeTraderPayment(
    val account_name: String,
    val account_number: String,
    val bank_name: String,
    val create_at: Int,
    val current_quota: Int,
    val delete_at: Int,
    val id: String,
    val is_withdraw: Any,
    val payment_channel_id: Int,
    val trader_id: String,
    val update_at: Int
)