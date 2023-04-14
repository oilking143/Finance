package com.talent.jump.data

data class ResponseGetMe(
    val create_at: Long,
    val direct_id: String,
    val id: String,
    val invitation_code: String,
    val nickname: String,
    val status: Boolean,
    val third_party_level_id: Int,
    val type: Int,
    val update_at: Long,
    val username: String
)