package ru.you11.skbkonturtestproject.main.contacts

import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.*
import ru.you11.skbkonturtestproject.main.BaseViewModel
import ru.you11.skbkonturtestproject.models.Contact

class ContactsViewModel: BaseViewModel() {

    init {
        getAllContacts()
    }

    val contacts = MutableLiveData<List<Contact>>()

    private fun getAllContacts() {
        launch {
            val data = ArrayList<Contact>()

            val list = ArrayList<Deferred<List<Contact>>>()
            for (i in 1..3) {
                list.add(async { repository.getContacts("generated-0$i") })
            }
            list.forEach {
                data.addAll(it.await())
            }
            contacts.postValue(data)
        }
    }
}