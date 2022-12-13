package com.talent.jump.model

import android.util.Log
import com.google.gson.JsonObject
import com.talent.jump.BuildConfig
import com.talent.jump.Events.*
import com.talent.jump.GlobalData
import com.talent.jump.GlobalData.Companion.Cookies
import com.talent.jump.data.LoginResponse
import com.talent.jump.data.TakeTradeResponse
import com.talent.jump.data.TradeListResponse
import com.talent.jump.data.errorResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import org.greenrobot.eventbus.EventBus
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import java.util.concurrent.TimeUnit

class ApiTokenClient {

    var retrofit= Retrofit.Builder().baseUrl(BuildConfig.BASE_URL)
        .client(getOkHttpClient())
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()


    var api: ApiRequest =retrofit.create(ApiRequest::class.java)


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

        val cookie = object : CookieJar {

            override fun saveFromResponse(url: HttpUrl, cookies: List<Cookie>) {

                if(cookies!=null)
                {
                    for (element in cookies)
                    {
                        Cookies.add(element)
                    }

                }

            }
            override fun loadForRequest(url: HttpUrl): List<Cookie> {
                return Cookies
            }
        }

        val httpClientBuilder = OkHttpClient.Builder()
        //OkHttp进行添加拦截器loggingInterceptor
        httpClientBuilder.addInterceptor(Interceptor { chain ->
            val newRequest = chain.request().newBuilder()
                .addHeader("Authorization", GlobalData.loginToken.access_token)
                .build()
            chain.proceed(newRequest)
        }).addInterceptor(Interceptor { chain ->
            val request = chain.request()
            val response = chain.proceed(request)

            Log.d("responseCode",response.code.toString())

            response
        }).addInterceptor(loggingInterceptor).cookieJar(cookie) .connectTimeout(15, TimeUnit.SECONDS)
        return httpClientBuilder.protocols(Collections.singletonList(Protocol.HTTP_1_1)).build()
    }

    fun getTeadeList(number:String,type:String,before_id:String)
    {
        GlobalScope.launch(Dispatchers.IO) {

            api.getTransactions(number,type,before_id).enqueue(object:retrofit2.Callback<TradeListResponse>{
                override fun onResponse(
                    call: Call<TradeListResponse>,
                    response: Response<TradeListResponse>
                ) {
                    if(response.isSuccessful)
                    {
                        val data: TradeListResponse =response.body()!!
                        EventBus.getDefault().post(TradeListEvent(data))
                    }
                    else{
                        when(response.code())
                        {

                            else->{
                                val errdata = errorResponse("登入失敗",
                                    "登入失敗，請聯絡客服")
                                EventBus.getDefault().post(errorEvent(errdata))
                            }
                        }
                    }
                }

                override fun onFailure(call: Call<TradeListResponse>, t: Throwable) {
                    val errdata = errorResponse("登入失敗",
                        "登入失敗，請聯絡客服")
                    EventBus.getDefault().post(errorEvent(errdata))
                }

            })


        }
    }

    fun TakeTeade(id:String)
    {
        GlobalScope.launch(Dispatchers.IO) {

            api.takeTransactions(id).enqueue(object:retrofit2.Callback<TakeTradeResponse>{
                override fun onResponse(
                    call: Call<TakeTradeResponse>,
                    response: Response<TakeTradeResponse>
                ) {
                    if(response.isSuccessful)
                    {
                        val data: TakeTradeResponse =response.body()!!
//                        EventBus.getDefault().post(TakeTradeEvent(data))
                        EventBus.getDefault().post(testEvent())
                    }
                    else{
                        when(response.code())
                        {
                            400->{
                                val errdata = errorResponse("交易失敗",
                                    "訂單已過期或額度不足")
                                EventBus.getDefault().post(errorEvent(errdata))
                            }

                            403->{
                                val errdata = errorResponse("交易失敗",
                                            "token過期, 需刷新或無權限接取非所屬商戶之訂單")
                                EventBus.getDefault().post(errorEvent(errdata))
                            }

                            404->{
                                val errdata = errorResponse("交易失敗",
                                    "未找到訂單")
                                EventBus.getDefault().post(errorEvent(errdata))
                            }

                            409->{
                                val errdata = errorResponse("交易失敗",
                                    "訂單已被其他交易員接走")
                                EventBus.getDefault().post(errorEvent(errdata))
                            }

                            500->{
                                val errdata = errorResponse("交易失敗",
                                    "伺服器錯誤")
                                EventBus.getDefault().post(errorEvent(errdata))
                            }
                            else->{
                                val errdata = errorResponse("交易失敗",
                                    "伺服器錯誤")
                                EventBus.getDefault().post(errorEvent(errdata))
                            }
                        }
                    }
                }

                override fun onFailure(call: Call<TakeTradeResponse>, t: Throwable) {
                    val errdata = errorResponse("交易失敗",
                        "伺服器錯誤")
                    EventBus.getDefault().post(errorEvent(errdata))

                }

            })


        }
    }

}