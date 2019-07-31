package ru.you11.skbkonturtestproject.api.models

import ru.you11.skbkonturtestproject.db.DbContact
import ru.you11.skbkonturtestproject.models.EducationPeriod
import ru.you11.skbkonturtestproject.models.Contact
import ru.you11.skbkonturtestproject.models.Temperament
import java.util.*

class ApiContact {
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
        fun convertToPersonList(apiContacts: List<ApiContact>): List<Contact> {
            return apiContacts.map { convertToPerson(it) }
        }

        fun convertToDbPersonList(apiContacts: List<ApiContact>): List<DbContact> {
            return apiContacts.map { convertToDbPerson(it) }
        }

        private fun convertToPerson(apiContact: ApiContact): Contact {
            return Contact(
                id = apiContact.id ?: "",
                name = apiContact.name ?: "",
                phone = apiContact.phone ?: "",
                height = apiContact.height ?: 0.0f,
                biography = apiContact.biography ?: "",
                temperament = Temperament.valueOf(apiContact.temperament?.toUpperCase() ?: Temperament.UNKNOWN.name),
                educationPeriod = convertToEducationPeriod(apiContact.educationPeriod ?: ApiEducationPeriod())
            )
        }

        private fun convertToDbPerson(apiContact: ApiContact): DbContact {
            return DbContact(
                id = apiContact.id ?: "",
                name = apiContact.name ?: "",
                phone = apiContact.phone ?: "",
                height = apiContact.height ?: 0.0f,
                biography = apiContact.biography ?: "",
                temperament = Temperament.valueOf(apiContact.temperament?.toUpperCase() ?: Temperament.UNKNOWN.name),
                startDate = apiContact.educationPeriod?.start ?: Date(),
                endDate = apiContact.educationPeriod?.end ?: Date()
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