package ru.you11.skbkonturtestproject.models

import android.os.Parcelable
import androidx.room.Entity
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
@Entity
class EducationPeriod(
    val start: Date,
    val end: Date
): Parcelable