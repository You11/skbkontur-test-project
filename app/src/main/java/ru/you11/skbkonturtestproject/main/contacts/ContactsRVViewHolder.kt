package ru.you11.skbkonturtestproject.main.contacts

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_contact.*
import ru.you11.skbkonturtestproject.models.Contact

class ContactsRVViewHolder(
    override val containerView: View,
    private val listener: OnContactClickListener
) : RecyclerView.ViewHolder(containerView), LayoutContainer {

    init {
        containerView.setOnClickListener { listener.onContactClick(contact) }
    }

    private lateinit var contact: Contact

    fun bind(contact: Contact) {
        this.contact = contact
        contactName.text = contact.name
        contactPhone.text = contact.height.toString()
        contactHeight.text = contact.phone
    }
}

interface OnContactClickListener {
    fun onContactClick(contact: Contact)
}