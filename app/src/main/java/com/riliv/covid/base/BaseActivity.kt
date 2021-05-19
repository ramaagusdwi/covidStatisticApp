package com.riliv.covid.base

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.riliv.covid.support.util.Utils

abstract class BaseActivity<P : BasePresenter<BaseView>> : BaseView, AppCompatActivity() {
    protected lateinit var presenter: P

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter = instantiatePresenter()
    }

    /**
     * Instantiates the presenter the Activity is based on.
     */
    protected abstract fun instantiatePresenter(): P

    override fun getContext(): Context {
        return this
    }


    override fun showError(errorResId: Int) {
        Utils.showToast(getContext(), getString(errorResId))
    }

    override fun showError(error: String) {
        Utils.showToast(getContext(), error)
    }

}