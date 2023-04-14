package com.talent.jump.data

data class DataTradeResponse(
    val member_levels: TradeMemberLevels,
    val merchant_member_payment: TradeMerchantMemberPayment,
    val payment: TradePayment,
    val payment_channels: TradePaymentChannels,
    val response: TradeResponse
)