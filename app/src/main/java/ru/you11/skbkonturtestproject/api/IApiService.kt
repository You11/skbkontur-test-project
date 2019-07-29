package ru.you11.skbkonturtestproject.api

import ru.you11.skbkonturtestproject.api.models.ApiPerson

interface IApiService {

    fun getPersonsData(filename: String): CallResult<List<ApiPerson>>
}