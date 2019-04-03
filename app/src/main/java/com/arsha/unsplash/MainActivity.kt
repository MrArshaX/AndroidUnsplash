package com.arsha.unsplash

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONArray
import org.json.JSONObject

class MainActivity : AppCompatActivity() {

    val handler = Handler()
    var loadedPosts: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        handler.post(networkState)
    }

    private val networkState = object : Runnable {
        override fun run() {
            val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
            if (activeNetwork != null) {
                if(activeNetwork.isConnected!!){
                    main_connectionError.visibility = View.GONE
                    if (!loadedPosts){
                        getPosts()
                    }
                }else{
                    main_connectionError.visibility = View.VISIBLE
                    main_connectionError.text = getString(R.string.connecting)
                }
            }else{
                main_connectionError.visibility = View.VISIBLE
                main_connectionError.text = getString(R.string.waiting_for_network)
            }
            handler.postDelayed(this, 3000)
        }
    }

    private fun getPosts(){
        Log.i("debug","called getPosts")
        loadedPosts = true
        getJSONArray("photos?page=1&per_page=50&order_by=latest",header = resources.getString(R.string.client_id),
                onSuccess = {Response ->
                    Log.i("debug","Response:\n $Response")
                    parsePost(Response)
                },
                onFailed = {ErrorMessage ->
                    Log.i("error","getPost function -> $ErrorMessage")
                })
    }

    private fun parsePost(json: JSONArray){
        val posts: MutableList<PostDC> = ArrayList()
        var avatarUrl: String ; var imageUrl: String ; var desc: String ; var likes: String ; var fullName: String ; var userName: String
        var twitter: String; var instagram: String; var bio: String
        var avatarObject: JSONObject ; var mainObject: JSONObject; var urlObject: JSONObject; var userObject: JSONObject
        for (obj in 0 until json.length()){
            // get Image url
            mainObject = json.getJSONObject(obj)
            urlObject = mainObject.getJSONObject("urls")
            imageUrl = urlObject.getString("regular")
            //----

            // get Description
            desc = mainObject.getString("alt_description")
            //----

            // get likes
            likes = mainObject.getString("likes")
            //----

            // get avatar
            userObject = mainObject.getJSONObject("user")
            avatarObject = userObject.getJSONObject("profile_image")
            avatarUrl = avatarObject.getString("large")
            //----

            // get fullName
            userObject = mainObject.getJSONObject("user")
            fullName = userObject.getString("name")
            //

            // get info
            twitter = userObject.getString("twitter_username")
            instagram = userObject.getString("instagram_username")
            bio = userObject.getString("bio")
            //

            // get userName
            userName = userObject.getString("username")
            //

            posts.add(PostDC(avatarUrl,fullName, imageUrl, likes, desc, twitter, instagram, bio, userName))
            initPostRecycler(posts)
        }
    }

    private fun initPostRecycler(list: MutableList<PostDC>){
        val llm = LinearLayoutManager(this,LinearLayoutManager.VERTICAL, false)
        main_rec.layoutManager = llm
        main_rec.adapter = PostAdapter(this, list)
    }

}
