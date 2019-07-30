package ru.you11.skbkonturtestproject.api

import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import ru.you11.skbkonturtestproject.api.models.ApiContact

interface ApiMethods {

    @GET("{filename}.json")
    suspend fun getContacts(@Path("filename") fileName: String): Response<List<ApiContact>>
}