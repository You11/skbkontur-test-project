package ru.you11.skbkonturtestproject.repository

import ru.you11.skbkonturtestproject.api.ApiMethods
import ru.you11.skbkonturtestproject.api.ApiService
import ru.you11.skbkonturtestproject.api.RetrofitFactory
import ru.you11.skbkonturtestproject.api.models.ApiContact
import ru.you11.skbkonturtestproject.db.ContactDao
import ru.you11.skbkonturtestproject.db.ContactDatabase
import ru.you11.skbkonturtestproject.db.DbContact
import ru.you11.skbkonturtestproject.models.Contact

class Repository(private val contactDao: ContactDao) {

    private val apiService = ApiService(RetrofitFactory().create().create(ApiMethods::class.java))

    fun getContactsFromCache(): List<Contact> {
        return DbContact.convertToContacts(contactDao.getAllContacts())
    }

    fun getContacts(): List<Contact> {

        val apiContacts = ArrayList<ApiContact>()
        apiContacts.addAll(apiService.getAllContacts("generated-01").data)
        apiContacts.addAll(apiService.getAllContacts("generated-02").data)
        apiContacts.addAll(apiService.getAllContacts("generated-03").data)
        saveContactsToCache(apiContacts)

        val contacts = ArrayList<Contact>()
        contacts.addAll(ApiContact.convertToPersonList(apiContacts))

        return contacts
    }

    private fun saveContactsToCache(contacts: List<ApiContact>) {
        contactDao.insertAllContacts(ApiContact.convertToDbPersonList(contacts))
    }
}