package ru.you11.skbkonturtestproject.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import ru.you11.skbkonturtestproject.api.models.ApiPerson

interface ApiMethods {

    @GET("{filename}.json")
    fun getPersons(@Path("filename") fileName: String): Call<List<ApiPerson>>
}