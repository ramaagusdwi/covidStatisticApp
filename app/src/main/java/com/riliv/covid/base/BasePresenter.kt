package com.riliv.covid.base

import com.riliv.covid.injection.component.DaggerPresenterInjector
import com.riliv.covid.injection.component.PresenterInjector
import com.riliv.covid.injection.module.ContextModule
import com.riliv.covid.injection.module.NetworkModule
import com.riliv.covid.ui.MainPresenter


abstract class BasePresenter<out V : BaseView>(protected val view: V) {
    private val injector: PresenterInjector = DaggerPresenterInjector
        .builder()
        .baseView(view)
        .contextModule(ContextModule)
        .networkModule(NetworkModule)
        .build()

    init {
        inject()
    }

    /**
     * This method may be called when the presenter view is created
     */
    //open fun onViewCreated(){}

    /**
     * This method may be called when the presenter view is destroyed
     */
    open fun onViewDestroyed() {}

    /**
     * Injects the required dependencies
     */
    private fun inject() {
        when (this) {
            is MainPresenter -> injector.inject(this)
        }
    }
}