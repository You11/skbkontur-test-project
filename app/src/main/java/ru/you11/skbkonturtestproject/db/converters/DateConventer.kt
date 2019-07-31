package ru.you11.skbkonturtestproject.db.converters

import androidx.room.TypeConverter
import java.util.*

class DateConventer {

    @TypeConverter
    fun fromTimestamp(value: Long) = Date(value)

    @TypeConverter
    fun dateToTimestamp(date: Date) = date.time
}