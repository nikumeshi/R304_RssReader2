package com.example.nikumeshi_azddi9.r304_rssreader2

import android.app.job.JobParameters
import android.app.job.JobService
import android.content.Context
import kotlinx.coroutines.*

class PollingJob : JobService(){
    override fun onStopJob(params: JobParameters?) = false

    override fun onStartJob(params: JobParameters?): Boolean {
        GlobalScope.launch(Dispatchers.IO){
            val response = httpGet("https://www.sbbit.jp/rss/HotTopics.rss")

            response?.let {
                val rss = parseRss(response)
                val prefs = getSharedPreferences("pref_polling", Context.MODE_PRIVATE)
                val lastFetchTime = prefs.getLong("last_publish_time", 0L)
                if (lastFetchTime > 0 && lastFetchTime < rss.pubDate.time){
                    notifyUpdate(this@PollingJob)
                }
                prefs.edit().putLong("last_publish_time", rss.pubDate.time).apply()
            }
        }

        return true
    }
}