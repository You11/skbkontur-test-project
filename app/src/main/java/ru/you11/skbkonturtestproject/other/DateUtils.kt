package ru.you11.skbkonturtestproject.other

import java.text.SimpleDateFormat
import java.util.*

object DateUtils {

    val formatDayMonthYear = SimpleDateFormat("dd.MM.yyyy", Locale.US)

    val fullApiDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.US)

    fun createEmptyDate(): Date {
        val cal = Calendar.getInstance()
        cal.set(1900, 0, 1)
        return cal.time
    }
}