package com.costular.leuksna_moon_phases.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.costular.leuksna_moon_phases.util.net.DispatcherFactory
import io.uniflow.android.flow.AndroidDataFlow
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

abstract class BaseViewModel(
    private val dispatcherFactory: DispatcherFactory
) : AndroidDataFlow() {

    fun launchOnIO(block: suspend CoroutineScope.() -> Unit) {
        viewModelScope.launch(context = dispatcherFactory.io, block = block)
    }

    fun ViewModel.launchOnMain(block: suspend CoroutineScope.() -> Unit) {
        viewModelScope.launch(context = dispatcherFactory.main, block = block)
    }

    fun ViewModel.launchOnDefault(block: suspend CoroutineScope.() -> Unit) {
        viewModelScope.launch(context = dispatcherFactory.default, block = block)
    }

}