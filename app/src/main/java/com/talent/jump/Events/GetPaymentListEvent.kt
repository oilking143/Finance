package com.talent.jump.Events

import com.talent.jump.data.PayMentsResponse


class GetPaymentListEvent internal constructor(paymentResponse: PayMentsResponse)  {

    private var paymentResponse: PayMentsResponse
    init {
        this.paymentResponse = paymentResponse
    }
    internal fun getPayment(): PayMentsResponse {
        return paymentResponse
    }


}