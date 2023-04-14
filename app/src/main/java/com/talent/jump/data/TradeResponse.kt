package com.talent.jump.data

data class TradeResponse(
    val amount: Int,
    val comment: String,
    val create_at: Long,
    val description: String,
    val expire_at: Int,
    val handle_at: Long,
    val handle_by_id: String,
    val id: String,
    val merchant_id: String,
    val merchant_member_id: String,
    val merchant_member_payment_id: String,
    val merchant_order_no: String,
    val merchant_order_time: Long,
    val pay_at: Int,
    val pay_info_callback_url: String,
    val payment_channel_id: Int,
    val payment_id: String,
    val report_image: String,
    val result_callback_url: String,
    val status: Int,
    val type: Int
)