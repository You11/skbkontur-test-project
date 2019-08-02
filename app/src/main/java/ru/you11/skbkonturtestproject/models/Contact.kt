package ru.you11.skbkonturtestproject.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import ru.you11.skbkonturtestproject.db.DbContact
import java.util.*

@Parcelize
data class Contact(
    val id: String,
    val name: String,
    val phone: String,
    val height: Float,
    val biography: String,
    val temperament: Temperament,
    val educationPeriod: EducationPeriod
): Parcelable {

    companion object {
        fun convertToDbPersonList(contacts: List<Contact>): List<DbContact> {
            return contacts.map { convertToDbPerson(it) }
        }

        private fun convertToDbPerson(contact: Contact): DbContact {
            return DbContact(
                id = contact.id,
                name = contact.name,
                phone = contact.phone,
                height = contact.height,
                biography = contact.biography,
                temperament = Temperament.valueOf(contact.temperament.name.toUpperCase()),
                startDate = contact.educationPeriod.start,
                endDate = contact.educationPeriod.end
            )
        }
    }
}