package com.talent.jump.data

data class TradeListData(
    val member_levels: MemberLevels,
    val payment_channels: PaymentChannels,
    val response: List<ResponseTradeList>
)