package ru.you11.skbkonturtestproject.main.contacts

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_contacts.*
import ru.you11.skbkonturtestproject.R
import ru.you11.skbkonturtestproject.main.LoadingStatus
import ru.you11.skbkonturtestproject.main.base.BaseFragment
import ru.you11.skbkonturtestproject.models.Contact

class ContactsFragment : BaseFragment<ContactsViewModel>(), OnContactClickListener {

    private lateinit var errorSnackbar: Snackbar

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_contacts, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
        setupObservers()
    }

    private fun setupViews() {
        setupRV()
        setupErrorSnackbar()
        setupSwipeToRefresh()
    }

    private fun setupRV() {
        contactsRV.layoutManager = LinearLayoutManager(activity)
        contactsRV.adapter = ContactsRVAdapter(this)
    }

    private fun setupErrorSnackbar() {
        if (!::errorSnackbar.isInitialized) errorSnackbar = createErrorSnackbar()
    }

    private fun setupSwipeToRefresh() {
        contactsSwipeRefresh.isEnabled = false
        contactsSwipeRefresh.setOnRefreshListener {
            if (viewModel.loadingStatus.value != LoadingStatus.LOADING) {
                viewModel.updateData()
            }
        }
    }

    private fun setupObservers() {
        setupDataObserver()
        setupErrorObserver()
        setupLoadingStatusObserver()
    }

    private fun createErrorSnackbar(): Snackbar {
        return Snackbar.make(view!!, "", Snackbar.LENGTH_INDEFINITE)
    }

    override fun onContactClick(contact: Contact) {
        val action = ContactsFragmentDirections.actionContactsFragmentToSingleContactFragment(contact)
        findNavController().navigate(action)
    }

    private fun setupDataObserver() {
        viewModel.contacts.observe(this, Observer {
            onDataUpdate(it)
        })
    }

    private fun setupErrorObserver() {
        viewModel.error.observe(this, Observer {
            onErrorUpdate(it)
        })
    }

    private fun setupLoadingStatusObserver() {
        viewModel.loadingStatus.observe(this, Observer {
            onLoadingStatusUpdate(it)
        })
    }

    private fun onDataUpdate(data: List<Contact>) {
        (contactsRV.adapter as ContactsRVAdapter).updateData(data)
    }

    private fun onErrorUpdate(error: String) {
        if (error.isEmpty()) {
            errorSnackbar.dismiss()
            return
        }

        errorSnackbar.setText(error)
        errorSnackbar.show()
    }

    private fun onLoadingStatusUpdate(loadingStatus: LoadingStatus) {
        when (loadingStatus) {

            LoadingStatus.LOADING -> {
                if (isAdapterEmpty()) {
                    setContentVisibility(false)
                } else {
                    contactsSwipeRefresh.isRefreshing = true
                }
            }

            LoadingStatus.FINISHED -> {
                setContentVisibility(true)
                contactsSwipeRefresh.isEnabled = true
                contactsSwipeRefresh.isRefreshing = false
            }

            LoadingStatus.EMPTY -> {

            }
        }
    }

    private fun setContentVisibility(isVisible: Boolean) {
        contactsRV.isVisible = isVisible
        contactsProgressBar.isVisible = !isVisible
    }

    private fun isAdapterEmpty() = (contactsRV.adapter as ContactsRVAdapter).itemCount == 0

    override fun createViewModel() = ViewModelProviders.of(this).get(ContactsViewModel::class.java)
}