package ru.you11.skbkonturtestproject.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
class EducationPeriod(
    val start: Date,
    val end: Date
): Parcelable