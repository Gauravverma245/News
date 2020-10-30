package com.example.recycler

import android.telecom.Call
import com.google.gson.Gson
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

//http://newsapi.org/v2/everything?q=apple&from=2020-10-29&to=2020-10-29&sortBy=popularity&apiKey=c79355fca77c461f982460bcdddbaf0f
//http://newsapi.org/v2/top-headlines?country=in&&apiKey=c79355fca77c461f982460bcdddbaf0f
/**STEP -> 1 */
const val BASE_URL = "https://newsapi.org/"
const val API_KEY = "c79355fca77c461f982460bcdddbaf0f"
interface NewsInterface{

    /** Here we want to get the data so we will be use get
    annotation. In the get annotation we mention the end points
    so that we can make a complete url. whenever we call get
    it will hit these end points.We also want to pass country
    and page number that's why we will pass them as a query.*/

    /** Once we made our request it will give callback whether request was
     * successful or not.
     */

    @GET("v2/top-headlines?apiKey=$API_KEY")
    fun getHeadlines(@Query("country")country: String, @Query("page")page: Int): retrofit2.Call<News>

    // It will make url of this type.
    // http://newsapi.org/v2/top-headlines?apiKey=c79355fca77c461f982460bcdddbaf0f&country=in&page=1

}

/** Make a new Retrofit object*/
object NewsServicee{
    // We have to implement this interface. let's make its instance.
    val newsInstance: NewsInterface

    init {
        //Make object of Retrofit then pass BASE_URL.
        //After this we have to define our converter.
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        // Now we wil give Implementation of Interface to retrofit object.
        newsInstance = retrofit.create(NewsInterface::class.java)

        // Now we have instance of retrofit. With the help of this
        // we can call our getHeadlines function() to get the news.
        // look at Mainactivity.
    }
}