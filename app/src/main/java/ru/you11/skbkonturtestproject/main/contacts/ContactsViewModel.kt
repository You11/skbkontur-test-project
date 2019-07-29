package ru.you11.skbkonturtestproject.main.contacts

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import kotlinx.coroutines.launch
import ru.you11.skbkonturtestproject.main.BaseViewModel
import ru.you11.skbkonturtestproject.models.Person

class ContactsViewModel: BaseViewModel() {

    init {
        getAllPersons()
    }

    val persons = MutableLiveData<List<Person>>()

    private fun getAllPersons() {
        launch {
            val data = repository.getPersons()
            persons.postValue(data)
        }
    }
}