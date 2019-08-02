package ru.you11.skbkonturtestproject.main.contacts

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.launch
import ru.you11.skbkonturtestproject.main.LoadingStatus
import ru.you11.skbkonturtestproject.main.base.BaseViewModel
import ru.you11.skbkonturtestproject.models.Contact

class ContactsViewModel(application: Application) : BaseViewModel(application) {

    val contacts = MutableLiveData<List<Contact>>()
    val loadingStatus = MutableLiveData<LoadingStatus>()
    val error = MutableLiveData<String>()

    fun updateData() {
        launch {
            loadingStatus.postValue(LoadingStatus.LOADING)
            val data = repository.getContacts()
            if (data.isSuccess) {
                loadingStatus.postValue(LoadingStatus.FINISHED)
                error.postValue("")
                contacts.postValue(data.data)
            } else {
                loadingStatus.postValue(LoadingStatus.ERROR)
                error.postValue(data.error)
            }
        }
    }

    fun setCachedData() {
        launch {
            val cachedData = repository.getContactsFromCache()
            if (cachedData.isNotEmpty()) {
                loadingStatus.postValue(LoadingStatus.FINISHED)
                contacts.postValue(cachedData)
            } else {
                loadingStatus.postValue(LoadingStatus.ERROR)
            }
        }
    }

    fun getSearchedContacts(searchString: String): List<Contact>? {
        return contacts.value?.filter { it.name.startsWith(searchString) }
    }

    fun isDataEmpty() = contacts.value?.isEmpty() ?: true
}