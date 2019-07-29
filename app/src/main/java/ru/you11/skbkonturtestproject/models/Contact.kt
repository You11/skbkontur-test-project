package ru.you11.skbkonturtestproject.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Contact(
    val id: String,
    val name: String,
    val phone: String,
    val height: Float,
    val biography: String,
    val temperament: Temperament,
    val educationPeriod: EducationPeriod
): Parcelable