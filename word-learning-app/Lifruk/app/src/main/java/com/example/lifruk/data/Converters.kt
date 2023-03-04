package com.example.lifruk.data

import androidx.room.TypeConverter

@TypeConverter
fun toDifficulty(value: String) = enumValueOf<Difficulty>(value)

@TypeConverter
fun fromDifficulty(value: Difficulty) = value.name

@TypeConverter
fun toType(value: String) = enumValueOf<Type>(value)

@TypeConverter
fun fromType(value: Type) = value.name

@TypeConverter
fun toLanguage(value: String) = enumValueOf<Language>(value)

@TypeConverter
fun fromLanguage(value: Language) = value.name

@TypeConverter
fun toMode(value: String) = enumValueOf<GameMode>(value)

@TypeConverter
fun fromMode(value: GameMode) = value.name



