package ru.you11.skbkonturtestproject.main.contacts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import ru.you11.skbkonturtestproject.R
import ru.you11.skbkonturtestproject.main.BaseFragment

class ContactsFragment: BaseFragment<ContactsViewModel>() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val root = inflater.inflate(R.layout.fragment_contacts, container, false)
        with(root) {

        }

        return root
    }

    override fun createViewModel() = ViewModelProviders.of(this).get(ContactsViewModel::class.java)
}