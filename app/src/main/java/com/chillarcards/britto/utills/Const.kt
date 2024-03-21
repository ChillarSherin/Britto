package com.chillarcards.britto.utills

import android.content.Context
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import com.chillarcards.britto.R
import com.chillarcards.britto.ui.DummyOrderItems

class Const {
    companion object {


        const val ver_title = ":  " //Client
        const val currency = ":  " //Client
        private lateinit var prefManager: PrefManager
        var cartItems: MutableList<DummyOrderItems> = mutableListOf()

      //  var CartItems: List<DummyOrderItems> = ArrayList()

        fun enableButton(button: Button) {
            button.isEnabled = true
            button.alpha = 1f
        }

        fun disableButton(button: Button) {
            button.isEnabled = false
            button.alpha = 0.55f
        }

        fun shortToast(context: Context, value: String) {
            //Toast.makeText(context, value, Toast.LENGTH_SHORT).show()
            val builder = AlertDialog.Builder(context)
            //set message for alert dialog
            builder.setMessage(value)
            //performing negative action
            builder.setNegativeButton(context.getString(R.string.ok)) { dialogInterface, _ ->
                dialogInterface.dismiss()
            }
            // Create the AlertDialog
            val alertDialog: AlertDialog = builder.create()
            // Set other dialog properties
            alertDialog.setOnShowListener {
                alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(context.resources.getColor(R.color.primary_red)
                )
            }
            alertDialog.setCanceledOnTouchOutside(false)
            alertDialog.show()
        }

    }
}