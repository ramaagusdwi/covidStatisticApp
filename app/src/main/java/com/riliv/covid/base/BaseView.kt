package com.riliv.covid.base

import android.content.Context
import androidx.annotation.StringRes

interface BaseView {
    /**
     * Returns the Context in which the application is running.
     * @return the Context in which the application is running
     */
    fun getContext(): Context

    /**
     * Displays an error in the view
     * @param errorResId the resource id_komponen of the error to display in the view
     */
    fun showError(@StringRes errorResId: Int)

    /**
     * Displays an error in the view
     * @param error the error to display in the view
     */
    fun showError(error: String)

}