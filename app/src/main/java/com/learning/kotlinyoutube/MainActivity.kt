package com.learning.kotlinyoutube

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_main.recyclerView_main
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Callback
import okhttp3.Call
import okhttp3.Response
import java.io.IOException

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView_main.layoutManager = LinearLayoutManager(this)
        fetchJson()
    }

    private fun fetchJson() {
        val feedUrl = "http://www.dropbox.com/s/shaa06bmlzd6gkb/feed.json?dl=1"

        val client = OkHttpClient()
        val request = Request.Builder().url(feedUrl).build()
        client.newCall(request).enqueue(object : Callback {
            override fun onResponse(call: Call?, response: Response?) {
                val body = response?.body()?.string()
                val gson = GsonBuilder().create()
                val homeFeed = gson.fromJson(body, HomeFeed::class.java)

                runOnUiThread {
                    recyclerView_main.adapter = MainAdapter(homeFeed)
                }
            }

            override fun onFailure(call: Call?, e: IOException?) {
                println("fail to request")

            }
        })
    }
}
