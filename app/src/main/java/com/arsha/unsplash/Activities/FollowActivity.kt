package com.arsha.unsplash.Activities

import android.app.FragmentManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.TabLayout
import com.arsha.unsplash.Fragments.FollowFragment
import com.arsha.unsplash.R
import com.arsha.unsplash.Utils.Adapter.FollowPagerAdapter
import kotlinx.android.synthetic.main.activity_follow.*

class FollowActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_follow)
        follow_back.setOnClickListener {
            onBackPressed()
        }
        getExtras()
    }
    private fun getExtras(){
        val uName = intent.getStringExtra("uName")
        val mode = intent.getStringExtra("mode")
        follow_userName.text = uName
        initViewPager(uName,mode)
    }

    private fun initViewPager(uName: String, mode: String){
        val adapter = FollowPagerAdapter(supportFragmentManager)
        adapter.addFragment(FollowFragment.newInstance(uName, "followers"),"Followers")
        adapter.addFragment(FollowFragment.newInstance(uName, "following"),"Following")
        follow_tabs.setupWithViewPager(follow_pager)
        follow_pager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(follow_tabs))
        follow_pager.adapter = adapter

        when(mode){
            "followers" -> follow_pager.currentItem = 0
            "following" -> follow_pager.currentItem = 1
        }
    }

}
