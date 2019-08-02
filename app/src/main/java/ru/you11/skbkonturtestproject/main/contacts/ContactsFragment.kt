package ru.you11.skbkonturtestproject.main.contacts

import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.core.content.edit
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
import androidx.recyclerview.widget.DividerItemDecoration
import ru.you11.skbkonturtestproject.other.Consts
import java.util.*


class ContactsFragment : BaseFragment<ContactsViewModel>(), OnContactClickListener {

    private lateinit var errorSnackbar: Snackbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

        viewModel.updateData()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_contacts, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
        setupObservers()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_contacts, menu)
        setupSearchView(menu)
    }

    private fun setupSearchView(menu: Menu) {
        (menu.findItem(R.id.search).actionView as SearchView).apply {
            queryHint = resources.getString(R.string.search_view_hint)
            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextChange(newText: String?): Boolean {
                    if (newText == null) return false
                    val searchedResult = viewModel.getSearchedContacts(newText) ?: return false
                    manageViewsVisibilityAfterSearch(searchedResult)
                    (contactsRV.adapter as ContactsRVAdapter).updateData(searchedResult)
                    return true
                }

                override fun onQueryTextSubmit(query: String?): Boolean {
                    return true
                }

                private fun manageViewsVisibilityAfterSearch(searchedResult: List<Contact>) {
                    if (searchedResult.isEmpty() && !contactsEmptyView.isVisible) {
                        setOnlyThisViewVisible(contactsEmptyView)
                    } else if (searchedResult.isNotEmpty() && contactsEmptyView.isVisible) {
                        setOnlyThisViewVisible(contactsRV)
                    }
                }
            })
        }
    }

    private fun setupViews() {
        setupRV()
        setupErrorSnackbar()
        setupSwipeToRefresh()
    }

    private fun setupRV() {
        val layoutManager = LinearLayoutManager(activity)
        val dividerItemDecoration = DividerItemDecoration(contactsRV.context, layoutManager.orientation)

        contactsRV.layoutManager = layoutManager
        contactsRV.addItemDecoration(dividerItemDecoration)
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

        if (!contactsSwipeRefresh.isEnabled) contactsSwipeRefresh.isEnabled = true

        if (loadingStatus != LoadingStatus.LOADING) {
            contactsSwipeRefresh.isRefreshing = false
        }

        when (loadingStatus) {
            LoadingStatus.LOADING -> {
                if (!contactsSwipeRefresh.isRefreshing) setOnlyThisViewVisible(contactsProgressBar)
            }

            LoadingStatus.FINISHED -> {
                setOnlyThisViewVisible(contactsRV)
            }

            LoadingStatus.EMPTY -> {
                setOnlyThisViewVisible(contactsEmptyView)
            }

            LoadingStatus.ERROR -> {
                if (viewModel.isDataEmpty()) setOnlyThisViewVisible(contactsEmptyView)
            }
        }
    }

    private fun setOnlyThisViewVisible(view: View) {
        val parent = view.parent as ViewGroup
        for (el in 0 until parent.childCount) {
            parent.getChildAt(el).visibility = View.GONE
        }

        view.visibility = View.VISIBLE
    }

    override fun createViewModel() = ViewModelProviders.of(this).get(ContactsViewModel::class.java)
}