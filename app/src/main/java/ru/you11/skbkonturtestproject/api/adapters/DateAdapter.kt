package ru.you11.skbkonturtestproject.api.adapters

import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import ru.you11.skbkonturtestproject.other.DateUtils
import java.text.SimpleDateFormat
import java.util.*

class DateAdapter {

    @ToJson
    fun toJson(date: Date): String {
        return DateUtils.fullApiDateFormat.format(date)
    }

    @FromJson
    fun fromJson(jsonDate: String?): Date {
        if (jsonDate.isNullOrBlank()) {
            return DateUtils.createEmptyDate()
        }

        return try {
            DateUtils.fullApiDateFormat.parse(jsonDate)
        } catch (e: Exception) {
            DateUtils.createEmptyDate()
        }
    }
}