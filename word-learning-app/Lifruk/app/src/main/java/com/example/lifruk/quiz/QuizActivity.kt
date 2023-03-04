package com.example.lifruk.quiz

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.lifruk.App
import com.example.lifruk.R
import com.example.lifruk.data.*
import com.example.lifruk.information.EndGameActivity
import com.example.lifruk.settings.SettingsActivity
import java.util.*
import java.util.Collections.shuffle


class QuizActivity : AppCompatActivity(), TextToSpeech.OnInitListener {

    companion object {
        const val EXTRA_TYPE = "selected_type"
        const val EXTRA_SCORE = "score"
    }

    private lateinit var selected_type: Type

    private var list10Questions: MutableList<Word> = mutableListOf()
    private var errorQuestions: MutableList<Word> = mutableListOf()

    private lateinit var currentWordToGuess: Word
    private var others3Words: MutableList<Word> = mutableListOf()
    private var allWords: MutableList<Word> = mutableListOf()

    private lateinit var topic: Topic
    private lateinit var player: Player

    private var topicID: Int = 0
    private var index: Int = 0
    private var score: Int = 0

    private var linkAudio: String = ""
    private lateinit var language: Language

    // View
    private lateinit var buttonNext: Button

    private lateinit var wordToGuessAudio: ImageButton
    private lateinit var wordToGuessPicture: TextView
    private lateinit var wordToGuessWord: ImageView

    private lateinit var answerWord: EditText

    private lateinit var wordShowGoodResponse: TextView

    private lateinit var picture1: ImageView
    private lateinit var picture2: ImageView
    private lateinit var picture3: ImageView
    private lateinit var picture4: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        getIntentExtra()
        player = if (App.player != null) {
            App.player!!
        } else {
            Player(-1, "", "", "", Language.ENGLISH, Language.ENGLISH, Difficulty.EASY)
        }

        getID()

        when (selected_type) {
            Type.WORD -> {
                setContentView(R.layout.a_q_guess_word)
                getViewByIdWord()
            }
            Type.AUDIO -> {
                tts = TextToSpeech(this, this)
                setContentView(R.layout.a_q_guess_audio)
                getViewByIdAudio()
            }
            Type.PICTURE -> {
                setContentView(R.layout.a_q_guess_picture)
                getViewByIdPicture()
            }
        }

