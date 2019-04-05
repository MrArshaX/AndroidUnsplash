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
import com.arsha.unsplash.Utils.DataClasses.FollowDC
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.follow_item.view.*
import kotlinx.android.synthetic.main.showprofile_dialog.*

/**
 * Created by Arsha on 3/25/2019.
 */


class RecyclerFollowAdapter(val context: Context?,private val persons: MutableList<FollowDC>): RecyclerView.Adapter<RecyclerFollowAdapter.MyHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        return MyHolder(LayoutInflater.from(context).inflate(R.layout.follow_item, parent, false), context)
    }

    override fun getItemCount() = persons.size

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        holder.setData(persons[position])
    }

    class MyHolder(itemView: View,val context: Context?): RecyclerView.ViewHolder(itemView) {
        fun setData(data: FollowDC) {
            try {
                if (context != null){
                    Glide.with(context).load(data.avatarUrl).into(itemView.followlist_avatar)
                }

                itemView.followlist_fullName.text = data.fullName
                itemView.followlist_userName.text = data.userName

                itemView.setOnClickListener({
                    showDialog(data)
                })

            } catch (x: Exception) {
                Log.i("error", "RecyclerPostAdapter Error ${x.message}")
            }
        }

        fun showDialog(data: FollowDC){
            val spDialog = Dialog(context)
            spDialog.setContentView(R.layout.showprofile_dialog)
            spDialog.window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            spDialog.spDialog_fullName.text = data.fullName
            if (context != null) {
                Glide.with(context).load(data.avatarUrl).thumbnail(1f).into(spDialog.spDialog_background)
                Glide.with(context).load(data.avatarUrl).into(spDialog.spDialog_Avatar)
            }
            spDialog.spDialog_gotoProfile.setOnClickListener({
                context?.startActivity(Intent(context, ProfileActivity::class.java).putExtra("userName",data.userName))
                spDialog.dismiss()
            })


            if (data.bio != "null"){
                spDialog.spDialog_bio.text = data.bio
            }else{
                spDialog.spDialog_bio.visibility = View.GONE
                spDialog.spDialog_bioTitle.visibility = View.GONE
            }
            if (data.twitter != "null"){
                spDialog.spDialog_twitterID.text = data.twitter
            }else{
                spDialog.spDialog_twitterID.visibility = View.GONE
                spDialog.spDialog_twitterIcon.visibility = View.GONE
            }
            if (data.instagram != "null"){
                spDialog.spDialog_instagramID.text = data.instagram
            }else{
                spDialog.spDialog_instagramID.visibility = View.GONE
                spDialog.spDialog_instagramIcon.visibility = View.GONE
            }

            spDialog.show()
        }
    }

}