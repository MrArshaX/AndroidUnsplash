package com.arsha.unsplash.Activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.util.Log
import android.view.View
import com.arsha.unsplash.Fragments.RecyclerGridFragment
import com.arsha.unsplash.Fragments.RecyclerviewFragment
import com.arsha.unsplash.R
import com.arsha.unsplash.Utils.Adapter.ProfPagerAdapter
import com.arsha.unsplash.Utils.getJSONObject
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_profile.*

import org.json.JSONObject

class ProfileActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        getUserName()
        prof_showMoreDetails.setOnClickListener({
            if (prof_showMoreDetailsLay.visibility == View.GONE){
                prof_showMoreDetailsLay.visibility =  View.VISIBLE
                prof_showMoreDetails.setImageDrawable(resources.getDrawable(R.drawable.ic_arrow_drop_up))
            } else{
                prof_showMoreDetailsLay.visibility =  View.GONE
                prof_showMoreDetails.setImageDrawable(resources.getDrawable(R.drawable.ic_arrow_drop_down))
            }
        })
        prof_back.setOnClickListener({
            onBackPressed()
        })
    }

    private fun getUserName() {
        try {
            getProfile(intent.getStringExtra("userName"))
            Log.i("debug","userName ->\n${intent.getStringExtra("userName")}")
            initViewpager(intent.getStringExtra("userName"))
        } catch (x: Exception) {
            Log.i("error", "getUserName error ->\n${x.message}")
        }
    }

    private fun getProfile(userName: String) {
        getJSONObject("users/$userName", resources.getString(R.string.client_id),
                onSuccess = { Response ->
                    Log.i("debug", "get Profile data ->\n$Response")
                    parseData(Response)
                },
                onFailed = { ErrorMessage ->
                    Log.i("error", "get Profile is failed ->\n$ErrorMessage")
                })
    }

    private fun parseData(obj: JSONObject){
        try {
            prof_userName.text = obj.getString("username")
            prof_fullName.text = obj.getString("name")

            if (obj.getString("bio") != "null"){
                prof_bio.text = obj.getString("bio")
            }else{
                prof_bio.visibility = View.GONE
            }

            prof_followersCount.text = obj.getString("followers_count")
            prof_followingCount.text = obj.getString("following_count")

            if (obj.getString("twitter_username") != "null"){
                prof_twitterId.text = obj.getString("twitter_username")
            }else{
                prof_twitterId.visibility = View.GONE
                prof_twitterIcon.visibility = View.GONE
            }

            if (obj.getString("instagram_username") != "null"){
                prof_instagramId.text = obj.getString("instagram_username")
            }else{
                prof_instagramId.visibility = View.GONE
                prof_instagramIcon.visibility = View.GONE
            }

            if (obj.getString("location") != "null"){
                prof_location.text = obj.getString("location")
            }else{
                prof_location.visibility = View.GONE
                prof_locationIcon.visibility = View.GONE
            }

            val avatars: JSONObject = obj.getJSONObject("profile_image")
            Glide.with(this).load(avatars.getString("large")).into(prof_avatar)

            if (obj.getString("portfolio_url") != "null"){
                prof_webUrl.text = obj.getString("portfolio_url")
            }else{
                prof_webUrl.visibility = View.GONE
            }

        }catch (x: Exception){
            Log.i("error","parse Data Error ->\n${x.message}")
        }
    }

    private fun initViewpager(userName: String){
        val adapter = ProfPagerAdapter(supportFragmentManager)
        adapter.addFragment(RecyclerGridFragment.newInstance(userName))
        adapter.addFragment(RecyclerviewFragment.newInstance(userName))
        prof_tabs.setupWithViewPager(prof_pager)
        prof_pager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(prof_tabs))
        prof_pager.adapter = adapter
        prof_tabs.getTabAt(0)!!.setIcon(R.drawable.ic_grid_gray)
        prof_tabs.getTabAt(1)!!.setIcon(R.drawable.ic_crop_original_gray)
    }

    fun gotoFollowActivity(v: View){
        when(v.id){
            R.id.prof_followersCount,R.id.prof_followersTitle -> startActivity(Intent(this,FollowActivity::class.java)
                    .putExtra("mode","followers").putExtra("uName",intent.getStringExtra("userName")))
            R.id.prof_followingCount,R.id.prof_followingTitle -> startActivity(Intent(this,FollowActivity::class.java)
                    .putExtra("mode","following").putExtra("uName",intent.getStringExtra("userName")))
        }
    }

}
