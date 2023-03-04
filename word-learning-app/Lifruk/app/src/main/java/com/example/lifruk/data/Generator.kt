package com.example.lifruk.data

import android.util.Log
import androidx.room.ColumnInfo
import com.example.lifruk.R

suspend fun generateTopics(topicDao: TopicDao) {
    // Add topics
    topicDao.insertTopic(Topic(0, "Color", Difficulty.EASY))
    topicDao.insertTopic(Topic(1, "Number", Difficulty.EASY))
    topicDao.insertTopic(Topic(2, "Animal", Difficulty.MEDIUM))
    topicDao.insertTopic(Topic(3, "Meal", Difficulty.HARD))
}

suspend fun generateWord(wordDao: WordDao) {
    wordDao.insertWord(Word(0, Language.ENGLISH,"red", 0, R.drawable.red))
    wordDao.insertWord(Word(1, Language.LITHUANIAN,"raudona", 0, R.drawable.red))
    wordDao.insertWord(Word(2, Language.UKRAINIAN,"червоний", 0, R.drawable.red))
    wordDao.insertWord(Word(3, Language.FRENCH,"rouge", 0, R.drawable.red))

    wordDao.insertWord(Word(4, Language.ENGLISH,"blue", 0, R.drawable.blue))
    wordDao.insertWord(Word(5, Language.LITHUANIAN,"mėlyna", 0, R.drawable.blue))
    wordDao.insertWord(Word(6, Language.UKRAINIAN,"блакитний", 0, R.drawable.blue))
    wordDao.insertWord(Word(7, Language.FRENCH,"bleu", 0, R.drawable.blue))

    wordDao.insertWord(Word(8, Language.ENGLISH,"green", 0, R.drawable.green))
    wordDao.insertWord(Word(9, Language.LITHUANIAN,"žalias", 0, R.drawable.green))
    wordDao.insertWord(Word(10, Language.UKRAINIAN,"зелений", 0, R.drawable.green))
    wordDao.insertWord(Word(11, Language.FRENCH,"vert", 0, R.drawable.green))

    wordDao.insertWord(Word(12, Language.ENGLISH,"yellow", 0, R.drawable.yellow))
    wordDao.insertWord(Word(13, Language.LITHUANIAN,"geltona", 0, R.drawable.yellow))
    wordDao.insertWord(Word(14, Language.UKRAINIAN,"жовтий", 0, R.drawable.yellow))
    wordDao.insertWord(Word(15, Language.FRENCH,"jaune", 0, R.drawable.yellow))

    wordDao.insertWord(Word(16, Language.ENGLISH,"white", 0, R.drawable.white))
    wordDao.insertWord(Word(17, Language.LITHUANIAN,"baltas", 0, R.drawable.white))
    wordDao.insertWord(Word(18, Language.UKRAINIAN,"білий", 0, R.drawable.white))
    wordDao.insertWord(Word(19, Language.FRENCH,"blanc", 0, R.drawable.white))

    wordDao.insertWord(Word(20, Language.ENGLISH,"black", 0, R.drawable.black))
    wordDao.insertWord(Word(21, Language.LITHUANIAN,"juodas", 0, R.drawable.black))
    wordDao.insertWord(Word(22, Language.UKRAINIAN,"чорний", 0, R.drawable.black))
    wordDao.insertWord(Word(23, Language.FRENCH,"noir", 0, R.drawable.black))

    wordDao.insertWord(Word(24, Language.ENGLISH,"pink", 0, R.drawable.pink))
    wordDao.insertWord(Word(25, Language.LITHUANIAN,"rožinis", 0, R.drawable.pink))
    wordDao.insertWord(Word(26, Language.UKRAINIAN,"рожевий", 0, R.drawable.pink))
    wordDao.insertWord(Word(27, Language.FRENCH,"rose", 0, R.drawable.pink))

    wordDao.insertWord(Word(28, Language.ENGLISH,"orange", 0, R.drawable.orange))
    wordDao.insertWord(Word(29, Language.LITHUANIAN,"oranžinė", 0, R.drawable.orange))
    wordDao.insertWord(Word(30, Language.UKRAINIAN,"помаранчевий", 0, R.drawable.orange))
    wordDao.insertWord(Word(31, Language.FRENCH,"orange", 0, R.drawable.orange))

    wordDao.insertWord(Word(32, Language.ENGLISH,"purple", 0, R.drawable.violet))
    wordDao.insertWord(Word(33, Language.LITHUANIAN,"violetas", 0, R.drawable.violet))
    wordDao.insertWord(Word(34, Language.UKRAINIAN,"фіолетовий", 0, R.drawable.violet))
    wordDao.insertWord(Word(35, Language.FRENCH,"violet", 0, R.drawable.violet))

    wordDao.insertWord(Word(36, Language.ENGLISH,"purple", 0, R.drawable.violet))
    wordDao.insertWord(Word(37, Language.LITHUANIAN,"violetas", 0, R.drawable.violet))
    wordDao.insertWord(Word(38, Language.UKRAINIAN,"фіолетовий", 0, R.drawable.violet))
    wordDao.insertWord(Word(39, Language.FRENCH,"violet", 0, R.drawable.violet))
}

