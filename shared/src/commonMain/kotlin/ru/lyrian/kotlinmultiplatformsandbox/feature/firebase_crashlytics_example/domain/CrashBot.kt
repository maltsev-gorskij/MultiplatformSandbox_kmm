package ru.lyrian.kotlinmultiplatformsandbox.feature.firebase_crashlytics_example.domain

import kotlin.random.Random

class CrashBot {
    private val dispatchCall = {
        when(Random.nextInt(4)){
            0 -> goCrash0()
            1 -> goCrash1()
            2 -> goCrash2()
            3 -> goCrash3()
        }
    }

    fun goCrash() {
        internalDispatch()
    }

    private fun internalDispatch() = dispatchCall()

    private fun internalDispatch1() = dispatchCall()

    private fun internalDispatch2() = dispatchCall()

    private fun goCrash0() {
        internalDispatch()
    }

    private fun goCrash1() {
        internalDispatch1()
    }

    private fun goCrash2() {
        internalDispatch2()
    }

    private fun goCrash3() {
        okCrash()
    }

    @Suppress("UseCheckOrError")
    private fun okCrash(){
        throw IllegalStateException("Nap time ...")
    }
}
