package ru.you11.skbkonturtestproject.main.singlecontact

import android.app.Application
import androidx.lifecycle.MutableLiveData
import ru.you11.skbkonturtestproject.main.base.BaseViewModel
import ru.you11.skbkonturtestproject.models.Contact

class SingleContactViewModel(application: Application) : BaseViewModel(application) {

    val contact = MutableLiveData<Contact>()


    fun setContactData(contact: Contact) {
        this.contact.value = contact
    }

    fun getContactPhone() = contact.value?.phone ?: ""
}