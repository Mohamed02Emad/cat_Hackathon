package com.team.cat_hackathon.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.viewpager.widget.PagerAdapter
import com.team.cat_hackathon.R
import com.team.cat_hackathon.data.models.OnBoarding
import kotlinx.coroutines.*

class OnBoardingAdapter(private val onBoardings: List<OnBoarding>) : PagerAdapter() {

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val itemView = LayoutInflater.from(container.context).inflate(
            R.layout.onboarding_card, container, false
        )
        val onBoarding = onBoardings[position]
        val imgView = itemView.findViewById<ImageView>(R.id.iv_onboarding)
        val txtView = itemView.findViewById<TextView>(R.id.tv_onboarding)

        imgView.setImageResource(onBoarding.image)
        txtView.text = onBoarding.title
        container.addView(itemView)
        return itemView
    }


    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun getCount(): Int {
        return onBoardings.size
    }


}
