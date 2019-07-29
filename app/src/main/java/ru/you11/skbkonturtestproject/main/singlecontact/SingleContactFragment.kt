package ru.you11.skbkonturtestproject.main.singlecontact

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.navArgs
import kotlinx.android.synthetic.main.fragment_single_contact.*
import ru.you11.skbkonturtestproject.R
import ru.you11.skbkonturtestproject.main.BaseFragment

class SingleContactFragment : BaseFragment<SingleContactViewModel>() {

    private val args: SingleContactFragmentArgs by navArgs()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_single_contact, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setViewsData()
    }

    private fun setViewsData() {
        val person = args.person
        singleContactName.text = person.name
        singleContactBiography.text = person.biography
        singleContactEducation.text = person.educationPeriod.start.toString() + " - " + person.educationPeriod.end.toString()
        singleContactTemperament.text = person.temperament.ordinal.toString().capitalize()
        singleContactPhone.text = person.phone
    }

    override fun createViewModel() = ViewModelProviders.of(this).get(SingleContactViewModel::class.java)
}