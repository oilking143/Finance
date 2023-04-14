package com.talent.jump.data

data class SingleTradeData(
    val payment_channel_name: SingleTradePaymentChannelName,
    val response: ResponseSingleTrade,
    val trader_payment: SingleTradeTraderPayment,
    val transaction_payment: SingleTradeTransactionPayment
)