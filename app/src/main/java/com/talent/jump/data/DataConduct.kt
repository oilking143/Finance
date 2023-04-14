package com.talent.jump.data

data class DataConduct(
    val member_levels: MemberLevelsConduct,
    val merchant_member_payments: MerchantMemberPaymentsConduct,
    val payment_channels: PaymentChannelsConduct,
    val payments: PaymentsConduct,
    val response: ResponseConduct
)