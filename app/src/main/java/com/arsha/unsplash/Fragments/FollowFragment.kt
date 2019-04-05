package com.arsha.unsplash.Fragments


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.arsha.unsplash.R
import com.arsha.unsplash.Utils.Adapter.RecyclerFollowAdapter
import com.arsha.unsplash.Utils.DataClasses.FollowDC
import com.arsha.unsplash.Utils.getJSONArray
import kotlinx.android.synthetic.main.fragment_follow.*
import org.json.JSONArray


/**
 * A simple [Fragment] subclass.
 */
class FollowFragment : Fragment() {

    companion object {
        fun newInstance(uName: String, mode: String): FollowFragment{
            val fragment = FollowFragment()
            val bundle = Bundle()
            bundle.putString("uName",uName)
            bundle.putString("mode",mode)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_follow, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getLists()
    }

    private fun getLists(){
        getJSONArray("users/${arguments!!.getString("uName")}/${arguments!!.getString("mode")}",resources.getString(R.string.client_id),
                onSuccess = {Response ->
                    Log.i("debug","follow response: $Response")
                    parseLists(Response)
                },
                onFailed = {ErrorMessage ->
                    Log.i("error","getFollowList Error ->\n$ErrorMessage")
                })
    }

    private fun parseLists(jArray: JSONArray){
        val list: MutableList<FollowDC> = mutableListOf()
        for (i in 0 until jArray.length()){
            var mainObj = jArray.getJSONObject(i)
            var avatarUrls = mainObj.getJSONObject("profile_image")
            list.add((FollowDC(
                    mainObj.getString("name"),
                    avatarUrls.getString("large"),
                    mainObj.getString("username"),
                    mainObj.getString("twitter_username"),
                    mainObj.getString("instagram_username"),
                    mainObj.getString("bio")

            )))

        }
        initRecycler(list)
    }

    private fun initRecycler(list: MutableList<FollowDC>){
        follow_recycler.layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
        follow_recycler.adapter = RecyclerFollowAdapter(context,list)
    }

}// Required empty public constructor
