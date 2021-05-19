package com.riliv.covid.ui

import com.riliv.covid.base.BaseView
import com.riliv.covid.model.Countries
import com.riliv.covid.model.SummaryModel

interface MainView : BaseView {
    /**
     * Updates the previous posts by the specified ones
     * @param posts the list of posts that will replace existing ones
     */

    fun setCountries(countries: List<Countries>)

    /**
     * Displays the loading indicator of the view
     */
    fun showLoading()

    /**
     * Hides the loading indicator of the view
     */
    fun hideLoading()
}