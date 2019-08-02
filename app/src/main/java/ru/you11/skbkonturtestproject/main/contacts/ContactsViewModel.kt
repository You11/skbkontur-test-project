package ru.you11.skbkonturtestproject.main.contacts

import android.app.Application
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.launch
import ru.you11.skbkonturtestproject.main.LoadingStatus
import ru.you11.skbkonturtestproject.main.base.BaseViewModel
import ru.you11.skbkonturtestproject.models.Contact

class ContactsViewModel(application: Application) : BaseViewModel(application) {

    val contacts = MutableLiveData<List<Contact>>()
    val loadingStatus = MutableLiveData<LoadingStatus>()
    val error = MutableLiveData<String>()

    fun setData(requestNew: Boolean = false) {
        launch {
            loadingStatus.postValue(LoadingStatus.LOADING)
            val data = repository.getContacts(requestNew)
            if (data.isSuccess) {

                if (!data.isCached) {
                    launch {
                        repository.saveContactsToCache(data.data)
                    }
                }

                clearError()
                loadingStatus.postValue(LoadingStatus.FINISHED)
                contacts.postValue(data.data)
            } else {
                loadingStatus.postValue(LoadingStatus.ERROR)
                error.postValue(data.error)
            }
        }
    }

    fun getSearchedContacts(searchString: String): List<Contact>? {
        return contacts.value?.filter {
            it.name.contains(searchString, true) || it.phone.filter { phone -> phone.isDigit() }.contains(searchString)
        }
    }

    fun isDataEmpty() = contacts.value?.isEmpty() ?: true

    private fun clearError() {
        error.postValue("")
    }
}