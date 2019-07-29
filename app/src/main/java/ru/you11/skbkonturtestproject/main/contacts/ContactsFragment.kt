package ru.you11.skbkonturtestproject.main.contacts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_contacts.*
import ru.you11.skbkonturtestproject.R
import ru.you11.skbkonturtestproject.main.BaseFragment
import ru.you11.skbkonturtestproject.models.Person

class ContactsFragment: BaseFragment<ContactsViewModel>(), OnPersonClickListener {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_contacts, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRV()
        setupDataObserver()
    }

    private fun setupRV() {
        contactsRV.layoutManager = LinearLayoutManager(activity)
        contactsRV.adapter = ContactsRVAdapter(this)
    }

    override fun onPersonClick(person: Person) {
        val action = ContactsFragmentDirections.actionContactsFragmentToSingleContactFragment(person)
        findNavController().navigate(action)
    }

    private fun setupDataObserver() {
        viewModel.persons.observe(this, Observer {
            onDataUpdate(it)
        })
    }

    private fun onDataUpdate(data: List<Person>) {
        (contactsRV.adapter as ContactsRVAdapter).updateData(data)
    }

    override fun createViewModel() = ViewModelProviders.of(this).get(ContactsViewModel::class.java)
}