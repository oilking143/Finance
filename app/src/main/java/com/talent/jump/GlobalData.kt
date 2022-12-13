package com.talent.jump

import android.app.Application
import com.talent.jump.data.LoginData
import okhttp3.Cookie

open class GlobalData: Application()  {

    companion object {
        var Cookies = mutableListOf<Cookie>()
        var loginToken:LoginData= LoginData("",0,"","","","")
    }

}