package ru.you11.skbkonturtestproject.main

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import ru.you11.skbkonturtestproject.repository.Repository
import kotlin.coroutines.CoroutineContext

abstract class BaseViewModel: ViewModel(), CoroutineScope {

    private val job = Job()
    override val coroutineContext: CoroutineContext
        get() = job

    protected val repository = Repository()

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}