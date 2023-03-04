package com.example.lifruk.flashcard


import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.lifruk.R


class AddNewWord : AppCompatActivity() {
    @SuppressLint("WrongViewCast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_add_word)


        val inflater = layoutInflater
        val dialogView = inflater.inflate(R.layout.dialog_add_word, null)
        val ewordEditText: EditText = dialogView.findViewById(R.id.eword)
        val enPronounciationEditText: EditText = dialogView.findViewById(R.id.en_pronounciation)
        val lwordEditText: EditText = dialogView.findViewById(R.id.lword)
        val lPronounciationEditText: EditText = dialogView.findViewById(R.id.l_pronounciation)
        val ukwordEditText: EditText = dialogView.findViewById(R.id.ukword)
        val ukPronounciationEditText: EditText = dialogView.findViewById(R.id.uk_pronounciation)


        //val builder
        //builder.setView(dialogView)

        val addButton: Button = findViewById(R.id.browse_button)
        addButton.setOnClickListener {
            // retrieve the input from the EditText fields
            val inputEword = ewordEditText.text.toString()
            val inputEnPronounciation = enPronounciationEditText.text.toString()
            val inputLword = lwordEditText.text.toString()
            val inputLPronounciation = lPronounciationEditText.text.toString()
            val inputUkword = ukwordEditText.text.toString()
            val inputUkPronounciation = ukPronounciationEditText.text.toString()

         
            ewordEditText.text.clear()
            enPronounciationEditText.text.clear()
            lwordEditText.text.clear()
            lPronounciationEditText.text.clear()
            ukwordEditText.text.clear()
            ukPronounciationEditText.text.clear()
        }
    }
}