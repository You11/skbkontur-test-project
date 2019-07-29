package ru.you11.skbkonturtestproject.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel

abstract class BaseFragment<T: ViewModel>: Fragment() {

    lateinit var viewModel: T
        private set

    protected abstract fun createViewModel(): T

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = createViewModel()
    }
}