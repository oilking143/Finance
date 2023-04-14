package com.talent.jump.Events

import com.talent.jump.data.CreatePaymentResponse


class CreatePaymentEvent internal constructor(paymentResponse: CreatePaymentResponse)  {

    private var paymentResponse: CreatePaymentResponse
    init {
        this.paymentResponse = paymentResponse
    }
    internal fun getCreateResponse(): CreatePaymentResponse {
        return paymentResponse
    }


}