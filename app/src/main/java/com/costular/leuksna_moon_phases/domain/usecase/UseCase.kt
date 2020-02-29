package com.costular.leuksna_moon_phases.domain.usecase

abstract class UseCase<in M, out T> {

    suspend fun execute(input: M): T {
        return doWork(input)
    }

    protected abstract suspend fun doWork(input: M): T

}