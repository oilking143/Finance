package com.talent.jump.Events

import com.talent.jump.data.errorResponse


class errorEvent internal constructor(errResponse: errorResponse)  {

    private var errResponse: errorResponse = errResponse
    init {
        this.errResponse = errResponse
    }
    internal fun getMsg(): errorResponse {
        return errResponse
    }


}