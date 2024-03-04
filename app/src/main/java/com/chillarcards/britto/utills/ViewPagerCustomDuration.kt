package com.chillarcards.britto.utills

import android.content.Context
import android.util.AttributeSet
import android.view.animation.Interpolator
import androidx.viewpager.widget.ViewPager


class ViewPagerCustomDuration : ViewPager {
    constructor(context: Context?) : super(context!!) {
        postInitViewPager()
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(
        context!!, attrs
    ) {
        postInitViewPager()
    }

    private var mScroller: ScrollerCustomDuration? = null

    /**
     * Override the Scroller instance with our own class so we can change the
     * duration
     */
    @Throws(NoSuchFieldException::class, IllegalAccessException::class)
    private fun postInitViewPager() {
        val scroller = ViewPager::class.java.getDeclaredField("mScroller")
        scroller.isAccessible = true
        val interpolator = ViewPager::class.java.getDeclaredField("sInterpolator")
        interpolator.isAccessible = true
        mScroller = ScrollerCustomDuration(
            context,
            interpolator[null] as Interpolator
        )
        scroller[this] = mScroller
    }

    /**
     * Set the factor by which the duration will change
     */
    fun setScrollDurationFactor(scrollFactor: Double) {
        mScroller!!.setScrollDurationFactor(scrollFactor)
    }
}

