package ru.you11.skbkonturtestproject.main.contacts

import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.launch
import ru.you11.skbkonturtestproject.main.BaseViewModel
import ru.you11.skbkonturtestproject.models.Contact

class ContactsViewModel: BaseViewModel() {

    init {
        getAllContacts()
    }

    val contacts = MutableLiveData<List<Contact>>()

    private fun getAllContacts() {
        launch {
            val data = repository.getContacts()
            contacts.postValue(data)
        }
    }
}