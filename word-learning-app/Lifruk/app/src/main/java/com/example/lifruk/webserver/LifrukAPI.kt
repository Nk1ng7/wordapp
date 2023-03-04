package com.example.lifruk.webserver

import android.content.Context
import android.util.Log
import com.android.volley.AuthFailureError
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.lifruk.BuildConfig
import com.example.lifruk.data.*
import org.json.JSONArray
import org.json.JSONObject

class LifrukAPI private constructor(context: Context) {
    private val appContext: Context = context.applicationContext

    private val protocol: String = BuildConfig.webserver_protocol
    private val location: String = BuildConfig.webserver_location
    private val port: String = BuildConfig.webserver_port
    private val accessPoint: String = "$protocol://$location:$port"

    private val queue = Volley.newRequestQueue(appContext)

    companion object {
        private lateinit var instance: LifrukAPI

        fun getInstance(context: Context): LifrukAPI {
            if (!::instance.isInitialized) {
                instance = LifrukAPI(context)
            }
            return instance
        }
    }

    private fun executePOSTJSONRequest(
        route: String,
        payload: JSONObject,
        successHandler: Response.Listener<JSONObject>,
        errorHandler: Response.ErrorListener?
    ) {
        val request = object : JsonObjectRequest(
            Method.POST, "$accessPoint/$route", payload, successHandler, errorHandler
        ) {
            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String> {
                val headers = HashMap<String, String>()
                headers["Content-Type"] = "application/json"
                return headers
            }
        }
        Log.d("VolleyRequest", "Executing request to $accessPoint/$route, payload: $payload")
        this.queue.add(request)
    }

    private fun executeGETJSONRequest(
        route: String,
        successHandler: Response.Listener<JSONObject>,
        errorHandler: Response.ErrorListener?
    ) {
        val request = object : JsonObjectRequest(
            Method.GET, "$accessPoint/$route", null, successHandler, errorHandler
        ) {
            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String> {
                val headers = HashMap<String, String>()
                headers["Content-Type"] = "application/json"
                return headers
            }
        }
        Log.d("VolleyRequest", "Executing request to $accessPoint/$route")
        this.queue.add(request)
    }

    private fun executeGETJSONArrayRequest(
        route: String,
        successHandler: Response.Listener<JSONArray>,
        errorHandler: Response.ErrorListener?
    ) {
        val request = object : JsonArrayRequest(
            Method.GET, "$accessPoint/$route", null, successHandler, errorHandler
        ) {
            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String> {
                val headers = HashMap<String, String>()
                headers["Content-Type"] = "application/json"
                return headers
            }
        }
        Log.d("VolleyRequest", "Executing request to $accessPoint/$route")
        this.queue.add(request)
    }

    fun registerPlayer(
        player: Player,
        successHandler: Response.Listener<JSONObject>,
        errorHandler: Response.ErrorListener?
    ) {
        executePOSTJSONRequest("player/create", player.asJSON(), successHandler, errorHandler)
    }

    fun addWord(
        word: Word,
        successHandler: Response.Listener<JSONObject>,
        errorHandler: Response.ErrorListener?
    ) {
        executePOSTJSONRequest("word/insertWord", word.asJSON(), successHandler, errorHandler)
    }

    fun logPlayerIn(
        email: String,
        password: String,
        successHandler: Response.Listener<JSONObject>,
        errorHandler: Response.ErrorListener?
    ) {
        executeGETJSONRequest("player/playerByEmailAndPwd/${email}/${password}", successHandler, errorHandler)
    }


    fun updatePlayerLanguage(
        playerID: Int,
        language: String,
        successHandler: Response.Listener<JSONObject>,
        errorHandler: Response.ErrorListener?
    ) {
        executeGETJSONRequest("player/updatePlayerL/${playerID}/${language}", successHandler, errorHandler)
    }

