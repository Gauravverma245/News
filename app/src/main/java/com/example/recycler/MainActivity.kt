package com.example.recycler

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.telecom.Call
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.littlemango.stacklayoutmanager.StackLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Response
import java.util.*
import javax.security.auth.callback.Callback

class MainActivity : AppCompatActivity() {

    lateinit var adapter: NewsAdapter
    private var articles = mutableListOf<Article>()

    var pageNum = 1
    var totalResults = -1

    val TAG = "MainActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        /** WE WILL FOLLOW THESE THREE STEPS
         * 1-> FIRST WE WILL MAKE AN INTERFACE SO THAT WE CAN PUT ALL THE METHODS THAT
         * WE WILL USE IN OUR PROJECT.
         * 2-> CONVERTERS.(We will make data classes for json to objects)
         * 3-> RETROFIT OBJECTS.
         */

        adapter = NewsAdapter(this@MainActivity, articles)
        newsList.adapter = adapter

        val layoutManager = StackLayoutManager(StackLayoutManager.ScrollOrientation.BOTTOM_TO_TOP)
        layoutManager.setPagerMode(true)
        layoutManager.setPagerFlingVelocity(3000)
        // we can change background color when we swipe up a new article.
        layoutManager.setItemChangedListener(object :StackLayoutManager.ItemChangedListener{
            override fun onItemChanged(position: Int) {
                container.setBackgroundColor(Color.parseColor(ColorPicker.getColor()))
                Log.d(TAG, "First visible item - ${layoutManager.getFirstVisibleItemPosition()} ")
                Log.d(TAG, "Total count - ${layoutManager.itemCount}")

                if(totalResults > layoutManager.itemCount && layoutManager.getFirstVisibleItemPosition() >= layoutManager.itemCount-5){
                    pageNum++
                    getNews()
                }
            }

        })
        newsList.layoutManager = layoutManager

        getNews()


    }

    private fun getNews() {
        Log.d(TAG, "Request sent for - $pageNum")
        val news = NewsServicee.newsInstance.getHeadlines("in", pageNum)
        /** In retrofit, we put all our requests in queue and it processes them one
         * by one. Once a request is processed it will give a callback.
         */

        news.enqueue(object: retrofit2.Callback<News>{
            override fun onResponse(call: retrofit2.Call<News>, response: Response<News>) {
                val news = response.body()
                if(news!=null){
                    Log.d("GAURAV", news.toString())
                    // It will store the count of total articles api can give
                    totalResults = news.totalResults
                    //Here we will add our articles into the articles list.
                    articles.addAll(news.articles)
                    // and notify the adapter that our data has been changed.
                    adapter.notifyDataSetChanged()
                }
            }

            override fun onFailure(call: retrofit2.Call<News>, t: Throwable) {
                Log.d("NOT DONE", "ERROR IN FETCHING NEWS")
            }

        })
    }

}