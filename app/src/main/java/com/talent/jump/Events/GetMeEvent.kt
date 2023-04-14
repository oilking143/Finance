package com.talent.jump.Events

import com.talent.jump.data.GetMeDataResponse


class GetMeEvent internal constructor(getMeResponse: GetMeDataResponse)  {

    private var getMeResponse: GetMeDataResponse
    init {
        this.getMeResponse = getMeResponse
    }
    internal fun getMe(): GetMeDataResponse {
        return getMeResponse
    }


}