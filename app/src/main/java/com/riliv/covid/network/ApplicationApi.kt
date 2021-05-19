package com.riliv.covid.network

import com.riliv.covid.model.SummaryModel
import io.reactivex.Observable
import okhttp3.RequestBody
import retrofit2.http.*

interface ApplicationApi {
    @Headers("Content-Type: application/json")
    @GET("summary")
    fun getSummary(
    ): Observable<SummaryModel?>
}