package ru.you11.skbkonturtestproject.main.contacts

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.launch
import ru.you11.skbkonturtestproject.main.LoadingStatus
import ru.you11.skbkonturtestproject.main.base.BaseViewModel
import ru.you11.skbkonturtestproject.models.Contact

class ContactsViewModel(application: Application) : BaseViewModel(application) {

    init {
        launch {
            setCachedData()
            updateAllContacts()
        }
    }

    val contacts = MutableLiveData<List<Contact>>()
    val loadingStatus = MutableLiveData<LoadingStatus>()
    val error = MutableLiveData<String>()

    fun updateData() {
        launch {
            updateAllContacts()
        }
    }

    private fun updateAllContacts() {
        Log.d("meow", "meow2")
        loadingStatus.postValue(LoadingStatus.LOADING)
        val data = repository.getContacts()
        if (loadingStatus.value != LoadingStatus.FINISHED) loadingStatus.postValue(LoadingStatus.FINISHED)
        if (data.isSuccess) {
            error.postValue("")
            contacts.postValue(data.data)
        } else {
            error.postValue(data.error)
        }
    }

    private fun setCachedData() {
        Log.d("meow", "meow1")
        val cachedData = repository.getContactsFromCache()
        if (cachedData.isNotEmpty()) {
            contacts.postValue(repository.getContactsFromCache())
        }
    }
}