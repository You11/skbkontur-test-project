package ru.you11.skbkonturtestproject.main.contacts

import android.app.Application
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.launch
import ru.you11.skbkonturtestproject.main.LoadingStatus
import ru.you11.skbkonturtestproject.main.base.BaseViewModel
import ru.you11.skbkonturtestproject.models.Contact
import ru.you11.skbkonturtestproject.other.Consts
import java.util.*

class ContactsViewModel(application: Application) : BaseViewModel(application) {

    val contacts = MutableLiveData<List<Contact>>()
    val loadingStatus = MutableLiveData<LoadingStatus>()
    val error = MutableLiveData<String>()

    fun updateData() {
        launch {
            setNewData()
        }
    }

    fun updateData(lastUpdateTimeInMillis: Long) {
        launch {
            if (isUpdateNeeded(lastUpdateTimeInMillis)) {
                setNewData()
            } else {
                setCachedData()
            }
        }
    }

    fun getSearchedContacts(searchString: String): List<Contact>? {
        return contacts.value?.filter { it.name.contains(searchString, true) }
    }

    fun isDataEmpty() = contacts.value?.isEmpty() ?: true

    private fun setNewData() {
        loadingStatus.postValue(LoadingStatus.LOADING)
        val data = repository.getContacts(getCached = false)
        if (data.isSuccess) {
            launch {
                repository.saveContactsToCache(data.data)
            }

            clearError()
            loadingStatus.postValue(LoadingStatus.FINISHED)
            contacts.postValue(data.data)
        } else {
            loadingStatus.postValue(LoadingStatus.ERROR)
            error.postValue(data.error)
        }
    }

    private fun setCachedData() {
        launch {
            val cachedData = repository.getContacts(getCached = true)
            if (cachedData.data.isNotEmpty()) {
                loadingStatus.postValue(LoadingStatus.FINISHED)
                contacts.postValue(cachedData.data)
            } else {
                loadingStatus.postValue(LoadingStatus.ERROR)
            }
        }
    }

    private fun isUpdateNeeded(lastUpdateTimeInMillis: Long): Boolean {
        val timeDiffForUpdateInMillis = Consts.Network.timeDiffForUpdateInMillis
        val currentTime = Date().time
        return (lastUpdateTimeInMillis + timeDiffForUpdateInMillis) < currentTime
    }

    private fun clearError() {
        error.postValue("")
    }
}