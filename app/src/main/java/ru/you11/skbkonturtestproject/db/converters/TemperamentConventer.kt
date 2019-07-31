package ru.you11.skbkonturtestproject.db.converters

import androidx.room.TypeConverter
import ru.you11.skbkonturtestproject.models.Temperament

class TemperamentConventer {

    @TypeConverter
    fun fromTemperament(temperament: Temperament) = temperament.name

    @TypeConverter
    fun toTemperament(string: String) = Temperament.valueOf(string)
}