package com.os_tec.store.Classes

import android.app.Activity
import android.content.Context
import android.graphics.Typeface
import android.os.Build
import android.text.Html
import android.text.Spannable
import android.text.SpannableString
import android.text.method.LinkMovementMethod
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.text.style.TextAppearanceSpan
import android.util.Log
import android.util.TypedValue
import android.widget.TextView
import java.time.format.TextStyle

class SeeMoreTextView(val activity:Activity) {
 //Osama ...........................................................................................
    fun setText(
     textView: TextView,//TextView
     textContent:String,//textData
     color:String,// Int Color Like-> R.color.black
     textStyle:String,//text Style : NORMAL , BOLD , ITALIC
     moreText:String // textMessage Like -> SeeMore -> More ->See ->Show ->ViewMore
      ){

     if (textView.text.toString().endsWith(moreText)){
         textView.text=textContent
         textView.maxLines= Int.MAX_VALUE

     }else{
         if (textView.length()>200){
             handleTextView(textView,textContent,color,textStyle,moreText)
             Log.e("textView Count ", "setNew Text")
         } else{
             textView.text=textContent
             textView.maxLines= Int.MAX_VALUE
         }

     }



    }


    // handle text view and set maxLine
    private fun handleTextView(
        textView: TextView,
        textContent: String,
        color: String,
        textStyle: String,
        moreText: String
    ) {
        when (textStyle) {
            "NORMAL" -> {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    textView.text = Html.fromHtml(
                        textContent.substring(0, 200) + "<font color='${color}'> <ins>$moreText</ins></font>", 0)
                } else {
                    textView.text = Html.fromHtml(
                        textContent.substring(0, 200) + "<font color='${color}'> <ins>$moreText</ins></font>"
                    )

                }

            }
            "BOLD" -> {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    textView.text = Html.fromHtml(
                        textContent.substring(0, 200) + "<font color='${color}'> <b>$moreText</b></font>", 0)
                } else {
                    textView.text =
                        Html.fromHtml(textContent.substring(0, 200) + "<font color='${color}'> <b>$moreText</b></font>")

                }

            }
        }


    }








}

