package ru.you11.skbkonturtestproject.main.contacts

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.you11.skbkonturtestproject.R
import ru.you11.skbkonturtestproject.models.Contact

class ContactsRVAdapter(private val listener: OnContactClickListener) : RecyclerView.Adapter<ContactsRVViewHolder>() {

    private val contacts = ArrayList<Contact>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ContactsRVViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_contact, parent, false),
        listener
    )

    override fun onBindViewHolder(holder: ContactsRVViewHolder, position: Int) {
        holder.bind(contacts[position])
    }

    override fun getItemCount(): Int = contacts.size

    fun updateData(newData: List<Contact>) {
        contacts.clear()
        contacts.addAll(newData)
        notifyDataSetChanged()
    }
}