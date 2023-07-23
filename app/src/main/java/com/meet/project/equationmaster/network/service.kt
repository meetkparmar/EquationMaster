package com.meet.project.equationmaster.network

import com.meet.project.equationmaster.network.models.EquationsRequest
import com.meet.project.equationmaster.network.models.EquationsResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface MainService {

    @POST("v4/")
    suspend fun evaluateEquations(@Body request: EquationsRequest): Response<EquationsResponse>
}