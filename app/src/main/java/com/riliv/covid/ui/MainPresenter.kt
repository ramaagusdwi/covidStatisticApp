package com.riliv.covid.ui

import android.util.Log
import com.riliv.covid.R
import com.riliv.covid.base.BasePresenter
import com.riliv.covid.network.ApplicationApi
import com.riliv.covid.support.util.Utils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import okhttp3.RequestBody
import retrofit2.HttpException
import javax.inject.Inject


class MainPresenter(MainView: MainView) :
    BasePresenter<MainView>(MainView) {
    @Inject
    lateinit var api: ApplicationApi

    private var subscription: Disposable? = null

    fun getSummary() {
        view.showLoading()
        subscription = api
            .getSummary()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .doOnTerminate { }
            .subscribe(
                { resp ->
                    view.hideLoading()
                    view.setCountries(resp!!.Countries!!)
                },

                { error ->
                    view.hideLoading()
                    if (error.message != null) {
                        view.showError(error.message!!)
                    } else {
                        view.showError(R.string.warning_unknown_error)

                    }

                }
            )
    }

    override fun onViewDestroyed() {
        subscription?.dispose()
    }
}