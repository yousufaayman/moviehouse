package com.yousufaayman.moviehouse

import android.content.Context
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton


class ScrollListener(itemView: View, mContext: Context, private val fabScrollToTop: FloatingActionButton) : RecyclerView.OnScrollListener() {

    private val bannerImageView: ImageView = itemView.findViewById(R.id.bannerIV)
    private val fadeInAnimationTo50: Animation = AnimationUtils.loadAnimation(mContext, R.anim.fade_in)
    private val fadeInAnimation: Animation = AnimationUtils.loadAnimation(mContext, R.anim.fade_in)
    private val fadeOutAnimation: Animation = AnimationUtils.loadAnimation(mContext, R.anim.fade_out)
    private var isBannerVisible = true

    init {
        fadeInAnimation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) {
                bannerImageView.visibility = View.VISIBLE
                bannerImageView.alpha = 1.0f
            }

            override fun onAnimationEnd(animation: Animation?) {}

            override fun onAnimationRepeat(animation: Animation?) {}
        })

        fadeInAnimationTo50.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) {
                bannerImageView.visibility = View.VISIBLE
                bannerImageView.alpha = 0.1f
            }

            override fun onAnimationEnd(animation: Animation?) {}

            override fun onAnimationRepeat(animation: Animation?) {}
        })

        fadeOutAnimation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) {}

            override fun onAnimationEnd(animation: Animation?) {
                bannerImageView.visibility = View.GONE
                isBannerVisible = false
            }

            override fun onAnimationRepeat(animation: Animation?) {}
        })
    }

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        // Check if scrolling upwards
        val isScrollingUp = dy < 0

        // If scrolling up and the banner is not visible, show it with fadeInAnimation
        if (!recyclerView.canScrollVertically(-1)) {
            bannerImageView.visibility = View.VISIBLE
            bannerImageView.startAnimation(fadeInAnimation)
        }else if (isScrollingUp && !isBannerVisible) {
            isBannerVisible = true
        } else if (!isScrollingUp && isBannerVisible) {
            // If scrolling down and the banner is visible, hide it with fadeOutAnimation
            bannerImageView.startAnimation(fadeOutAnimation)
            isBannerVisible = false
        }

        if (dy > 0) {
            fabScrollToTop.hide()
        } else {
            fabScrollToTop.show()
        }
    }
}

