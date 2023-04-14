package com.talent.jump.data

data class TradeListData(
    val payment_channel_name: TradeListPaymentChannelName,
    val response: List<ResponseTradeList>
)