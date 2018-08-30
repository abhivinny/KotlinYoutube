package com.learning.kotlinyoutube

import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.video_row.view.textView_channel_name
import kotlinx.android.synthetic.main.video_row.view.textView_video_id
import kotlinx.android.synthetic.main.video_row.view.imageView_thmbnail
import kotlinx.android.synthetic.main.video_row.view.imageView_channel_profile

/**
* Created by Abhishek on 2018-03-30.
*/

class MainAdapter(private val homeFeed: HomeFeed) : RecyclerView.Adapter<MainAdapter.CustomViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): CustomViewHolder {
        val layoutInflater = LayoutInflater.from(parent?.context)
        val cellRow = layoutInflater.inflate(R.layout.video_row, parent, false)
        return CustomViewHolder(cellRow)
    }

    override fun onBindViewHolder(holder: CustomViewHolder?, position: Int) {
        val video = homeFeed.videos[position]
        holder?.view?.textView_video_id?.text = video.name
        holder?.view?.textView_channel_name?.text = video.channel.name + " . " + "20K views 4 days ago"

        val thumbnail = holder?.view?.imageView_thmbnail
        Picasso.get().load(video.imageUrl).into(thumbnail)

        val channelProfile = holder?.view?.imageView_channel_profile
        Picasso.get().load(video.channel.profileImageUrl).into(channelProfile)

        holder?.video = video
    }

    override fun getItemCount(): Int {
        return homeFeed.videos.size
    }


    class CustomViewHolder(val view : View, var video: Video? = null) : RecyclerView.ViewHolder(view) {
        companion object {
            val VIDEO_TITLE = "video_title"
            val VIDEO_ID = "video_id"
        }

        init {
            view.setOnClickListener {
                val intent = Intent(view.context, CourseDetailActivity::class.java)
                intent.putExtra(VIDEO_TITLE, video?.name)
                intent.putExtra(VIDEO_ID, video?.id)
                view.context.startActivity(intent)
            }
        }
    }
}