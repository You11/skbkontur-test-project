package ru.you11.skbkonturtestproject.main.contacts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_contacts.*
import kotlinx.android.synthetic.main.fragment_contacts.view.*
import kotlinx.android.synthetic.main.fragment_contacts.view.contactsRV
import ru.you11.skbkonturtestproject.R
import ru.you11.skbkonturtestproject.main.BaseFragment
import ru.you11.skbkonturtestproject.model.EducationPeriod
import ru.you11.skbkonturtestproject.model.Person
import ru.you11.skbkonturtestproject.model.Temperament
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class ContactsFragment: BaseFragment<ContactsViewModel>() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_contacts, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRV()
        setData()
    }

    private fun setupRV() {
        contactsRV.layoutManager = LinearLayoutManager(activity)
        contactsRV.adapter = ContactsRVAdapter()
    }

    private fun setData() {
        val persons = ArrayList<Person>()
        persons.add(Person("id", "name", 194.4f, "biography", Temperament.CHOLERIC, EducationPeriod(Date(0), Date(0))))
        persons.add(Person("id", "name", 194.4f, "biography", Temperament.CHOLERIC, EducationPeriod(Date(0), Date(0))))
        (contactsRV.adapter as ContactsRVAdapter).updateData(persons)
    }

    override fun createViewModel() = ViewModelProviders.of(this).get(ContactsViewModel::class.java)
}