package ru.you11.skbkonturtestproject.main.contacts

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_contacts.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import ru.you11.skbkonturtestproject.R
import ru.you11.skbkonturtestproject.main.BaseFragment
import ru.you11.skbkonturtestproject.models.Person
import ru.you11.skbkonturtestproject.repository.Repository

class ContactsFragment: BaseFragment<ContactsViewModel>(), OnPersonClickListener {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_contacts, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRV()
        setData()
    }

    private fun setupRV() {
        contactsRV.layoutManager = LinearLayoutManager(activity)
        contactsRV.adapter = ContactsRVAdapter(this)
    }

    override fun onPersonClick(person: Person) {
        val action = ContactsFragmentDirections.actionContactsFragmentToSingleContactFragment(person)
        findNavController().navigate(action)
    }

    private fun setData() {
        //TODO: Remove
        val handler = Handler()
        GlobalScope.launch {
            val repository = Repository()
            val persons = repository.getPersons()
            handler.post {
                (contactsRV.adapter as ContactsRVAdapter).updateData(persons)
            }
        }

//        val persons = ArrayList<Person>()
//        persons.add(Person("id", "name", "+7 (432) 563-53-12", 194.4f, "biography", Temperament.CHOLERIC, EducationPeriod(Date(0), Date(0))))
//        persons.add(Person("id", "name", "+7 (432) 563-53-12", 194.4f, "biography", Temperament.CHOLERIC, EducationPeriod(Date(0), Date(0))))
//        (contactsRV.adapter as ContactsRVAdapter).updateData(persons)
    }

    override fun createViewModel() = ViewModelProviders.of(this).get(ContactsViewModel::class.java)
}