package com.talent.jump.data

data class DataTakeTrade(
    val member_levels: TakeTradeMemberLevels,
    val merchant_member_payments: MerchantMemberPayments,
    val payment_channels: TakeTradePaymentChannels,
    val payments: Payments,
    val response: Response
)