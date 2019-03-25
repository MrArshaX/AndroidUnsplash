package com.arsha.unsplash

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONArray
import org.json.JSONObject

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        getPosts()
    }

    private fun getPosts(){
        MyConnection("photos?page=1&per_page=50&order_by=latest",header = resources.getString(R.string.client_id),
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
        var avatarUrl: String ; var imageUrl: String ; var desc: String ; var likes: String ; var fullName: String
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
            avatarUrl = avatarObject.getString("medium")
            //----

            // get fullName
            userObject = mainObject.getJSONObject("user")
            fullName = userObject.getString("name")
            //

            posts.add(PostDC(avatarUrl,fullName, imageUrl, likes, desc))
            initPostRecycler(posts)
        }
    }

    private fun initPostRecycler(list: MutableList<PostDC>){
        val llm = LinearLayoutManager(this,LinearLayoutManager.VERTICAL, false)
        main_rec.layoutManager = llm
        main_rec.adapter = PostAdapter(applicationContext, list)
    }

}
