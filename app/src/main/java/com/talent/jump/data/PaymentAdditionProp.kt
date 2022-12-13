package com.talent.jump.data

data class PaymentAdditionProp(
    val account_name: String,
    val account_number: String,
    val bank_name: String,
    val channel_id: Int,
    val create_at: Int,
    val current_quota: Int,
    val delete_at: Int,
    val holder_id: String,
    val id: String,
    val is_withdraw: Boolean,
    val status: Boolean,
    val update_at: Int
)