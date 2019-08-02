package ru.you11.skbkonturtestproject.repository

import android.content.Context
import android.util.Log
import androidx.core.content.edit
import ru.you11.skbkonturtestproject.api.ApiMethods
import ru.you11.skbkonturtestproject.api.ApiService
import ru.you11.skbkonturtestproject.api.CallResult
import ru.you11.skbkonturtestproject.api.RetrofitFactory
import ru.you11.skbkonturtestproject.api.models.ApiContact
import ru.you11.skbkonturtestproject.db.ContactDao
import ru.you11.skbkonturtestproject.db.DbContact
import ru.you11.skbkonturtestproject.main.App
import ru.you11.skbkonturtestproject.models.Contact
import ru.you11.skbkonturtestproject.other.Consts
import java.util.*
import kotlin.collections.ArrayList

class Repository(private val contactDao: ContactDao) {

    private val apiService = ApiService(RetrofitFactory().create().create(ApiMethods::class.java))

    fun getContacts(): CallResult<List<Contact>> {

        if (!isUpdateNeeded()) {
            return getCachedContacts()
        }

        val contacts = getContactsFromNetwork()
        if (contacts.isSuccess)
            saveLastUpdateDatetime()

        return contacts
    }

    fun saveContactsToCache(contacts: List<Contact>) {
        contactDao.insertAllContacts(Contact.convertToDbPersonList(contacts))
    }

    private fun getContactsFromNetwork(): CallResult<List<Contact>> {
        val apiContacts = ArrayList<ApiContact>()

        Consts.Network.filenames.forEach {
            val result = apiService.getAllContacts(it)
            if (!result.isSuccess) {
                return CallResult(result.error)
            }
            apiContacts.addAll(result.data)
        }

        return CallResult(ArrayList(ApiContact.convertToPersonList(apiContacts)))
    }

    private fun getCachedContacts(): CallResult<List<Contact>> {
        val result = CallResult(DbContact.convertToContacts(contactDao.getAllContacts()))
        result.isCached = true
        return result
    }

    private fun getLastUpdateDatetime(): Long? {
        val prefs = App.instance.getSharedPreferences(Consts.Prefs.contactsPrefs, Context.MODE_PRIVATE)
        val lastUpdateTime = prefs?.getLong(Consts.Prefs.contactsPrefsLastUpdate, 0)
        return if (lastUpdateTime == 0L) null else lastUpdateTime
    }

    private fun isUpdateNeeded(): Boolean {
        val lastUpdateTimeInMillis = getLastUpdateDatetime() ?: return true
        val timeDiffForUpdateInMillis = Consts.Network.timeDiffForUpdateInMillis
        val currentTime = Date().time
        return (lastUpdateTimeInMillis + timeDiffForUpdateInMillis) < currentTime
    }

    private fun saveLastUpdateDatetime() {
        val prefs = App.instance.getSharedPreferences(Consts.Prefs.contactsPrefs, Context.MODE_PRIVATE)
        prefs?.edit {
            val time = Date().time
            putLong(Consts.Prefs.contactsPrefsLastUpdate, time)
            apply()
        }
    }
}