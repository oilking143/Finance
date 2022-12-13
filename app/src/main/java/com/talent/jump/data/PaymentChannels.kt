package com.talent.jump.data

import com.google.gson.annotations.SerializedName

data class PaymentChannels(

    @SerializedName("1")
    val first: String,
    @SerializedName("2")
    val sec: String,
    @SerializedName("3")
    val third: String
)