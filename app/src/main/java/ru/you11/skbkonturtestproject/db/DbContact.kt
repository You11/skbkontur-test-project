package ru.you11.skbkonturtestproject.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.you11.skbkonturtestproject.models.Contact
import ru.you11.skbkonturtestproject.models.EducationPeriod
import ru.you11.skbkonturtestproject.models.Temperament
import java.util.*

@Entity(tableName = "Contact")
class DbContact(
    @PrimaryKey val id: String,
    val name: String,
    val phone: String,
    val height: Float,
    val biography: String,
    val temperament: Temperament,
    @ColumnInfo(name = "start_education_date") val startDate: Date,
    @ColumnInfo(name = "end_education_date") val endDate: Date
) {

    companion object {
        fun convertToContacts(dbContacts: List<DbContact>): List<Contact> {
            return dbContacts.map { convertToContact(it) }
        }

        private fun convertToContact(dbContact: DbContact): Contact {
            return Contact(id = dbContact.id,
                name = dbContact.name,
                phone = dbContact.phone,
                height = dbContact.height,
                biography = dbContact.biography,
                temperament = dbContact.temperament,
                educationPeriod = EducationPeriod(dbContact.startDate, dbContact.endDate)
            )
        }
    }
}