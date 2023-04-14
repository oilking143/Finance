package com.talent.jump.Events

import com.talent.jump.data.errorResponse


class LoadEvent internal constructor(id:String,paymentID:String,tradeType:Int)  {

    var id=id
    var paymentID=paymentID
    var tradeType=tradeType

    fun getID():String
    {
        return id
    }

    fun getPayment():String
    {
        return paymentID
    }

    fun getTreadType():Int
    {
        return tradeType
    }


}