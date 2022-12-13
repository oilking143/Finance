package com.talent.jump.Events

import com.talent.jump.data.LoginResponse

class LoginEvent internal constructor(LoginResponse: LoginResponse){
    private var loginResponse: LoginResponse = LoginResponse

    init {
        this.loginResponse = LoginResponse
    }

    fun GetLoginData(): LoginResponse
    {
        return loginResponse
    }
}