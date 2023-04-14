package com.talent.jump.model

import android.util.Log
import com.google.gson.JsonObject
import com.talent.jump.BuildConfig
import com.talent.jump.Events.*
import com.talent.jump.GlobalData
import com.talent.jump.GlobalData.Companion.Cookies
import com.talent.jump.data.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import org.greenrobot.eventbus.EventBus
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Field
import java.util.*
import java.util.concurrent.TimeUnit


class ApiTokenClient {

    var retrofit= getOkHttpClient()?.let {
        Retrofit.Builder().baseUrl(BuildConfig.BASE_URL)
        .client(it)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
    }


    var api: ApiRequest =retrofit!!.create(ApiRequest::class.java)


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

                for (element in cookies)
                {
                    Cookies.add(element)
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


            response
        }).addInterceptor(loggingInterceptor).cookieJar(cookie) .connectTimeout(15, TimeUnit.SECONDS)
        return httpClientBuilder.protocols(Collections.singletonList(Protocol.HTTP_1_1)).build()
    }

    fun getHistory(bankName: String,status: String,accountNumber: String)
    {
        GlobalScope.launch(Dispatchers.IO) {

            api.getHistory(bankName,status,accountNumber).enqueue(object:retrofit2.Callback<HistoryResponse>{
                override fun onResponse(
                    call: Call<HistoryResponse>,
                    response: Response<HistoryResponse>
                ) {
                    if(response.isSuccessful)
                    {
                        val data: HistoryResponse =response.body()!!
                        EventBus.getDefault().post(HistoryEvent(data))
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

                override fun onFailure(call: Call<HistoryResponse>, t: Throwable) {
                    val errdata = errorResponse("登入失敗",
                        "登入失敗，請聯絡客服")
                    EventBus.getDefault().post(errorEvent(errdata))
                }

            })


        }
    }



    fun getConduct()
    {
        GlobalScope.launch(Dispatchers.IO) {

            api.getConduct().enqueue(object:retrofit2.Callback<JsonObject>{
                override fun onResponse(
                    call: Call<JsonObject>,
                    response: Response<JsonObject>
                ) {
                    if(response.isSuccessful)
                    {
                        val data: JsonObject =response.body()!!
                        EventBus.getDefault().post(ConductEvent(data))
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

                override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                    val errdata = errorResponse("登入失敗",
                        "登入失敗，請聯絡客服")
                    EventBus.getDefault().post(errorEvent(errdata))
                    Log.d("Error","Error Conduct")
                }

            })


        }
    }

    fun getMe()
    {
        GlobalScope.launch(Dispatchers.IO) {

            api.getMe().enqueue(object:retrofit2.Callback<GetMeDataResponse>{
                override fun onResponse(
                    call: Call<GetMeDataResponse>,
                    response: Response<GetMeDataResponse>
                ) {
                    if(response.isSuccessful)
                    {
                        val data: GetMeDataResponse =response.body()!!
                        EventBus.getDefault().post(GetMeEvent(data))
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

                override fun onFailure(call: Call<GetMeDataResponse>, t: Throwable) {
                    val errdata = errorResponse("登入失敗",
                        "登入失敗，請聯絡客服")
                    EventBus.getDefault().post(errorEvent(errdata))
                }

            })


        }
    }

    fun CretePayment(data: JsonObject)
    {
        GlobalScope.launch(Dispatchers.IO) {


            api.CreatePayments(data).enqueue(object:retrofit2.Callback<CreatePaymentResponse>{
                override fun onResponse(
                    call: Call<CreatePaymentResponse>,
                    response: Response<CreatePaymentResponse>
                ) {
                    if(response.isSuccessful)
                    {
                        val data: CreatePaymentResponse =response.body()!!
                        EventBus.getDefault().post(CreatePaymentEvent(data))
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

                override fun onFailure(call: Call<CreatePaymentResponse>, t: Throwable) {
                    val errdata = errorResponse("連線失敗",
                        "線路因不明原因斷路，請稍後重試")
                    EventBus.getDefault().post(errorEvent(errdata))
                }

            })


        }
    }

    fun getPayMentList(channel_id:String,is_withdraw:String,status:String)
    {
        GlobalScope.launch(Dispatchers.IO) {

            api.getPayments("0","-1","-1").enqueue(object:retrofit2.Callback<PayMentsResponse>{
                override fun onResponse(
                    call: Call<PayMentsResponse>,
                    response: Response<PayMentsResponse>
                ) {
                    if(response.isSuccessful)
                    {
                        val data: PayMentsResponse =response.body()!!
                        EventBus.getDefault().post(GetPaymentListEvent(data))
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

                override fun onFailure(call: Call<PayMentsResponse>, t: Throwable) {
                    val errdata = errorResponse("登入失敗",
                        "登入失敗，請聯絡客服")
                    EventBus.getDefault().post(errorEvent(errdata))
                }

            })


        }
    }


    fun getSingleJsonTreade(id:String)
    {
        GlobalScope.launch(Dispatchers.IO) {

            api.getSingleTransaction(id).enqueue(object:retrofit2.Callback<SingleTradeResponse>{
                override fun onResponse(
                    call: Call<SingleTradeResponse>,
                    response: Response<SingleTradeResponse>
                ) {
                    if(response.isSuccessful)
                    {
                        val dataResponse: SingleTradeResponse =response.body()!!
                        EventBus.getDefault().post(SingleTradeJsonEvent(dataResponse))
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

                override fun onFailure(call: Call<SingleTradeResponse>, t: Throwable) {
                    val errdata = errorResponse("登入失敗",
                        "登入失敗，請聯絡客服")
                    EventBus.getDefault().post(errorEvent(errdata))
                }

            })


        }
    }


    fun getSingleHistory(id:String)
    {
        GlobalScope.launch(Dispatchers.IO) {

            api.getHistoryTransaction(id).enqueue(object:retrofit2.Callback<HistorySingleData>{
                override fun onResponse(
                    call: Call<HistorySingleData>,
                    response: Response<HistorySingleData>
                ) {
                    if(response.isSuccessful)
                    {
                        val data: HistorySingleData =response.body()!!
                        EventBus.getDefault().post(SingleHistoryEvent(data))
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

                override fun onFailure(call: Call<HistorySingleData>, t: Throwable) {
                    val errdata = errorResponse("登入失敗",
                        "登入失敗，請聯絡客服")
                    EventBus.getDefault().post(errorEvent(errdata))
                }

            })


        }
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
                    Log.d("errorConduct","errorConduct")
                }

            })


        }
    }

    fun TakeTread(id:String,paymentId:String )
    {
        GlobalScope.launch(Dispatchers.IO) {

            api.takeTransactions(id,paymentId).enqueue(object:retrofit2.Callback<JsonObject>{
                override fun onResponse(
                    call: Call<JsonObject>,
                    response: Response<JsonObject>
                ) {
                    if(response.isSuccessful)
                    {
                        val data: JsonObject =response.body()!!
                        EventBus.getDefault().post(TakeTradeEvent(data))
                    }
                    else{
                        when(response.code())
                        {
                            400->{
                                val failData = FailTradeData("交易失敗",
                                    "訂單已過期或額度不足")
                                EventBus.getDefault().post(TradeFailEvent(failData))
                            }

                            403->{
                                val failData = FailTradeData("交易失敗",
                                            "token過期, 需刷新或無權限接取非所屬商戶之訂單")
                                EventBus.getDefault().post(TradeFailEvent(failData))
                            }

                            404->{
                                val failData = FailTradeData("交易失敗",
                                    "未找到訂單")
                                EventBus.getDefault().post(TradeFailEvent(failData))
                            }

                            409->{
                                val failData = FailTradeData("交易失敗",
                                    "訂單已被其他交易員接走")
                                EventBus.getDefault().post(TradeFailEvent(failData))
                            }

                            500->{
                                val failData = FailTradeData("交易失敗",
                                    "伺服器錯誤")
                                EventBus.getDefault().post(TradeFailEvent(failData))
                            }
                            else->{
                                val failData = FailTradeData("交易失敗",
                                    "伺服器錯誤")
                                EventBus.getDefault().post(TradeFailEvent(failData))
                            }
                        }
                    }
                }

                override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                    val errdata = errorResponse("交易失敗",
                        "伺服器錯誤")
                    EventBus.getDefault().post(errorEvent(errdata))

                }

            })


        }
    }

    fun PayTread( id:String,merchant_id:String,merchant_order_no:String,amount:Int)
    {
        GlobalScope.launch(Dispatchers.IO) {

            api.payTrasation(id,merchant_id,merchant_order_no,amount,"").enqueue(object:retrofit2.Callback<JsonObject>{
                override fun onResponse(
                    call: Call<JsonObject>,
                    response: Response<JsonObject>
                ) {
                    if(response.isSuccessful)
                    {
                        val data: JsonObject =response.body()!!
                        EventBus.getDefault().post(PayTradeEvent(data))
                    }
                    else{
                        val failData = FailTradeData("交易失敗",
                            "伺服器錯誤")
                        EventBus.getDefault().post(TradeFailEvent(failData))
                    }
                }

                override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                    val errdata = errorResponse("交易失敗",
                        "伺服器錯誤")
                    EventBus.getDefault().post(errorEvent(errdata))

                }

            })


        }
    }



    fun ReportTrade(id:String, comment:RequestBody,isSuccess:RequestBody, paymentId:RequestBody )
    {
        GlobalScope.launch(Dispatchers.IO) {

            api.ReportTrade(id,comment,isSuccess,paymentId).enqueue(object:retrofit2.Callback<ReportResponse>{
                override fun onResponse(
                    call: Call<ReportResponse>,
                    response: Response<ReportResponse>
                ) {
                    if(response.isSuccessful)
                    {
                        val data: ReportResponse =response.body()!!
                        EventBus.getDefault().post(ReportTradeEvent(data))
                    }
                    else{
                        var jObjError = JSONObject(response.errorBody()!!.string())
                        var ErrorMsg=jObjError.getJSONObject("data").getString("error_msg")
                        when(response.code())
                        {
                            400->{
                                if(ErrorMsg.contains("payment_id"))
                                {
                                    val failData = FailTradeData("回報失敗",
                                        "交易銀行卡錯誤，請選擇相同渠道的銀行卡")
                                    EventBus.getDefault().post(ReportFailEvent(failData))
                                }
                                else
                                {
                                    val failData = FailTradeData("回報失敗",
                                        ErrorMsg)
                                    EventBus.getDefault().post(ReportFailEvent(failData))
                                }

                            }

                            403->{
                                val failData = FailTradeData("回報失敗",
                                    ErrorMsg)
                                EventBus.getDefault().post(ReportFailEvent(failData))
                            }

                            404->{
                                val failData = FailTradeData("回報失敗",
                                    ErrorMsg)
                                EventBus.getDefault().post(ReportFailEvent(failData))
                            }

                            409->{
                                val failData = FailTradeData("回報失敗",
                                    ErrorMsg)
                                EventBus.getDefault().post(ReportFailEvent(failData))
                            }

                            500->{
                                val failData = FailTradeData("回報失敗",
                                    ErrorMsg)
                                EventBus.getDefault().post(ReportFailEvent(failData))
                            }
                            else->{
                                val failData = FailTradeData("回報失敗",
                                    ErrorMsg)
                                EventBus.getDefault().post(ReportFailEvent(failData))
                            }
                        }
                    }
                }

                override fun onFailure(call: Call<ReportResponse>, t: Throwable) {

                    var jObjError = JSONObject(call.request().body!!.toString())
                    var ErrorMsg=jObjError.getJSONObject("data").getString("error_msg")
                    val errdata = errorResponse("回報失敗",
                        ErrorMsg)
                    EventBus.getDefault().post(errorEvent(errdata))

                }

            })


        }
    }

}