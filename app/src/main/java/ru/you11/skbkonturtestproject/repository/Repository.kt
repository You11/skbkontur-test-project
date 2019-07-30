package ru.you11.skbkonturtestproject.repository

import ru.you11.skbkonturtestproject.api.ApiMethods
import ru.you11.skbkonturtestproject.api.ApiService
import ru.you11.skbkonturtestproject.api.RetrofitFactory
import ru.you11.skbkonturtestproject.api.models.ApiContact
import ru.you11.skbkonturtestproject.models.Contact

class Repository {

    private val apiService = ApiService(RetrofitFactory().create().create(ApiMethods::class.java))

    suspend fun getContacts(filename: String): List<Contact> {
        val contacts = ArrayList<Contact>()
        contacts.addAll(ApiContact.convertToPersonList(apiService.getAllContacts(filename).data))
        return contacts
    }
}