package com.talent.jump.data

data class DataPayMents(
    val payment_channel_name: PaymentChannelName,
    val response: List<ResponsePayments>
)