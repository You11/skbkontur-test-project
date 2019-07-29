package ru.you11.skbkonturtestproject.api.adapters

import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import java.text.SimpleDateFormat
import java.util.*

class DateAdapter {

    @ToJson
    fun toJson(date: Date): String {
        return getDateFormat().format(date)
    }

    @FromJson
    fun fromJson(jsonDate: String?): Date {
        if (jsonDate.isNullOrBlank()) {
            return createEmptyDate()
        }

        return try {
            getDateFormat().parse(jsonDate)
        } catch (e: Exception) {
            createEmptyDate()
        }
    }

    //TODO: refactor later
    private fun getDateFormat() = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.US)

    private fun createEmptyDate(): Date {
        val cal = Calendar.getInstance()
        cal.set(1900, 0, 1)
        return cal.time
    }
}