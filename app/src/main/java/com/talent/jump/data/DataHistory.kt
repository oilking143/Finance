package com.talent.jump.data

data class DataHistory(
    val member_levels: MemberLevelsHistory,
    val payment_channels: PaymentChannelsHistory,
    val response: List<ResponseHistory>
)