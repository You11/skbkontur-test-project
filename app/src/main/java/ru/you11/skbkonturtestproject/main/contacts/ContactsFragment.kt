package ru.you11.skbkonturtestproject.main.contacts

import android.content.Context
import android.os.Bundle
import android.util.Log
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

        if (isUpdateNeeded()) {
            Log.d("meow", "update needed!")
            viewModel.updateData()
        } else {
            Log.d("meow", "update not needed!")
            viewModel.setCachedData()
        }
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
                    (contactsRV.adapter as ContactsRVAdapter).updateData(searchedResult)
                    return true
                }

                override fun onQueryTextSubmit(query: String?): Boolean {
                    return true
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
        if (loadingStatus != LoadingStatus.LOADING) {
            saveLastUpdateDatetime()
        }

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

    private fun saveLastUpdateDatetime() {
        val prefs = activity?.getSharedPreferences(Consts.Prefs.appPrefs, Context.MODE_PRIVATE)
        prefs?.edit {
            val time = Date().time
            putLong(Consts.Prefs.appPrefsLastUpdate, time)
            Log.d("meow", "saved! $time")
            apply()
        }
    }

    private fun getLastUpdateDatetime(): Long? {
        val prefs = activity?.getSharedPreferences(Consts.Prefs.appPrefs, Context.MODE_PRIVATE)
        val lastUpdateTime = prefs?.getLong(Consts.Prefs.appPrefsLastUpdate, 0)
        return if (lastUpdateTime == 0L) null else lastUpdateTime
    }

    private fun isUpdateNeeded(): Boolean {
        val lastVisitTime = getLastUpdateDatetime() ?: return true
        val timeDiffForUpdateInMillis = 60000L
        val currentTime = Date().time
        Log.d("meow", "saved time: $lastVisitTime")
        Log.d("meow", "current time: $currentTime")
        return (lastVisitTime + timeDiffForUpdateInMillis) < currentTime
    }

    private fun setContentVisibility(isVisible: Boolean) {
        contactsRV.isVisible = isVisible
        contactsProgressBar.isVisible = !isVisible
    }

    private fun isAdapterEmpty() = (contactsRV.adapter as ContactsRVAdapter).itemCount == 0

    override fun createViewModel() = ViewModelProviders.of(this).get(ContactsViewModel::class.java)
}