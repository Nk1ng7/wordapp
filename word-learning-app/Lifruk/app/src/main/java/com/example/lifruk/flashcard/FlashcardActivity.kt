package com.example.lifruk.flashcard

import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.lifruk.App
import com.example.lifruk.R
import com.example.lifruk.data.*
import com.example.lifruk.quiz.QuizActivity
import com.example.lifruk.webserver.LifrukAPI
import java.util.*

class FlashcardActivity : AppCompatActivity(), TextToSpeech.OnInitListener {
    //, TextToSpeech.OnInitListener
    /*
    lateinit var flipIn: AnimatorSet
    lateinit var flipOut: AnimatorSet
    */
    private var isFront = false

    private lateinit var selected_type: Type

    private lateinit var buttonNext: Button

    private lateinit var randomWord: Word

    private lateinit var frontCardImage: ImageView
    private lateinit var frontCardText: TextView
    private lateinit var backCard: TextView

    private var topicID: Int = 0
    private var index: Int = 0

    private lateinit var player: Player

    private var allWords: MutableList<Word> = mutableListOf()
    private var allWordTranslation: MutableList<Word> = mutableListOf()
    // private var allWords: List<String> = listOf("blue", "red", "yellow")
    //private var allWordTranslation: List<String> = listOf("melyna", "raudona", "geltona")

    //private var allPictures: ListView<Image>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        tts = TextToSpeech(this, this)


