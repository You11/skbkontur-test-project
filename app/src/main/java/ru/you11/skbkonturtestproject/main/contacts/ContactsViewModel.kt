package ru.you11.skbkonturtestproject.main.contacts

import android.app.Application
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.launch
import ru.you11.skbkonturtestproject.main.LoadingStatus
import ru.you11.skbkonturtestproject.main.base.BaseViewModel
import ru.you11.skbkonturtestproject.models.Contact

class ContactsViewModel(application: Application): BaseViewModel(application) {

    init {
        getAllContacts()
    }

    val contacts = MutableLiveData<List<Contact>>()
    val loadingStatus = MutableLiveData<LoadingStatus>()
    val error = MutableLiveData<String>()

    private fun getAllContacts() {
        launch {
            setCachedData()

            val data = repository.getContacts()
            if (loadingStatus.value != LoadingStatus.FINISHED) loadingStatus.postValue(LoadingStatus.FINISHED)
            if (data.isSuccess) {
                contacts.postValue(data.data)
            } else {
                error.postValue(data.error)
            }
        }
    }

    private fun setCachedData() {
        val cachedData = repository.getContactsFromCache()
        if (cachedData.isEmpty()) {
            loadingStatus.postValue(LoadingStatus.LOADING)
        } else {
            contacts.postValue(repository.getContactsFromCache())
            loadingStatus.postValue(LoadingStatus.FINISHED)
        }
    }
}