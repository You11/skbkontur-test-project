package ru.you11.skbkonturtestproject.main.contacts

import android.app.Application
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.launch
import ru.you11.skbkonturtestproject.main.base.BaseViewModel
import ru.you11.skbkonturtestproject.models.Contact

class ContactsViewModel(application: Application): BaseViewModel(application) {

    init {
        getAllContacts()
    }

    val contacts = MutableLiveData<List<Contact>>()

    private fun getAllContacts() {
        launch {
            contacts.postValue(repository.getContactsFromCache())
            val data = repository.getContacts()
            contacts.postValue(data)
        }
    }
}