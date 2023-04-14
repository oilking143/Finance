package com.talent.jump.data

data class TransactionPayment(
    val account_name: String,
    val account_number: String,
    val bank_name: String,
    val create_at: Int,
    val id: String,
    val payment_channel_id: Int
)