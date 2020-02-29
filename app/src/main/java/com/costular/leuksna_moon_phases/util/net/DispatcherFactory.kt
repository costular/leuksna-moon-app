package com.costular.leuksna_moon_phases.util.net

import kotlinx.coroutines.CoroutineDispatcher

interface DispatcherFactory {

    val main: CoroutineDispatcher
    val io: CoroutineDispatcher
    val default: CoroutineDispatcher

}