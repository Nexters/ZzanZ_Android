package com.example.zzanz_android.data.remote.api

import com.example.zzanz_android.data.remote.dto.ChallengeDto
import com.example.zzanz_android.data.remote.dto.GoalAmountDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import javax.inject.Inject

class ChallengeServiceImpl @Inject constructor(
    private val client: HttpClient
) : ChallengeService {
    override suspend fun getChallengeParticipate(cursor: Int?, page: Int): List<ChallengeDto> {
        return client.get("/challenge/participate") {
            cursor?.let { parameter("cursor", it) }
            parameter("page", page)
        }.body()
    }

    override suspend fun postGoalAmount(goalAmountDto: GoalAmountDto): Any {
        return client.post("/challenge/participate") {
            contentType(ContentType.Application.Json)
            setBody(goalAmountDto)
        }
    }

    override suspend fun postCategoryGoalAmount(goalAmountListDto: List<GoalAmountDto>): Any {
        return client.post("/challenge/plan/category") {
            contentType(ContentType.Application.Json)
            setBody(goalAmountListDto)
        }
    }

    override suspend fun putGoalAmount(goalAmountDto: GoalAmountDto): Any {
        return client.put("/challenge/participate/goalAmount") {
            contentType(ContentType.Application.Json)
            setBody(goalAmountDto)
        }
    }
}