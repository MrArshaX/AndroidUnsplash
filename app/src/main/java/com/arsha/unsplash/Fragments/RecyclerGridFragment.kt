package com.arsha.unsplash.Fragments


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.arsha.unsplash.R
import com.arsha.unsplash.getJSONArray
import kotlinx.android.synthetic.main.fragment_recyclergrid.*
import org.json.JSONArray
import org.json.JSONObject
import android.widget.GridLayout.VERTICAL
import com.arsha.unsplash.RecyclerGridAdapter


/**
 * A simple [Fragment] subclass.
 */
class RecyclerGridFragment : Fragment() {


    companion object {
        fun newInstance(uName: String): RecyclerGridFragment{
            val fragment = RecyclerGridFragment()
            val bundle = Bundle()
            bundle.putString("uName",uName)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_recyclergrid, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getPhotos()

    }

    private fun getPhotos(){
        getJSONArray("users/${arguments!!.getString("uName")}/photos",resources.getString(R.string.client_id),
                onSuccess = {Response ->
                    parsePhotos(Response)
                },
                onFailed = {ErrorMessage ->
                    Log.i("error","getPhotos Error ->\n$ErrorMessage")
                })
    }

    private fun parsePhotos(jArray: JSONArray){
        var mainObject: JSONObject ; var urlsObject: JSONObject
        val links: MutableList<String> = ArrayList()
        for (i in 0 until jArray.length()){
            mainObject = jArray.getJSONObject(i)
            urlsObject = mainObject.getJSONObject("urls")
            links.add(urlsObject.getString("regular"))
        }
        initRecycler(links)
    }

    private fun initRecycler(links: MutableList<String>){
        prof_recyclerGrid.layoutManager = GridLayoutManager(context,3, VERTICAL, false)
        prof_recyclerGrid.adapter = RecyclerGridAdapter(context,links)
    }


}// Required empty public constructor
