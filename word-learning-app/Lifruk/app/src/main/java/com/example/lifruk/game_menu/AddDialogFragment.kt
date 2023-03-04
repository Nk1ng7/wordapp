package com.example.lifruk.game_menu

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.text.InputType
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import com.example.lifruk.R


class AddDialogFragment() : DialogFragment() {

    interface ConfirmAddDialogListener {
        fun onDialogPositiveClick(inputTextTopicName: String)
        fun onDialogNegativeClick()
    }

    var listener: ConfirmAddDialogListener? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            // Get the layout inflater
            val inflater = requireActivity().layoutInflater

            val editTextTopicName = EditText(context)
            with(editTextTopicName) {
                inputType = InputType.TYPE_CLASS_TEXT
                hint = "Color, Number..."
            }

            // Inflate and set the layout for the dialog
            // Pass null as the parent view because its going in the dialog layout
            builder.setView(inflater.inflate(R.layout.dialog_add_word, null))
                .setView(editTextTopicName)
                .setPositiveButton("Add",
                    DialogInterface.OnClickListener { dialog, id -> listener?.onDialogPositiveClick(editTextTopicName.text.toString()) })
                .setNegativeButton("Cancel",
                    DialogInterface.OnClickListener { dialog, id -> listener?.onDialogNegativeClick() })

            return builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}