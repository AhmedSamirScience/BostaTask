package com.task.bosta.utils.widgets

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.task.bosta.R
import timber.log.Timber

object CustomAlertDialog {
    fun showMyCustomDialog(activity: AppCompatActivity, type: Type, btnTitle: String, msg: String, block: (() -> Unit)?){
        val dialog = Dialog(activity)
        if (type == Type.ERROR){
            dialog.setContentView(R.layout.custom_error_dialog)
        }else{
            dialog.setContentView(R.layout.custom_alert)
        }
        dialog.setCancelable(false)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val btn: AppCompatButton = dialog.findViewById(R.id.dialog_btn)
        val msgText: TextView = dialog.findViewById(R.id.dialog_msg)
        if (btnTitle.isNotEmpty()){
            btn.text = btnTitle
        }
        msgText.text = msg
        btn.setOnClickListener {
            dialog.dismiss()
            block?.invoke()
        }
        dialog.show()
    }




    fun showGift(activity: AppCompatActivity, image: String) {
        try {
            Timber.e("Inside Dialog")
            val dialog = Dialog(activity)

            dialog.setContentView(R.layout.image_viewer)

            dialog.setCancelable(false)
            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            val ok = dialog.findViewById<LinearLayout>(R.id.close_dialog)
            val voucherImage = dialog.findViewById<ImageView>(R.id.image)

                Glide.with(activity).load(image+".png")
                    .apply(RequestOptions().error(R.drawable.ic_error))
                    .into(voucherImage)
                ok.setOnClickListener {
                    dialog.dismiss()
                }
            dialog.show()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    enum class Type {
        ERROR, SUCCESS
    }

}