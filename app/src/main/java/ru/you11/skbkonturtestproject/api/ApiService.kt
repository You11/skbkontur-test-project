package ru.you11.skbkonturtestproject.api

import retrofit2.Call
import ru.you11.skbkonturtestproject.api.models.ApiContact

class ApiService(private val apiMethods: ApiMethods): IApiService {

    override fun getAllContacts(filename: String): CallResult<List<ApiContact>> {
        return getApiResponse(apiMethods.getContacts(filename))
    }

    private fun <T> getApiResponse(call: Call<T>): CallResult<T> {
        return try {
            val response = call.execute()

            if (response.isSuccessful) {
                val responseBody = response.body()

                if (responseBody != null) CallResult(responseBody) else {
                    getEmptyBodyError()
                }
            } else {
                CallResult(response.code().toString())
            }
        } catch (e: Exception) {
            CallResult("Ошибка соединения")
        }
    }

    private fun <T>getEmptyBodyError(): CallResult<T> = CallResult("Ошибка соединения")

}