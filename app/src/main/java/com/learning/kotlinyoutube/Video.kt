package com.learning.kotlinyoutube

/**
 * Created by Abhishek on 2018-03-31.
 */

class Video(val id : Int,
            val name : String,
            val imageUrl: String,
            val channel : Channel)

class Channel(val name : String, val profileImageUrl : String)