package com.arsha.unsplash.Utils.Adapter
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arsha.unsplash.Activities.ProfileActivity
import com.arsha.unsplash.R
import com.arsha.unsplash.Utils.DataClasses.PostDC
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.post_item.view.*
import kotlinx.android.synthetic.main.showprofile_dialog.*

/**
 * Created by Arsha on 3/25/2019.
 */
enum class PostMode{
    NORMAL,PROFILE
}

class PostAdapter(val context: Context?,val posts: MutableList<PostDC>, val mode: PostMode): RecyclerView.Adapter<PostAdapter.MyHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        return MyHolder(LayoutInflater.from(context).inflate(R.layout.post_item, parent, false), context)
    }

    override fun getItemCount() = posts.size

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        holder.setData(posts[position],mode)
    }

    class MyHolder(itemView: View,val context: Context?): RecyclerView.ViewHolder(itemView) {
        fun setData(post: PostDC, mode: PostMode) {
            try {
                when(mode){
                    PostMode.NORMAL -> initNormalPostMode(post)
                    PostMode.PROFILE -> initProfilePostMode(post)
                }

            } catch (x: Exception) {
                Log.i("error", "PostAdapter Error ${x.message}")
            }
        }

        private fun initNormalPostMode(post: PostDC){

            if (context != null){
            Glide.with(context).load(post.avatarUrl).into(itemView.post_userAvatar)
            Glide.with(context).load(post.imageUrl).into(itemView.post_picture)
            }

            itemView.post_username.text = post.fullName
            itemView.post_likeCount.text = "${post.likes} likes"
            if (post.desc != "null") {
                itemView.post_desc.visibility = View.VISIBLE
                itemView.post_desc.text = post.desc
            } else {
                itemView.post_desc.visibility = View.GONE
            }
            itemView.post_username.setOnClickListener({
                showDialog(post)
            })
            itemView.post_userAvatar.setOnClickListener({
                showDialog(post)
            })
        }

        private fun initProfilePostMode(post: PostDC){
            if (context != null) {
                Glide.with(context).load(post.imageUrl).into(itemView.post_picture)
            }
            itemView.post_header.visibility = View.GONE
            itemView.post_likeCount.text = "${post.likes} likes"
            if (post.desc != "null") {
                itemView.post_desc.visibility = View.VISIBLE
                itemView.post_desc.text = post.desc
            } else {
                itemView.post_desc.visibility = View.GONE
            }
        }

        fun showDialog(post: PostDC){
            val spDialog = Dialog(context)
            spDialog.setContentView(R.layout.showprofile_dialog)
            spDialog.window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            spDialog.spDialog_fullName.text = post.fullName
            if (context != null) {
                Glide.with(context).load(post.avatarUrl).thumbnail(1f).into(spDialog.spDialog_background)
                Glide.with(context).load(post.avatarUrl).into(spDialog.spDialog_Avatar)
            }
            spDialog.spDialog_gotoProfile.setOnClickListener({
                context?.startActivity(Intent(context, ProfileActivity::class.java).putExtra("userName",post.userName))
                spDialog.dismiss()
            })


            if (post.bio != "null"){
                spDialog.spDialog_bio.text = post.bio
            }else{
                spDialog.spDialog_bio.visibility = View.GONE
                spDialog.spDialog_bioTitle.visibility = View.GONE
            }
            if (post.twitter != "null"){
                spDialog.spDialog_twitterID.text = post.twitter
            }else{
                spDialog.spDialog_twitterID.visibility = View.GONE
                spDialog.spDialog_twitterIcon.visibility = View.GONE
            }
            if (post.instagram != "null"){
                spDialog.spDialog_instagramID.text = post.instagram
            }else{
                spDialog.spDialog_instagramID.visibility = View.GONE
                spDialog.spDialog_instagramIcon.visibility = View.GONE
            }

            spDialog.show()
        }
    }
}