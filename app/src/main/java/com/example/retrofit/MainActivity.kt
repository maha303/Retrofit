package com.example.retrofit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import retrofit2.Call
import retrofit2.Callback

import retrofit2.Response

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val responseText =findViewById<View>(R.id.text)as TextView
        val apiInterface = APIClient().getClient()?.create(APIInterface::class.java)
        val call: Call<BookDetails>? = apiInterface!!.doGetListResources()

        call?.enqueue(object : Callback<BookDetails>{
            override fun onResponse(
                call:Call<BookDetails>?,
                response:Response<BookDetails>
            ){
                Log.d("TAG",response.code().toString() + "")
                var displayResponse=""
                val resource:BookDetails?=response.body()
                val datumList=resource?.data

                for(datum in datumList!!){
                    displayResponse +="""${datum.id.toString()} ${datum.name} ${datum.pantoneValue} ${datum.year}"""
                }
                responseText.text=displayResponse
            }
            override fun onFailure(call:Call<BookDetails?>,t:Throwable?){
            call.cancel()
            }
        })
    }}
