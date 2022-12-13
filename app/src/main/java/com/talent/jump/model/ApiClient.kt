package com.talent.jump.model

import android.util.Log
import com.google.gson.JsonObject
import com.talent.jump.BuildConfig
import com.talent.jump.Events.LoginEvent
import com.talent.jump.Events.errorEvent
import com.talent.jump.data.LoginResponse
import com.talent.jump.data.errorResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.greenrobot.eventbus.EventBus
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ApiClient {
    var api= Retrofit.Builder().baseUrl(BuildConfig.BASE_URL)
        .client(getOkHttpClient())
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
        .create(ApiRequest::class.java)

    private fun getOkHttpClient(): OkHttpClient? {
        //日志显示级别
        val level: HttpLoggingInterceptor.Level = HttpLoggingInterceptor.Level.BODY
        //新建log拦截器
        val loggingInterceptor = HttpLoggingInterceptor(object : HttpLoggingInterceptor.Logger {
            override fun log(message: String) {
                Log.d("Response", "OkHttp====Message:$message")
            }
        })
        loggingInterceptor.setLevel(level)
        //定制OkHttp


        val httpClientBuilder = OkHttpClient.Builder()
        //OkHttp进行添加拦截器loggingInterceptor
        httpClientBuilder.addInterceptor(loggingInterceptor).connectTimeout(10, TimeUnit.SECONDS)

        return httpClientBuilder.build()
    }

    fun Login(data: JsonObject)
    {
        GlobalScope.launch(Dispatchers.IO) {


            api.Login(data).enqueue(object:retrofit2.Callback<LoginResponse>{
                override fun onResponse(
                    call: Call<LoginResponse>,
                    response: Response<LoginResponse>
                ) {
                    if(response.isSuccessful)
                    {
                        val data: LoginResponse =response.body()!!
                        EventBus.getDefault().post(LoginEvent(data))
                    }
                    else{
                        when(response.code())
                        {
                            400->{
                                val errdata = errorResponse("登入失敗",
                                    "登入失敗，請檢查帳號密碼")
                                EventBus.getDefault().post(errorEvent(errdata))
                            }

                                else->{
                                    val errdata = errorResponse("登入失敗",
                                        "登入失敗，請聯絡客服")
                                    EventBus.getDefault().post(errorEvent(errdata))
                                }
                        }


                    }
                }

                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    val errdata = errorResponse("連線失敗",
                        "線路因不明原因斷路，請稍後重試")
                    EventBus.getDefault().post(errorEvent(errdata))
                }

            })


        }
    }

}