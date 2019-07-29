package ru.you11.skbkonturtestproject.main.contacts

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.you11.skbkonturtestproject.R
import ru.you11.skbkonturtestproject.models.Person

class ContactsRVAdapter(private val listener: OnPersonClickListener) : RecyclerView.Adapter<ContactsRVViewHolder>() {

    private val persons = ArrayList<Person>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ContactsRVViewHolder(
        LayoutInflater.from(parent.context).inflate(
            R.layout.item_contact, parent, false
        ),
        listener
    )

    override fun onBindViewHolder(holder: ContactsRVViewHolder, position: Int) {
        holder.bind(persons[position])
    }

    override fun getItemCount(): Int = persons.size

    fun updateData(newData: List<Person>) {
        persons.clear()
        persons.addAll(newData)
        notifyDataSetChanged()
    }
}