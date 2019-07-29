package ru.you11.skbkonturtestproject.api.models

import ru.you11.skbkonturtestproject.models.EducationPeriod
import ru.you11.skbkonturtestproject.models.Person
import ru.you11.skbkonturtestproject.models.Temperament
import java.util.*

class ApiPerson {
    val id: String? = null
    val name: String? = null
    val phone: String? = null
    val height: Float? = null
    val biography: String? = null
    val temperament: String? = null
    val educationPeriod: ApiEducationPeriod? = null

    class ApiEducationPeriod {
        val start: Date? = null
        val end: Date? = null
    }

    companion object {
        fun convertToPersonList(apiPersons: List<ApiPerson>): List<Person> {
            return apiPersons.map { convertToPerson(it) }
        }

        private fun convertToPerson(apiPerson: ApiPerson): Person {
            return Person(
                id = apiPerson.id ?: "",
                name = apiPerson.name ?: "",
                phone = apiPerson.phone ?: "",
                height = apiPerson.height ?: 0.0f,
                biography = apiPerson.biography ?: "",
                temperament = Temperament.valueOf(apiPerson.temperament?.toUpperCase() ?: Temperament.UNKNOWN.name),
                educationPeriod = convertToEducationPeriod(apiPerson.educationPeriod ?: ApiEducationPeriod())
            )
        }

        private fun convertToEducationPeriod(apiEducationPeriod: ApiEducationPeriod): EducationPeriod {
            return EducationPeriod(
                apiEducationPeriod.start ?: Date(),
                apiEducationPeriod.end ?: Date()
            )
        }
    }
}