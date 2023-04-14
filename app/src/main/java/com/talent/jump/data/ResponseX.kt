package com.talent.jump.data

data class ResponseX(
    val amount: Int,
    val assign_at: Long,
    val comment: String,
    val create_at: Long,
    val description: String,
    val expire_at: Long,
    val handle_at: Int,
    val id: String,
    val merchant_id: String,
    val merchant_order_no: String,
    val pay_at: Int,
    val payment_channel_id: Int,
    val report_image: String,
    val status: Int,
    val third_party_exchange_rate: Int,
    val third_party_id: Int,
    val third_party_order_no: String,
    val third_party_payment_channel_id: Int,
    val trader_payment_id: String,
    val transaction_payment_id: String,
    val type: Int
)