        generateQuestions()
        //   buttonNext.setOnClickListener { nextQuestion() }
    }

    private var tts: TextToSpeech? = null
    override fun onInit(status: Int) {

        if (status == TextToSpeech.SUCCESS) {
            // set US English as language for tts
            var ukrainian = Locale("uk_UA")
            var french = Locale("fr_FR")
            val english = Locale("uk")
            var lithuanian = Locale("lv_LV")
            tts!!.isLanguageAvailable(Locale.FRENCH)
            tts!!.isLanguageAvailable(Locale("uk", "UA"))
            tts!!.isLanguageAvailable(Locale("lv", "LV"))
            tts!!.isLanguageAvailable(Locale.FRENCH)
            val result = tts!!.setLanguage(english)

            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "The Language specified is not supported!")
            }

        } else {
            Log.e("TTS", "Initilization Failed!")
        }
        Log.e("TTS", "Language loaded")
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        this.menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == R.id.settings) {
            val intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent)
        }
        return true
    }

    private fun speakOut() {
        tts!!.speak(currentWordToGuess.content, TextToSpeech.QUEUE_FLUSH, null, "")
    }

    private fun runaudio() {
        wordToGuessAudio.setOnClickListener {
            Log.i("clicked", "runaudio")
            speakOut()
        }
    }

    private fun getViewByIdAudio() {
        buttonNext = findViewById(R.id.buttonNext)
        picture1 = findViewById(R.id.pictureAudio1)
        picture2 = findViewById(R.id.pictureAudio2)
        picture3 = findViewById(R.id.pictureAudio3)
        picture4 = findViewById(R.id.pictureAudio4)
        wordToGuessAudio = findViewById(R.id.wordToGuessAudio)
    }

    private fun getViewByIdPicture() {
        buttonNext = findViewById(R.id.nextPicture)
        picture1 = findViewById(R.id.picturePicture1)
        picture2 = findViewById(R.id.picturePicture2)
        picture3 = findViewById(R.id.picturePicture3)
        picture4 = findViewById(R.id.picturePicture4)
        wordToGuessPicture = findViewById(R.id.wordToGuessPicture)
    }

    private fun getViewByIdWord() {
        buttonNext = findViewById(R.id.nextWord)
        answerWord = findViewById(R.id.responseWord)
        wordToGuessWord = findViewById(R.id.wordToGuessWord)
        wordShowGoodResponse = findViewById(R.id.wordShowGoodResponse)
    }

    private fun getIntentExtra() {
        selected_type = intent.getStringExtra(EXTRA_TYPE)?.let { toType(it) }!!
        //topic = topicViewModel.getTopicById(intent.getIntExtra(EXTRA_TOPIC, 0))
        topic = Topic(0, "COLOR", Difficulty.EASY)
    }

    private fun getID() {
        topicID = topic.id
        language = player.learning_l
    }

    private fun generateQuestions() {
        Log.i("player", player.id.toString())

        //checking the value of level set by user and setting data according to it
        val sharedPreferences = getSharedPreferences("Level", MODE_PRIVATE)
        val level = sharedPreferences.getString("level", "")
        if (level == getString(R.string.easy)) {
            list10Questions.addAll(
                listOf(
                    Word(
                        0,
                        Language.ENGLISH,
                        getString(R.string.blue),
                        0,
                        R.drawable.blue
                    )
                )
            )
            list10Questions.addAll(
                listOf(
                    Word(
                        1,
                        Language.ENGLISH,
                        getString(R.string.red),
                        0,
                        R.drawable.red
                    )
                )
            )
            list10Questions.addAll(
                listOf(
                    Word(
                        2,
                        Language.ENGLISH,
                        getString(R.string.yellow),
                        0,
                        R.drawable.yellow
                    )
                )
            )
            list10Questions.addAll(
                listOf(
                    Word(
                        3,
                        Language.ENGLISH,
                        getString(R.string.brown),
                        0,
                        R.drawable.brown
                    )
                )
            )
            list10Questions.addAll(
                listOf(
                    Word(
                        4,
                        Language.ENGLISH,
                        getString(R.string.pink),
                        0,
                        R.drawable.pink
                    )
                )
            )
        } else if (level == getString(R.string.medium)) {
            list10Questions.add(
                Word(
                    0,
                    Language.ENGLISH,
                    getString(R.string.chicken),
                    0,
                    R.drawable.a20
                )
            )
            list10Questions.add(
                Word(
                    1,
                    Language.ENGLISH,
                    getString(R.string.rabbit),
                    0,
                    R.drawable.a23
                )
            )
            list10Questions.add(
                Word(
                    2,
                    Language.ENGLISH,
                    getString(R.string.cow),
                    0,
                    R.drawable.a22
                )
            )
            list10Questions.add(
                Word(
                    3,
                    Language.ENGLISH,
                    getString(R.string.goat),
                    0,
                    R.drawable.a24
                )
            )
            list10Questions.add(
                Word(
                    4,
                    Language.ENGLISH,
                    getString(R.string.horse),
                    0,
                    R.drawable.a21
                )
            )
        } else if (level == getString(R.string.Difficult)) {
            list10Questions.add(
                Word(
                    0,
                    Language.ENGLISH,
                    getString(R.string.apple),
                    0,
                    R.drawable.m30
                )
            )
            list10Questions.add(
                Word(
                    1,
                    Language.ENGLISH,
                    getString(R.string.pear),
                    0,
                    R.drawable.m32
                )
            )
            list10Questions.add(
                Word(
                    2,
                    Language.ENGLISH,
                    getString(R.string.broccoli),
                    0,
                    R.drawable.m31
                )
            )
            list10Questions.add(
                Word(
                    3,
                    Language.ENGLISH,
                    getString(R.string.pizza),
                    0,
                    R.drawable.m33
                )
            )
            list10Questions.add(
                Word(
                    4,
                    Language.ENGLISH,
                    getString(R.string.meat),
                    0,
                    R.drawable.m34
                )
            )
        } else {
            list10Questions.addAll(
                listOf(
                    Word(
                        0,
                        Language.ENGLISH,
                        getString(R.string.blue),
                        0,
                        R.drawable.blue
                    )
                )
            )
            list10Questions.addAll(
                listOf(
                    Word(
                        1,
                        Language.ENGLISH,
                        getString(R.string.red),
                        0,
                        R.drawable.red
                    )
                )
            )
            list10Questions.addAll(
                listOf(
                    Word(
                        2,
                        Language.ENGLISH,
                        getString(R.string.yellow),
                        0,
                        R.drawable.yellow
                    )
                )
            )
            list10Questions.addAll(
                listOf(
                    Word(
                        3,
                        Language.ENGLISH,
                        getString(R.string.brown),
                        0,
                        R.drawable.brown
                    )
                )
            )
            list10Questions.addAll(
                listOf(
                    Word(
                        4,
                        Language.ENGLISH,
                        getString(R.string.pink),
                        0,
                        R.drawable.pink
                    )
                )
            )
        }

        //list10Questions.addAll(wordViewModel.getRandomQuestions(10 - errorQuestions.size, topicID, language))
        shuffle(list10Questions)
        nextQuestion()
    }

    private fun nextQuestion() {
        Log.i("obsi/nextQuestion", index.toString())
        if (index in 0..4) {
            Log.i("obsi/nextQuestion", "check next question")
            if (selected_type == Type.WORD) {
                newQuestionWord()
            } else {
                newQuestion()
            }
            //displayQuestion()
        } else if (index < 0) {
            Log.e("obsi/nextQuestion/error", "index<0")
        } else {
            Log.i("obsi/nextQuestion", "finishing game")
            endGame()
        }
    }

    private fun newQuestion() {
        currentWordToGuess = list10Questions[index]
        Log.i("obsi/newQuestion", currentWordToGuess.toString())
        allWords.clear()
        allWords.addAll(
            listOf(
                list10Questions.random(),
                list10Questions.random(),
                list10Questions.random()
            )
        )
        allWords.add(currentWordToGuess)
        shuffle(allWords)
        displayQuestion()
        index++
    }

    private fun newQuestionWord() {
        wordShowGoodResponse.visibility = View.INVISIBLE
        currentWordToGuess = list10Questions[index]
        displayWordGame()
        index++
    }

    private fun endGame() {
        Log.i("obsi/endGame", "end")
        intent = Intent(this, EndGameActivity::class.java)
        intent.putExtra(EXTRA_SCORE, score)
        startActivity(intent)
    }

    private fun displayQuestion() {
        when (selected_type) {
            Type.WORD -> displayWordGame()
            Type.AUDIO -> displayAudioGame()
            Type.PICTURE -> displayPictureGame()
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun resetBackground() {
        picture1.background = getDrawable(R.drawable.basic_border)
        picture2.background = getDrawable(R.drawable.basic_border)
        picture3.background = getDrawable(R.drawable.basic_border)
        picture4.background = getDrawable(R.drawable.basic_border)
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun display4Pictures() {
        resetBackground()
        picture1.setImageDrawable(getDrawable(allWords[0].picture))
        picture2.setImageDrawable(getDrawable(allWords[1].picture))
        picture3.setImageDrawable(getDrawable(allWords[2].picture))
        picture4.setImageDrawable(getDrawable(allWords[3].picture))
        picture1.setOnClickListener { checkImageResourceAndSetBorder(picture1) }
        picture2.setOnClickListener { checkImageResourceAndSetBorder(picture2) }
        picture3.setOnClickListener { checkImageResourceAndSetBorder(picture3) }
        picture4.setOnClickListener { checkImageResourceAndSetBorder(picture4) }
    }

    private fun displayAudioGame() {
        Log.i("obsi/displayAudioGame", currentWordToGuess.content)
        display4Pictures()
        buttonNext.setOnClickListener { nextQuestion() }
        runaudio()
    }

    private fun displayPictureGame() {
        display4Pictures()
        wordToGuessPicture.text = currentWordToGuess.content
        buttonNext.setOnClickListener { nextQuestion() }
        // wordToGuessPicture.text = currentWordToGuess.content
    }

    private fun checkAnswerWord() {
        wordShowGoodResponse.text = currentWordToGuess.content
        if (answerWord.text.toString().replace(Regex("\\s"), "") == currentWordToGuess.content) {
            wordShowGoodResponse.setTextColor(Color.GREEN)
            score++
        } else {
            wordShowGoodResponse.setTextColor(Color.RED)
        }
        wordShowGoodResponse.visibility = View.VISIBLE
        buttonNext.setOnClickListener { nextQuestion() }
    }

    private fun displayWordGame() {
        wordToGuessWord.setBackgroundResource(currentWordToGuess.picture)
        buttonNext.setOnClickListener { checkAnswerWord() }
    }

    @SuppressLint("UseCompatLoadingForDrawables", "LongLogTag")
    private fun checkImageResourceAndSetBorder(imageView: ImageView) {
        if (imageView.id == picture1.id && allWords[0].id == currentWordToGuess.id || imageView.id == picture2.id && allWords[1].id == currentWordToGuess.id || imageView.id == picture3.id && allWords[2].id == currentWordToGuess.id || imageView.id == picture4.id && allWords[3].id == currentWordToGuess.id) {
            imageView.background = getDrawable(R.drawable.green_border)
            Log.i("obsi/checkImageResourceAndSetBorder", "TRUE")
            score++
        } else {
            Log.i("obsi/checkImageResourceAndSetBorder", "FALSE")
            imageView.background = getDrawable(R.drawable.red_border)
            showGoodOne()
        }

        picture1.setOnClickListener { }
        picture2.setOnClickListener { }
        picture3.setOnClickListener { }
        picture4.setOnClickListener { }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun showGoodOne() {
        for (index in 0..3) {
            if (allWords[index].id == currentWordToGuess.id) {
                when (index) {
                    0 -> picture1.background = getDrawable(R.drawable.green_border)
                    1 -> picture2.background = getDrawable(R.drawable.green_border)
                    2 -> picture3.background = getDrawable(R.drawable.green_border)
                    3 -> picture4.background = getDrawable(R.drawable.green_border)
                }
            }
        }
    }

    private fun addAnswer(game: Game, word: Word, correct: Boolean, type: Type) {
        Log.i("obsi/addAnswer", "msg")
        //answerViewModel.insertAnswer(Answer(-1, word.id, game.id, correct, type))
    }
}
