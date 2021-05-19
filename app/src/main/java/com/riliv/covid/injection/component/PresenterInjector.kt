package com.riliv.covid.injection.component

import com.riliv.covid.base.BaseView
import com.riliv.covid.injection.module.ContextModule
import com.riliv.covid.injection.module.NetworkModule
import com.riliv.covid.ui.MainPresenter
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(modules = [(ContextModule::class), (NetworkModule::class)])
interface PresenterInjector {

    fun inject(mainPresenter: MainPresenter)


    @Component.Builder
    interface Builder {
        fun build(): PresenterInjector

        fun networkModule(networkModule: NetworkModule): Builder
        fun contextModule(contextModule: ContextModule): Builder

        @BindsInstance
        fun baseView(baseView: BaseView): Builder
    }
}