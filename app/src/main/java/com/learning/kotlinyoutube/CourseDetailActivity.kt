package com.learning.kotlinyoutube

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.gson.GsonBuilder
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.course_detail_recycler_view.course_detail_recycler_view
import kotlinx.android.synthetic.main.course.view.imageView
import kotlinx.android.synthetic.main.course.view.textView_duration
import kotlinx.android.synthetic.main.course.view.textView_course
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

/**
* Created by Abhishek on 2018-05-08.
*/
class CourseDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.course_detail_recycler_view)
        course_detail_recycler_view.layoutManager = LinearLayoutManager(this)

        val videoTitle = intent.getStringExtra(MainAdapter.CustomViewHolder.VIDEO_TITLE)
        val videoId = intent.getIntExtra(MainAdapter.CustomViewHolder.VIDEO_ID, -1)
        supportActionBar?.title = videoTitle
        fetchJson(videoId)
    }

    private fun fetchJson(videoId: Int) {
        val url =  "https://api.letsbuildthatapp.com/youtube/course_detail?id=" + videoId
        val client = OkHttpClient()
        val request = Request.Builder().url(url).build()
        client.newCall(request).enqueue(object : Callback {
            override fun onResponse(call: Call?, response: Response?) {
                val body = response?.body()?.string()
                val gson = GsonBuilder().create()
                val courseVideos = gson.fromJson(body, Array<CourseVideo>::class.java)

                runOnUiThread {
                    course_detail_recycler_view.adapter = CourseDetailAdapter(courseVideos)
                }

            }

            override fun onFailure(call: Call?, e: IOException?) {
                println("Error in getting the response " + e?.message)
            }
        })

    }

    private class CourseDetailAdapter(val courseVideos: Array<CourseVideo>) :
            RecyclerView.Adapter<CourseDetailViewHolder>() {

        override fun onBindViewHolder(holder: CourseDetailViewHolder?, position: Int) {
            val course = courseVideos[position]
            holder?.view?.textView_course?.text = course.name
            holder?.view?.textView_duration?.text = course.duration
            val thumbnail = holder?.view?.imageView
            Picasso.get().load(course.imageUrl).into(thumbnail)
        }

        override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): CourseDetailViewHolder {
            val layoutInflater = LayoutInflater.from(parent?.context)
            val cellRow = layoutInflater.inflate(R.layout.course, parent, false)
            return CourseDetailViewHolder(cellRow)
        }

        override fun getItemCount(): Int {
            return courseVideos.size
        }
    }

    private class CourseDetailViewHolder(val view : View) : RecyclerView.ViewHolder(view)

    private class CourseVideo(val name: String,
                              val duration: String,
                              val imageUrl: String)
}