suspend fun generateTranslation(translationDao: TranslationDao) {
    translationDao.insertTranslation(Translation(0, 0, 1))
    translationDao.insertTranslation(Translation(1, 0, 2))
    translationDao.insertTranslation(Translation(2, 0, 3))

    translationDao.insertTranslation(Translation(3, 4, 5))
    translationDao.insertTranslation(Translation(4, 4, 6))
    translationDao.insertTranslation(Translation(5, 4, 7))

    translationDao.insertTranslation(Translation(6, 8, 9))
    translationDao.insertTranslation(Translation(7, 8, 10))
    translationDao.insertTranslation(Translation(8, 8, 11))

    translationDao.insertTranslation(Translation(9, 12, 13))
    translationDao.insertTranslation(Translation(10, 12, 14))
    translationDao.insertTranslation(Translation(11, 12, 15))

    translationDao.insertTranslation(Translation(12, 16, 17))
    translationDao.insertTranslation(Translation(13, 16, 18))
    translationDao.insertTranslation(Translation(14, 16, 19))

    translationDao.insertTranslation(Translation(15, 20, 21))
    translationDao.insertTranslation(Translation(16, 20, 22))
    translationDao.insertTranslation(Translation(17, 20, 23))

    translationDao.insertTranslation(Translation(18, 24, 25))
    translationDao.insertTranslation(Translation(19, 24, 26))
    translationDao.insertTranslation(Translation(20, 24, 27))

    translationDao.insertTranslation(Translation(21, 28, 29))
    translationDao.insertTranslation(Translation(22, 28, 30))
    translationDao.insertTranslation(Translation(23, 28, 31))

    translationDao.insertTranslation(Translation(24, 32, 33))
    translationDao.insertTranslation(Translation(25, 32, 34))
    translationDao.insertTranslation(Translation(26, 32, 35))

    translationDao.insertTranslation(Translation(27, 36, 37))
    translationDao.insertTranslation(Translation(28, 36, 38))
    translationDao.insertTranslation(Translation(29, 36, 39))
}

suspend fun generatePlayer(playerDao: PlayerDao) {
    playerDao.insertPlayer(Player(0, "fio", "fiona@gmail.com", "test", Language.FRENCH, Language.ENGLISH, Difficulty.EASY))
}

/*
suspend fun generateWord(wordDao: WordDao) {
    // Add words
    wordDao.insertWord(Word(0, "red", null, "raudona", null, "червоний", null, "rouge", null, R.drawable.red))
    wordDao.insertWord(Word(1, "blue", null, "mėlyna", null, "блакитний", null, "bleu", null, R.drawable.blue))
    wordDao.insertWord(Word(2, "green", null, "žalias", null, "зелений", null, "vert", null, R.drawable.green))
    wordDao.insertWord(Word(3, "yellow", null, "geltona", null, "жовтий", null, "jaune", null, R.drawable.yellow))
    wordDao.insertWord(Word(4, "white", null, "baltas", null, "білий", null, "blanc", null, R.drawable.white))
    wordDao.insertWord(Word(5, "black", null, "juodas", null, "чорний", null, "noir", null, R.drawable.black))
    wordDao.insertWord(Word(6, "pink", null, "rožinis", null, "рожевий", null, "rose", null, R.drawable.pink))
    wordDao.insertWord(Word(7, "orange", null, "oranžinė", null, "помаранчевий", null, "orange", null, R.drawable.orange))
    wordDao.insertWord(Word(8, "violet", null, "violetas", null, "фіолетовий", null, "violet", null, R.drawable.violet))

    wordDao.insertWord(Word(9, "zero", null, "nulis", null, "нуль", null, "zéro", null, R.drawable.zero))
    wordDao.insertWord(Word(10, "one", null, "vienas", null, "один", null, "un", null, R.drawable.one))
    wordDao.insertWord(Word(11, "two", null, "du", null, "два", null, "deux", null, R.drawable.two))
    wordDao.insertWord(Word(12, "three", null, "trys", null, "три", null, "trois", null, R.drawable.three))
    wordDao.insertWord(Word(13, "four", null, "keturi", null, "чотири", null, "quatre", null, R.drawable.four))
    wordDao.insertWord(Word(14, "five", null, "penkios", null, "п'ять", null, "cinq", null, R.drawable.five))
    wordDao.insertWord(Word(15, "six", null, "šeši", null, "шість", null, "six", null, R.drawable.six))
    wordDao.insertWord(Word(16, "seven", null, "septyni", null, "сім", null, "sept", null, R.drawable.seven))
    wordDao.insertWord(Word(17, "eight", null, "aštuoni", null, "вісім", null, "huit", null, R.drawable.eight))
    wordDao.insertWord(Word(18, "nine", null, "devynios", null, "дев'ять", null, "neuf", null, R.drawable.nine))
}
*/