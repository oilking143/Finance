package com.talent.jump.Utility

import android.util.Log
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.io.IOException


object reportTestClass {

    public fun postReport(filePath:String){

        val client = OkHttpClient().newBuilder()
            .build()
        val mediaType: MediaType? = "multipart/form-data".toMediaTypeOrNull()
        val body: RequestBody = MultipartBody.Builder().setType(MultipartBody.FORM)
            .addFormDataPart("is_success", "400")
            .addFormDataPart(
                "report", filePath,
                File(filePath)
                    .asRequestBody("application/octet-stream".toMediaTypeOrNull())
            )
            .addFormDataPart("payment_id", "mmbcdn05tgsfqthmteppeu0")
            .addFormDataPart("comment", "test")
            .build()
        val request: Request = Request.Builder()
            .url("https://finance.minilive.xyz/v1/t/transactions/tscemi9q0sfqtu6i5oclk0/report")
            .method("POST", body)
            .addHeader("Content-Type", "multipart/form-data")
            .addHeader("Accept", "application/json")
            .addHeader(
                "Authorization",
                "Bearer 4b0c77db5cd3a87d3c7148107690affafae9ed06026268a9e56d0bae9cd94efc"
            )
            .build()
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.d("HKT", e.toString())
            }

            override fun onResponse(call: Call, response: Response) {
                val resStr = response.body?.string()


                Log.d("response",resStr!!)
            }
        })
    }
}