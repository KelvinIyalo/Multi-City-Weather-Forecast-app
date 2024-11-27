package com.kelvin.weatherappkelvin.presentation.dialog

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.kelvin.weatherappkelvin.R

@SuppressLint( "MissingInflatedId")
fun Context.showAlertDialog(
    onContinueButton: () -> Unit,
    onCancelButton: () -> Unit ={},
    warningMessage: String
) {
    val dialogBuilder = AlertDialog.Builder(this)
    val popupView = LayoutInflater.from(this).inflate(R.layout.warning_dialog, null)
    val tryAgainButton = popupView.findViewById<TextView>(R.id.continue_btn)
    val cancelButton = popupView.findViewById<TextView>(R.id.cancel_button)
    val message = popupView.findViewById<TextView>(R.id.warning_message)
    message.text = warningMessage


    dialogBuilder.setView(popupView)
    dialogBuilder.setCancelable(false)
    val dialog = dialogBuilder.create()
    dialog.show()

    cancelButton.setOnClickListener {
        dialog.dismiss()
        onCancelButton.invoke()
    }

    tryAgainButton.setOnClickListener {
        dialog.dismiss()
        onContinueButton.invoke()
    }

}