    fun getAllTopics(success: (List<Topic>) -> Unit, failure: (String) -> Unit) {
        executeGETJSONArrayRequest("topic/allTopics", { response ->
            // handle the response
            val topics = mutableListOf<Topic>()
            for (i in 0 until response.length()) {
                val topicJson = response.getJSONObject(i)
                val topic = Topic(
                    topicJson.getInt("id"),
                    topicJson.getString("name"),
                    toDifficulty(topicJson.getString("difficulty"))
                )
                topics.add(topic)
            }
            success(topics)
        }, { error ->
            // handle the error
            failure(error.message ?: "An unknown error occurred")
        })
    }

    fun getBadQuestions(
        topicId: Int,
        language: String,
        playerID: Int,
        success: (List<Word>) -> Unit,
        failure: (String) -> Unit
    ) {
        val route = "word/badQuestions/$topicId/$language/$playerID"
        executeGETJSONArrayRequest(route, { response ->
            // handle the response
            val badQuestions = mutableListOf<Word>()
            for (i in 0 until response.length()) {
                val badQuestionJson = response.getJSONObject(i)
                val badQuestion = Word(
                    badQuestionJson.getInt("id"),
                    toLanguage(badQuestionJson.getString("language")),
                    badQuestionJson.getString("content"),
                    badQuestionJson.getInt("topicID"),
                    badQuestionJson.getInt("picture")
                )
                badQuestions.add(badQuestion)
            }
            success(badQuestions)
        }, { error ->
            // handle the error
            failure(error.message ?: "An unknown error occurred")
        })
    }


    fun getRandomQuestions(
        topicId: Int,
        language: String,
        nb: Int,
        success: (List<Word>) -> Unit,
        failure: (String) -> Unit
    ) {
        val route = "word/randomQuestions/$topicId/$language/$nb"
        executeGETJSONArrayRequest(route, { response ->
            // handle the response
            val badQuestions = mutableListOf<Word>()
            for (i in 0 until response.length()) {
                val badQuestionJson = response.getJSONObject(i)
                val badQuestion = Word(
                    badQuestionJson.getInt("id"),
                    toLanguage(badQuestionJson.getString("language")),
                    badQuestionJson.getString("content"),
                    badQuestionJson.getInt("topicID"),
                    badQuestionJson.getInt("picture")
                )
                badQuestions.add(badQuestion)
            }
            success(badQuestions)
        }, { error ->
            // handle the error
            failure(error.message ?: "An unknown error occurred")
        })
    }

    fun getRandom3Words(
        topicId: Int,
        language: String,
        wordAnswerID: Int,
        success: (List<Word>) -> Unit,
        failure: (String) -> Unit
    ) {
        val route = "word/random3Words/$topicId/$language/$wordAnswerID"
        executeGETJSONArrayRequest(route, { response ->
            // handle the response
            val badQuestions = mutableListOf<Word>()
            for (i in 0 until response.length()) {
                val badQuestionJson = response.getJSONObject(i)
                val badQuestion = Word(
                    badQuestionJson.getInt("id"),
                    toLanguage(badQuestionJson.getString("language")),
                    badQuestionJson.getString("content"),
                    badQuestionJson.getInt("topicID"),
                    badQuestionJson.getInt("picture")
                )
                badQuestions.add(badQuestion)
            }
            success(badQuestions)
        }, { error ->
            // handle the error
            failure(error.message ?: "An unknown error occurred")
        })
    }

    fun getFlashcardByTopic(
        topicId: Int,
        language: String,
        success: (List<Word>) -> Unit,
        failure: (String) -> Unit
    ) {
        val route = "flashcard/flashcardByTopic/$topicId/$language"
        executeGETJSONArrayRequest(route, { response ->
            // handle the response
            val flashcardWords = mutableListOf<Word>()
            for (i in 0 until response.length()) {
                val flashcardWordsJson = response.getJSONObject(i)
                val flashcardWord = Word(
                    flashcardWordsJson.getInt("id"),
                    toLanguage(flashcardWordsJson.getString("language")),
                    flashcardWordsJson.getString("content"),
                    flashcardWordsJson.getInt("topicID"),
                    flashcardWordsJson.getInt("picture")
                )
                flashcardWords.add(flashcardWord)
            }
            success(flashcardWords)
        }, { error ->
            // handle the error
            failure(error.message ?: "An unknown error occurred")
        })
    }
}