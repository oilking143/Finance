package com.talent.jump.data

data class DataX(
    val payment_channel_name: PaymentChannelNameX,
    val response: ResponseX,
    val trader_payment: TraderPayment,
    val transaction_payment: TransactionPayment
)