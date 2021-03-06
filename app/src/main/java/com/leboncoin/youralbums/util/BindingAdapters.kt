package com.leboncoin.youralbums.util

import android.view.View
import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.leboncoin.youralbums.R


/**
 * Binding adapter used to hide the spinner once data is available or no internet
 */
@BindingAdapter("isNetworkError", "datalist")
fun hideIfNetworkError(view: View, isNetWorkError: Boolean, dataList: Any?) {
    view.visibility = if (dataList != null) View.GONE else View.VISIBLE

    if (isNetWorkError) {
        view.visibility = View.GONE
    }
}

@BindingAdapter("imageUrl")
fun bindImage(imgView: ImageView, imgUrl: String?) {
    imgUrl?.let {
        val imgUri = imgUrl.toUri().buildUpon().scheme("https").build()
        Glide.with(imgView.context).load(imgUri).apply(
            RequestOptions()
                .placeholder(R.drawable.loading_animation)
                .error(R.drawable.ic_broken_image)
        )
            .into(imgView)
    }
}
