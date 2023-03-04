package com.example.lifruk.data


enum class GameMode(val value: Int) {
    QUIZ(0),
    FLASHCARD(1)
}

enum class Difficulty(val value: Int) {
    EASY(0),
    MEDIUM(1),
    HARD(2)
}

enum class Type(val value: Int) {
    WORD(0),
    PICTURE(1),
    AUDIO(2)
}

enum class Language(val value: Int) {
    ENGLISH(0),
    LITHUANIAN(1),
    UKRAINIAN(2),
    FRENCH(3);

    companion object {
        fun asString(language: Language): String {
            return when (language) {
                ENGLISH -> "English"
                LITHUANIAN -> "Lithuanian"
                UKRAINIAN -> "Ukrainian"
                FRENCH -> "French"
            }
        }

        fun fromString(language: String): Language {
            return when (language) {
                "English" -> ENGLISH
                "Lithuanian" -> LITHUANIAN
                "Ukrainian" -> UKRAINIAN
                "French" -> FRENCH
                else -> ENGLISH
            }
        }
    }
}

