package ru.you11.skbkonturtestproject.repository

import ru.you11.skbkonturtestproject.api.ApiMethods
import ru.you11.skbkonturtestproject.api.ApiService
import ru.you11.skbkonturtestproject.api.CallResult
import ru.you11.skbkonturtestproject.api.RetrofitFactory
import ru.you11.skbkonturtestproject.api.models.ApiContact
import ru.you11.skbkonturtestproject.db.ContactDao
import ru.you11.skbkonturtestproject.db.DbContact
import ru.you11.skbkonturtestproject.models.Contact
import ru.you11.skbkonturtestproject.other.Consts

class Repository(private val contactDao: ContactDao) {

    private val apiService = ApiService(RetrofitFactory().create().create(ApiMethods::class.java))


    fun getContacts(getCached: Boolean): CallResult<List<Contact>> {
        if (getCached) {
            return getCachedContacts()
        }

        val apiContacts = ArrayList<ApiContact>()

        Consts.Network.filenames.forEach {
            val result = apiService.getAllContacts(it)
            if (!result.isSuccess) {
                return CallResult(result.error)
            }
            apiContacts.addAll(result.data)
        }

        val contacts = ArrayList(ApiContact.convertToPersonList(apiContacts))

        return CallResult(contacts)
    }

    fun saveContactsToCache(contacts: List<Contact>) {
        contactDao.insertAllContacts(Contact.convertToDbPersonList(contacts))
    }

    private fun getCachedContacts(): CallResult<List<Contact>> {
        return CallResult(DbContact.convertToContacts(contactDao.getAllContacts()))
    }
}