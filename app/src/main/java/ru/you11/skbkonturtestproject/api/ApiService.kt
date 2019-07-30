package ru.you11.skbkonturtestproject.api

import retrofit2.Response
import ru.you11.skbkonturtestproject.api.models.ApiContact

class ApiService(private val apiMethods: ApiMethods): IApiService {

    override suspend fun getAllContacts(filename: String): CallResult<List<ApiContact>> {
        return getApiResponse(apiMethods.getContacts(filename))
    }

    private fun <T> getApiResponse(response: Response<T>): CallResult<T> {
        return try {
            if (response.isSuccessful) {
                val responseBody = response.body()

                if (responseBody != null) CallResult(responseBody) else {
                    getEmptyBodyError()
                }
            } else {
                CallResult(response.code().toString())
            }
        } catch (e: Exception) {
            CallResult("unknown error")
        }
    }

    private fun <T>getEmptyBodyError(): CallResult<T> = CallResult("empty body")

}