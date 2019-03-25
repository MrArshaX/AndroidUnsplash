package com.arsha.unsplash
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.post_item.view.*

/**
 * Created by Arsha on 3/25/2019.
 */
class PostAdapter(val context: Context,val posts: MutableList<PostDC>): RecyclerView.Adapter<PostAdapter.MyHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        return MyHolder(LayoutInflater.from(context).inflate(com.arsha.unsplash.R.layout.post_item,parent,false),context)
    }

    override fun getItemCount() = posts.size

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        holder.setData(posts[position])
    }

    class MyHolder(itemView: View,val context: Context): RecyclerView.ViewHolder(itemView) {
        fun setData(post: PostDC){
            try {

                Glide.with(context).load(post.avatarUrl).into(itemView.post_userAvatar)
                itemView.post_username.text = post.fullName
                Glide.with(context).load(post.imageUrl).into(itemView.post_picture)
                itemView.post_likeCount.text = "${post.likes} likes"
                if(post.desc != "null"){
                    itemView.post_desc.visibility = View.VISIBLE
                    itemView.post_desc.text = post.desc
                }else{
                    itemView.post_desc.visibility = View.GONE
                }
            }catch (x: Exception){
                Log.i("error","PostAdapter Error ${x.message}")
            }
        }
    }
}