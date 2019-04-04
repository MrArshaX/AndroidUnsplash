package com.arsha.unsplash.Utils
import android.content.Context
import android.view.View.MeasureSpec
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.widget.ImageView


/**
 * Created by Arsha on 3/25/2019.
 */
class ResizableImageView(context: Context, attrs: AttributeSet) : ImageView(context, attrs) {

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val d = getDrawable()

        if (d != null) {
            // ceil not round - avoid thin vertical gaps along the left/right edges
            val width = MeasureSpec.getSize(widthMeasureSpec)
            val height = Math.ceil((width.toFloat() * d!!.getIntrinsicHeight().toFloat() / d!!.getIntrinsicWidth().toFloat()).toDouble()).toInt()
            setMeasuredDimension(width, height)
        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        }
    }

}