package com.example.nikumeshi_azddi9.r304_rssreader2

import android.app.job.JobInfo
import android.app.job.JobScheduler
import android.content.ComponentName
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.browser.customtabs.CustomTabsIntent
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {
    private lateinit var rssViewModel : RssViewModel
    private val rssObserver = Observer<Rss> { rss ->
        rss?.let{
            val adapter = ArticlesAdapter(this, rss.articles) { article ->
                CustomTabsIntent.Builder().build().launchUrl(this, Uri.parse(article.link))
            }
            articles.adapter = adapter
            articles.layoutManager = GridLayoutManager(this, 2)
        }
    }
    override fun onCreate (savedInstanceState: Bundle?) {
        super .onCreate(savedInstanceState)
        setContentView(R.layout.activity_main )
        rssViewModel = ViewModelProviders.of(this).get(RssViewModel::class.java)
        rssViewModel.rssData.observe(this, rssObserver)
        createChannel(this)
        val fetchJob = JobInfo.Builder(1 , ComponentName(this, PollingJob::class.java))
            .setPeriodic(TimeUnit.HOURS.toMillis(6))
            .setPersisted(true)
            .setRequiredNetworkType(JobInfo. NETWORK_TYPE_ANY)
            .build()
        getSystemService(JobScheduler::class.java).schedule(fetchJob)
    }
}