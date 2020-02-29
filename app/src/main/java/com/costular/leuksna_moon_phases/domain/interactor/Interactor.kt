package com.costular.leuksna_moon_phases.domain.interactor

abstract class Interactor<in M, out T> {

    suspend fun execute(input: M): T {
        return doWork(input)
    }

    protected abstract suspend fun doWork(input: M): T

}