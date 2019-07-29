package ru.you11.skbkonturtestproject.main.contacts

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_contact.*
import ru.you11.skbkonturtestproject.models.Person

class ContactsRVViewHolder(
    override val containerView: View,
    private val listener: OnPersonClickListener
) : RecyclerView.ViewHolder(containerView), LayoutContainer {

    init {
        containerView.setOnClickListener { listener.onPersonClick(person) }
    }

    private lateinit var person: Person

    fun bind(person: Person) {
        this.person = person
        contactName.text = person.name
        contactPhone.text = person.height.toString()
        contactHeight.text = person.phone
    }
}

interface OnPersonClickListener {
    fun onPersonClick(person: Person)
}