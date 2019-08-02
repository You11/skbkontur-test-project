package ru.you11.skbkonturtestproject.api

import android.content.Context
import android.net.ConnectivityManager
import retrofit2.Call
import ru.you11.skbkonturtestproject.api.models.ApiContact
import ru.you11.skbkonturtestproject.main.App
import android.net.NetworkInfo
import ru.you11.skbkonturtestproject.R


class ApiService(private val apiMethods: ApiMethods): IApiService {

    private val resources = App.instance.resources

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
                CallResult(getErrorMessageBasedOnCode(response.code()))
            }
        } catch (e: Exception) {
            if (!isConnectedToInternet())
                return CallResult(resources.getString(R.string.not_connected_error))

            return CallResult(resources.getString(R.string.general_error))
        }
    }

    private fun getErrorMessageBasedOnCode(code: Int): String {
        return when (code) {
            in 500..600 -> resources.getString(R.string.server_error)
            403 -> resources.getString(R.string.forbidden_error)
            else -> code.toString()
        }
    }

    private fun <T>getEmptyBodyError(): CallResult<T> = CallResult(resources.getString(R.string.general_error))

    private fun isConnectedToInternet(): Boolean {
        val cm = App.instance.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return cm.activeNetworkInfo?.isConnected ?: false
    }
}