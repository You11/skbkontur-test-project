package ru.you11.skbkonturtestproject.model

data class Person(
    val id: String,
    val name: String,
    val height: Float,
    val biography: String,
    val temperament: Temperament,
    val educationPeriod: EducationPeriod
) {
}