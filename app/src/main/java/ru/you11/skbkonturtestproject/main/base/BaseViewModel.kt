package ru.you11.skbkonturtestproject.main.base

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import ru.you11.skbkonturtestproject.db.ContactDatabase
import ru.you11.skbkonturtestproject.repository.Repository
import kotlin.coroutines.CoroutineContext

abstract class BaseViewModel(application: Application): AndroidViewModel(application), CoroutineScope {

    private val job = Job()
    override val coroutineContext: CoroutineContext
        get() = job

    protected val repository: Repository

    init {
        val dao = ContactDatabase.getDatabase(application).contactDao()
        repository = Repository(dao)

    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}