package com.arsha.unsplash.Fragments


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout

import com.arsha.unsplash.R
import com.arsha.unsplash.Utils.Adapter.PostAdapter
import com.arsha.unsplash.Utils.Adapter.PostMode
import com.arsha.unsplash.Utils.Adapter.RecyclerGridAdapter
import com.arsha.unsplash.Utils.DataClasses.PostDC
import com.arsha.unsplash.Utils.getJSONArray
import kotlinx.android.synthetic.main.fragment_recyclergrid.*
import kotlinx.android.synthetic.main.fragment_recyclerview.*
import org.json.JSONArray
import org.json.JSONObject


/**
 * A simple [Fragment] subclass.
 */
class RecyclerviewFragment : Fragment() {

    companion object {
        fun newInstance(uName: String): RecyclerviewFragment{
            val fragment = RecyclerviewFragment()
            val bundle = Bundle()
            bundle.putString("uName",uName)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_recyclerview, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getPhotos()

    }

    private fun getPhotos(){
        getJSONArray("users/${arguments!!.getString("uName")}/photos", resources.getString(R.string.client_id),
                onSuccess = { Response ->
                    parsePhotos(Response)
                },
                onFailed = { ErrorMessage ->
                    Log.i("error", "getPhotos Error ->\n$ErrorMessage")
                })
    }

    private fun parsePhotos(jArray: JSONArray){
        var mainObject: JSONObject; var urlsObject: JSONObject
        val posts: MutableList<PostDC> = ArrayList()
        for (i in 0 until jArray.length()){
            mainObject = jArray.getJSONObject(i)
            urlsObject = mainObject.getJSONObject("urls")
            posts.add(PostDC(imageUrl = urlsObject.getString("regular"),likes = mainObject.getString("likes"),desc = mainObject.getString("alt_description")))
        }
        initRecycler(posts)
    }

    private fun initRecycler(posts: MutableList<PostDC>){
        prof_recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        prof_recyclerView.adapter = PostAdapter(context, posts, PostMode.PROFILE)
    }

}// Required empty public constructor
