package com.example.zzanz_android.data.remote.api

import com.example.zzanz_android.data.remote.dto.ChallengeDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import javax.inject.Inject

class ChallengeServiceImpl @Inject constructor(
    private val client: HttpClient
): ChallengeService {
    override suspend fun getChallengeParticipate(cursor: Int?, page: Int): List<ChallengeDto>{
        return client.get("/challenge/participate"){
            cursor?.let { parameter("cursor", it) }
            parameter("page", page)
        }.body()
    }
}