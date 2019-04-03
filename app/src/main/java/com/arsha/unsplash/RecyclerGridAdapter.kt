package com.arsha.unsplash

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.recyclergrid_item.view.*

/**
 * Created by Arsha on 4/3/2019.
 */
class RecyclerGridAdapter(val context: Context?, val links: MutableList<String>) : RecyclerView.Adapter<RecyclerGridAdapter.MyHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        val view: View = LayoutInflater.from(context).inflate(R.layout.recyclergrid_item,parent,false)
        return MyHolder(view)
    }

    override fun getItemCount(): Int = links.size

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        holder.setData(links[position])
    }

    class MyHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        fun setData(link: String){
            Glide.with(itemView.context).load(link).into(itemView.rgi_mainPicture)
        }
    }
}