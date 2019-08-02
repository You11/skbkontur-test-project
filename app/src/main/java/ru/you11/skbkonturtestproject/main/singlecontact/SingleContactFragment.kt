package ru.you11.skbkonturtestproject.main.singlecontact

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.navArgs
import kotlinx.android.synthetic.main.fragment_single_contact.*
import ru.you11.skbkonturtestproject.R
import ru.you11.skbkonturtestproject.main.base.BaseFragment
import android.content.Intent
import android.net.Uri
import androidx.lifecycle.Observer
import ru.you11.skbkonturtestproject.models.Contact
import ru.you11.skbkonturtestproject.other.DateUtils
import java.util.*


class SingleContactFragment : BaseFragment<SingleContactViewModel>() {

    private val args: SingleContactFragmentArgs by navArgs()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_single_contact, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        enableHomeButton()

        viewModel.setContactData(args.contact)
        viewModel.contact.observe(this, Observer {
            setViewsData(it)
        })

        singleContactPhone.setOnClickListener {
            dialPhone(viewModel.getContactPhone())
        }
    }

    private fun setViewsData(contact: Contact) {
        singleContactName.text = contact.name
        singleContactBiography.text = contact.biography
        singleContactEducation.text = createFormattedDateString(
            contact.educationPeriod.start,
            contact.educationPeriod.end
        )
        singleContactTemperament.text = contact.temperament.toString().toLowerCase().capitalize()
        singleContactPhone.text = contact.phone
    }

    private fun dialPhone(phoneNumber: String) {
        val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$phoneNumber"))
        startActivity(intent)
    }

    override fun createViewModel() = ViewModelProviders.of(this).get(SingleContactViewModel::class.java)

    private fun createFormattedDateString(startDate: Date, endDate: Date): String {
        return resources.getString(
            R.string.two_dates,
            DateUtils.formatDayMonthYear.format(startDate),
            DateUtils.formatDayMonthYear.format(endDate)
        )
    }
}