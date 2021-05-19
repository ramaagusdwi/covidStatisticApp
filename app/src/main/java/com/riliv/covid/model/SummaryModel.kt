package com.riliv.covid.model

// result generated from /json

data class SummaryModel(
    val ID: String?,
    val Message: String?,
    val Global: Global?,
    val Countries: List<Countries>?,
    val Date: String?
)

data class Countries(
    val ID: String?,
    val Country: String?,
    val CountryCode: String?,
    val Slug: String?,
    val NewConfirmed: Int?,
    val TotalConfirmed: Int?,
    val NewDeaths: Int?,
    val TotalDeaths: Int?,
    val NewRecovered: Int?,
    val TotalRecovered: Int?,
    val Date: String?,
    val Selected: Boolean? = false,
//    val Premium: Premium?
)

data class Global(
    val NewConfirmed: Int?,
    val TotalConfirmed: Int?,
    val NewDeaths: Int?,
    val TotalDeaths: Int?,
    val NewRecovered: Int?,
    val TotalRecovered: Int?,
    val Date: String?
)

//data class Premium()
