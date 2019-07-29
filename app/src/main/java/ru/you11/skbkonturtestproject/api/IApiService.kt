package ru.you11.skbkonturtestproject.api

import ru.you11.skbkonturtestproject.api.models.ApiContact

interface IApiService {

    fun getAllContacts(filename: String): CallResult<List<ApiContact>>
}