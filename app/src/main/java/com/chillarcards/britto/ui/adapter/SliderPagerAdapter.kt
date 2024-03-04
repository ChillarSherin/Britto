package com.chillarcards.britto.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.viewpager.widget.PagerAdapter
import com.chillarcards.britto.R
import com.chillarcards.britto.ui.DummyImage
import com.chillarcards.britto.utills.PrefManager
import com.squareup.picasso.Picasso


class SliderPagerAdapter(var context: Context, images: List<DummyImage>) :
    PagerAdapter() {
    private val images: List<DummyImage>
    var layoutInflater: LayoutInflater
    var prefManager: PrefManager

    init {
        this.images = images
        layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        prefManager = PrefManager(context)
    }

    override fun getCount(): Int {
        return images.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object`
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val itemView: View = layoutInflater.inflate(R.layout.item_viewpager, container, false)
        val imageView = itemView.findViewById<ImageView>(R.id.imageView)

        Picasso.get().load(images[position].name)
            .placeholder(R.drawable.place_holder)
            .error(R.drawable.place_holder) //  .transform(RoundCornersTransform(32.0f))
            .into(imageView)

        container.addView(itemView)

        return itemView
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as ConstraintLayout)
    }
}