        player = if (App.player != null) {
            App.player!!
        } else {
            Player(-1, "", "", "", Language.ENGLISH, Language.ENGLISH, Difficulty.EASY)
        }
        getIntentExtra()
        generateAllWords()
    }

    private fun generateAllWords() {

        this.applicationContext?.let {
            // do something with the random3Words
            allWords.clear()

            //checking the value of level set by user and setting data according to it
            val sharedPreferences = getSharedPreferences("Level", MODE_PRIVATE)
            val level = sharedPreferences.getString("level", "")
            if (level == getString(R.string.easy)) {
                allWords.add(
                    Word(
                        0,
                        Language.ENGLISH,
                        getString(R.string.blue),
                        0,
                        R.drawable.blue
                    )
                )
                allWords.add(Word(1, Language.ENGLISH, getString(R.string.red), 0, R.drawable.red))
                allWords.add(
                    Word(
                        2,
                        Language.ENGLISH,
                        getString(R.string.yellow),
                        0,
                        R.drawable.yellow
                    )
                )
                allWords.add(
                    Word(
                        2,
                        Language.ENGLISH,
                        getString(R.string.brown),
                        0,
                        R.drawable.brown
                    )
                )
                allWords.add(
                    Word(
                        2,
                        Language.ENGLISH,
                        getString(R.string.pink),
                        0,
                        R.drawable.pink
                    )
                )
            }
            else if(level==getString(R.string.medium)){
                allWords.add(
                    Word(
                        0,
                        Language.ENGLISH,
                        getString(R.string.chicken),
                        0,
                        R.drawable.a20
                    )
                )
                allWords.add(Word(1, Language.ENGLISH, getString(R.string.rabbit), 0, R.drawable.a23))
                allWords.add(
                    Word(
                        2,
                        Language.ENGLISH,
                        getString(R.string.cow),
                        0,
                        R.drawable.a22
                    )
                )
                allWords.add(
                    Word(
                        2,
                        Language.ENGLISH,
                        getString(R.string.goat),
                        0,
                        R.drawable.a24
                    )
                )
                allWords.add(
                    Word(
                        2,
                        Language.ENGLISH,
                        getString(R.string.horse),
                        0,
                        R.drawable.a21
                    )
                )
            }
            else if(level==getString(R.string.Difficult)){
                allWords.add(
                    Word(
                        0,
                        Language.ENGLISH,
                        getString(R.string.apple),
                        0,
                        R.drawable.m30
                    )
                )
                allWords.add(Word(1, Language.ENGLISH, getString(R.string.pear), 0, R.drawable.m32))
                allWords.add(
                    Word(
                        2,
                        Language.ENGLISH,
                        getString(R.string.broccoli),
                        0,
                        R.drawable.m31
                    )
                )
                allWords.add(
                    Word(
                        2,
                        Language.ENGLISH,
                        getString(R.string.pizza),
                        0,
                        R.drawable.m33
                    )
                )
                allWords.add(
                    Word(
                        2,
                        Language.ENGLISH,
                        getString(R.string.meat),
                        0,
                        R.drawable.m34
                    )
                )
            }
            else{
                allWords.add(
                    Word(
                        0,
                        Language.ENGLISH,
                        getString(R.string.blue),
                        0,
                        R.drawable.blue
                    )
                )
                allWords.add(Word(1, Language.ENGLISH, getString(R.string.red), 0, R.drawable.red))
                allWords.add(
                    Word(
                        2,
                        Language.ENGLISH,
                        getString(R.string.yellow),
                        0,
                        R.drawable.yellow
                    )
                )
                allWords.add(
                    Word(
                        2,
                        Language.ENGLISH,
                        getString(R.string.brown),
                        0,
                        R.drawable.brown
                    )
                )
                allWords.add(
                    Word(
                        2,
                        Language.ENGLISH,
                        getString(R.string.pink),
                        0,
                        R.drawable.pink
                    )
                )
            }
            this.applicationContext?.let {

                // do something with the random3Words
                allWordTranslation.clear()
                allWordTranslation.addAll(allWords)

                when (selected_type) {
                    Type.AUDIO -> {
                        setContentView(R.layout.a_flashcard_audio)
                        getViewByIdAudio()
                        //generateAudioCard()
                        initWord()
                        tts = TextToSpeech(this, this)
                        flipAudio()
                        nextCard()
                    }
                    Type.PICTURE -> {
                        setContentView(R.layout.a_flashcard_picture)
                        getViewByIdPicture()
                        initWordPicture()
                        flipPicture()
                        nextCard()
                    }
                    Type.WORD -> {
                        setContentView(R.layout.a_flashcard_text)
                        getViewByIdWord()
                        generateTextCard()
                        flipText()
                        nextCard()
                    }
                }

            }
        }
    }

    private fun getViewByIdAudio() {
        buttonNext = findViewById(R.id.next_button_audio)
        frontCardImage = findViewById(R.id.flashcard_audio_button)
        backCard = findViewById(R.id.flashcard_back_audio_text)
    }

    private fun getViewByIdPicture() {
        buttonNext = findViewById(R.id.next_button_picture)
        frontCardImage = findViewById(R.id.flashcard_front_picture)
        backCard = findViewById(R.id.flashcard_picture_back_text)
    }

    private fun getViewByIdWord() {
        buttonNext = findViewById(R.id.next_button_text)
        frontCardText = findViewById(R.id.flashcard_front_text)
        backCard = findViewById(R.id.flashcard_back_text)
    }

    private fun generateTextCard() {
        randomWord = allWords.random()
        val wordIndex = allWords.indexOf(randomWord)
        val translation = allWordTranslation[wordIndex]
        frontCardText.text = randomWord.content
        backCard.text = translation.content
    }

    private fun initWord() {
        randomWord = allWords.random()
        val wordIndex = allWords.indexOf(randomWord)
        val translation = allWordTranslation[wordIndex]
        backCard.text = translation.content
    }

    private fun initWordPicture() {
        randomWord = allWords.random()
        val wordIndex = allWords.indexOf(randomWord)
        val translation = allWordTranslation[wordIndex]
        frontCardImage.setBackgroundResource(randomWord.picture)
        backCard.text = translation.content
    }

    private fun nextCard() {
        when (selected_type) {
            Type.AUDIO -> {
                buttonNext.setOnClickListener {
                    initWord()
                    tts = TextToSpeech(this, this)
                }
            }
            Type.PICTURE -> {
                buttonNext.setOnClickListener {
                    initWordPicture()
                }
            }
            Type.WORD -> {
                buttonNext.setOnClickListener {
                    generateTextCard()
                }
            }
        }
    }


    private fun addWord() {

        val word: Word = Word(160, Language.ENGLISH, "test", 0, 11)
        this.applicationContext?.let {
            LifrukAPI.getInstance(it).addWord(word, { response ->
                // handle the response
                Toast.makeText(
                    it, "Word ${response.get("content")} just created !", Toast.LENGTH_SHORT
                ).show()
            }, { error ->
                // handle the error
                error.message?.let { msg -> Log.e("obsi/FlashcardActivity", msg) }
                Toast.makeText(it, "Cannot add word", Toast.LENGTH_SHORT).show()
            })
        }
    }

    private fun runaudio() {
        val flashcard_audio_button: ImageButton = findViewById(R.id.flashcard_audio_button)
        flashcard_audio_button.setOnClickListener {
            Log.i("clicked", "runaudio")
            speakOut()
        }
    }

    private var tts: TextToSpeech? = null

    override fun onInit(status: Int) {

        if (status == TextToSpeech.SUCCESS) {
            // set US English as language for tts
            var ukrainian = Locale("uk_UA")
            var french = Locale("fr_FR")
            var english = Locale("uk")
            var lithuanian = Locale("lv_LV")
            tts!!.isLanguageAvailable(Locale.FRENCH)
            tts!!.isLanguageAvailable(Locale("uk", "UA"))
            tts!!.isLanguageAvailable(Locale("lv", "LV"))
            val result = tts!!.setLanguage(english)

            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "The Language specified is not supported!")
            }

        } else {
            Log.e("TTS", "Initilization Failed!")
        }

    }

    private fun speakOut() {
        tts!!.speak(randomWord.content, TextToSpeech.QUEUE_FLUSH, null, "")
    }

    private fun flipPicture() {
        val flipButtonPicture: Button = findViewById(R.id.flip_button_picture)
        val flashcardFrontPicture: ImageView = findViewById(R.id.flashcard_front_picture)
        val flashcardPictureBackText: TextView = findViewById(R.id.flashcard_picture_back_text)

        flipButtonPicture.setOnClickListener {
            if (isFront) {
                flashcardFrontPicture.visibility = View.VISIBLE
                flashcardPictureBackText.visibility = View.INVISIBLE
                isFront = false
            } else {
                flashcardPictureBackText.visibility = View.VISIBLE
                flashcardFrontPicture.visibility = View.INVISIBLE
                isFront = true
            }
        }
    }


    private fun flipText() {
        val flipButtonText: Button = findViewById(R.id.flip_button_text)
        val flashcardFrontText: TextView = findViewById(R.id.flashcard_front_text)
        val flashcardBackText: TextView = findViewById(R.id.flashcard_back_text)

        flashcardBackText.visibility = View.INVISIBLE
        flipButtonText.setOnClickListener {
            if (isFront) {
                flashcardBackText.visibility = View.INVISIBLE
                flashcardFrontText.visibility = View.VISIBLE
                isFront = false
            } else {
                flashcardFrontText.visibility = View.INVISIBLE
                flashcardBackText.visibility = View.VISIBLE
                isFront = true
            }
        }
    }

    private fun flipAudio() {
        val flipButtonAudio: Button = findViewById(R.id.flip_button_audio)
        val flashcardFrontAudio: ImageView = findViewById(R.id.flashcard_audio_button)
        val flashcardBackAudioText: TextView = findViewById(R.id.flashcard_back_audio_text)

        flashcardBackAudioText.visibility = View.INVISIBLE
        flipButtonAudio.setOnClickListener {
            if (isFront) {
                flashcardFrontAudio.visibility = View.VISIBLE
                flashcardBackAudioText.visibility = View.INVISIBLE
                isFront = false
            } else {
                if (index == 0) {
                    speakOut()
                    index = 1
                } else {
                    flashcardBackAudioText.visibility = View.VISIBLE
                    flashcardFrontAudio.visibility = View.INVISIBLE
                    isFront = true
                    index = 0
                }
            }
        }
    }


    private fun getIntentExtra() {
        selected_type = intent.getStringExtra(QuizActivity.EXTRA_TYPE)?.let { toType(it) }!!
        topicID = 0
    }
}
