package com.application.knc.Utils

import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import android.os.Build
import android.text.Html
import android.text.Spanned
import android.widget.ImageView
import android.widget.TextView
import androidx.core.text.HtmlCompat
import androidx.databinding.BindingAdapter
import com.application.knc.R
import com.google.android.material.textfield.TextInputLayout
import com.squareup.picasso.Picasso


@BindingAdapter("app:passwordToggleEnabled")
fun setPasswordToggleEnabled(textInputLayout: TextInputLayout, enabled: Boolean) {
    textInputLayout.endIconMode =
        if (enabled) TextInputLayout.END_ICON_PASSWORD_TOGGLE else TextInputLayout.END_ICON_NONE
    textInputLayout.editText?.let {
        it.inputType =
            if (enabled) android.text.InputType.TYPE_CLASS_TEXT or android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD
            else android.text.InputType.TYPE_CLASS_TEXT
    }
}



@SuppressLint("UseCompatLoadingForDrawables")
@BindingAdapter("app:netImageSrc","app:placeholder", requireAll = false)
fun loadImage(view: ImageView, url:String?, placeholder: Drawable?)
{
    try
    {
        Picasso.get().load(url).resize(800,800).onlyScaleDown().placeholder((placeholder?:view.context.getDrawable(R.drawable.img_loading))!!).error(R.drawable.img_logo).into(view)
    } catch (e: Exception)
    {
        view.setImageDrawable(placeholder?:view.context.getDrawable(R.drawable.img_logo))
    }
}

@BindingAdapter("app:htmlText")
fun loadHtml(textView: TextView, content: String?){
    if (!content.isNullOrEmpty()) {
        textView.text = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Html.fromHtml(content, HtmlCompat.FROM_HTML_MODE_LEGACY)
        } else {
            Html.fromHtml(content)
        }
    }
}