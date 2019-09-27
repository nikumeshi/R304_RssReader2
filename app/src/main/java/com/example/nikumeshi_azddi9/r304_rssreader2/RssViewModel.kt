package com.example.nikumeshi_azddi9.r304_rssreader2

import android.os.AsyncTask
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class RssViewModel  : ViewModel() {
    private val _rssData = RssLiveData()

    val rssData: LiveData<Rss> = _rssData

    private class RssLiveData : MutableLiveData<Rss>(){
        private fun loadRss(){
            RssAsyncTask(this).execute()
        }

        override fun onActive() {
            value?.let { loadRss() }
        }
    }

    private class RssAsyncTask(private val liveData: RssLiveData) : AsyncTask<Void, Void, Rss>(){

        override fun doInBackground(vararg params: Void?): Rss? {
            val response = httpGet("https://www.sbbit.jp/rss/HotTopics.rss")
            return if (response != null) parseRss(response) else null
        }

        override fun onPostExecute(result: Rss?) {
            liveData.postValue(result)
        }
    }
}