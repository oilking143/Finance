package com.talent.jump.data

data class LoginData(
    val access_token: String,
    val expire_at: Long,
    val refresh_token: String,
    val token_type: String,
    val error_code: String,
    val error_msg: String

)