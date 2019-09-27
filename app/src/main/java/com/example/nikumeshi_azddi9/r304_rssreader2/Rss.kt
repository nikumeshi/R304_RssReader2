package com.example.nikumeshi_azddi9.r304_rssreader2


import org.w3c.dom.NodeList
import java.io.InputStream
import java.text.SimpleDateFormat
import java.util.*
import javax.xml.parsers.DocumentBuilderFactory
import javax.xml.xpath.XPathConstants
import javax.xml.xpath.XPathFactory

data class  Article(val title: String, val link: String, val pubDate: Date)

data class Rss(val title: String, val pubDate: Date, val articles: List<Article>)

fun parseRss(stream: InputStream) : Rss{
    val doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(stream)
    val xPath = XPathFactory.newInstance().newXPath()
    val formatter = SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.US)

    val items = xPath.evaluate("/rss/channel//item", doc, XPathConstants.NODESET) as NodeList

    val articles = arrayListOf<Article>()

    for (i in 0 until items.length){
        val item = items.item(i)
        articles.add(Article(
            xPath.evaluate("./title/text()", item),
            xPath.evaluate("./link/text()", item),
            formatter.parse(xPath.evaluate("./pubDate/text()", item))
            )
        )
    }

    return Rss(
        xPath.evaluate("/rss/channel/title/text()", doc),
        formatter.parse(xPath.evaluate("/rss/channel/pubDate/text()", doc)),
        articles
    )
}

//class RssLoader(context: Context) : AsyncTaskLoader<Rss>(context){
//    private var cache : Rss? = null
//
//    override fun loadInBackground(): Rss? {
//        val response = httpGet("https://www.sbbit.jp/rss/HotTopics.rss")
//        response?.let { return parseRss(response) }
//
//        return null
//    }
//
//    override fun deliverResult(data: Rss?) {
//        if (isReset || data == null) return
//
//        cache = data
//        super.deliverResult(data)
//    }
//
//    override fun onStartLoading() {
//        cache?.let { deliverResult(cache) }
//
//        if (takeContentChanged() || cache == null) forceLoad()
//    }
//
//    override fun onStopLoading() {
//        cancelLoad()
//    }
//
//    override fun onReset() {
//        super.onReset()
//        onStopLoading()
//        cache = null
//    }
//}