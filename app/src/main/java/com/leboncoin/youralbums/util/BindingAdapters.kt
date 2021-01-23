package com.leboncoin.youralbums.util

import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import java.io.InputStream
import java.net.URL


/**
 * Binding adapter used to hide the spinner once data is available.
 */
@BindingAdapter("isNetworkError", "playlist")
fun hideIfNetworkError(view: View, isNetWorkError: Boolean, playlist: Any?) {
    view.visibility = if (playlist != null) View.GONE else View.VISIBLE

    if (isNetWorkError) {
        view.visibility = View.GONE
    }
}

/**
 * Binding adapter used to display images from URL using Glide
 */
/*@BindingAdapter("imageUrl")
fun setImageUrl(imageView: ImageView, url: String) {
    Glide.with(imageView.context).load(url).into(imageView)
}*/

/*@BindingAdapter("imageUrl")
fun bindImage(imgView: ImageView, imgUrl: String?) {
    imgUrl?.let {
        val imgUri = imgUrl.toUri().buildUpon().scheme("https").build()
        Glide.with(imgView.context)
                .load(imgUri)
                .apply(RequestOptions()
                        .placeholder(R.drawable.loading_animation)
                        .error(R.drawable.ic_broken_image))
                .into(imgView)
    }
}*/

@BindingAdapter("imageUrl")
fun setImageUrl(imageView: ImageView, url: String) {
    val drawable = loadImageFromWebOperations(url)
    imageView.setImageDrawable(drawable)
}

fun loadImageFromWebOperations(url: String?): Drawable? {
    return try {
        val `is`: InputStream = URL(url).content as InputStream
        Drawable.createFromStream(`is`, "src name")
    } catch (e: Exception) {
        null
    }
}
