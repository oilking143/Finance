package com.talent.jump.data

data class DataHistorySingle(
    val payment_channel_name: PaymentChannelNameHistorySingle,
    val response: HistorySingleResponse,
    val trader_payment: HistorySingleTraderPayment,
    val transaction_payment: HistorySingleTransactionPayment
)