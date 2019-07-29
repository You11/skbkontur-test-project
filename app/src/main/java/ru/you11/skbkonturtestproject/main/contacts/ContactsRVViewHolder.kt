package ru.you11.skbkonturtestproject.main.contacts

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_contact.*
import ru.you11.skbkonturtestproject.model.Person

class ContactsRVViewHolder(override val containerView: View): RecyclerView.ViewHolder(containerView), LayoutContainer {

    fun bind(person: Person) {
        contactName.text = person.name
        contactPhone.text = person.height.toString()
        contactHeight.text = person.id //person id == phone number?
    }
}