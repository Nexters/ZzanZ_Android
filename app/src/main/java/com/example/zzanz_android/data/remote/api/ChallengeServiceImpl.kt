package com.example.zzanz_android.data.remote.api

import android.util.Log
import com.example.zzanz_android.data.remote.dto.BaseResponseDto
import com.example.zzanz_android.data.remote.dto.ChallengeDto
import com.example.zzanz_android.data.remote.dto.GoalAmountByCategoryDto
import com.example.zzanz_android.data.remote.dto.GoalAmountDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
import javax.inject.Inject

class ChallengeServiceImpl @Inject constructor(
    private val client: HttpClient
) : ChallengeService {
    private val TAG = this.javaClass.simpleName
    override suspend fun getChallengeParticipate(cursor: Int?, page: Int): List<ChallengeDto> {
        return client.get("/challenge/participate") {
            cursor?.let { parameter("cursor", it) }
            parameter("page", page)
        }.body()
    }

    override suspend fun postGoalAmount(goalAmountDto: GoalAmountDto): Boolean {
        return try {
            client.post("/challenge/participate") {
                contentType(ContentType.Application.Json)
                setBody(goalAmountDto)
            }.let {
                it.status == HttpStatusCode.OK
            }
        } catch (e: ClientRequestException) {
            // 4XX response
            // TODO - 에러시 오류 로직 처리
            Log.e(TAG, "Error : ${e.response.body<BaseResponseDto>().message}")
            return false
        } catch (e: ServerResponseException) {
            // 5XX response
            // TODO - 에러시 오류 로직 처리
            Log.e(TAG, "Error : ${e.response.status.description}")
            return false
        } catch (e: Exception) {
            Log.e(TAG, "Error : ${e.message}")
            return false

        }
    }

    override suspend fun postCategoryGoalAmount(goalAmountListDto: List<GoalAmountByCategoryDto>): Boolean {
        return try {
            client.post("/challenge/plan/category") {
                contentType(ContentType.Application.Json)
                setBody(goalAmountListDto)
            }.let {
                it.status == HttpStatusCode.OK
            }
        } catch (e: ClientRequestException) {
            // 4XX response
            // TODO - 에러시 오류 로직 처리
            Log.e(TAG, "Error : ${e.response.body<BaseResponseDto>().message}")
            return false
        } catch (e: ServerResponseException) {
            // 5XX response
            // TODO - 에러시 오류 로직 처리
            Log.e(TAG, "Error : ${e.response.status.description}")
            return false
        } catch (e: Exception) {
            Log.e(TAG, "Error : ${e.message}")
            return false
        }
    }

    override suspend fun putGoalAmount(goalAmountDto: GoalAmountDto): Boolean {
        return try {
            client.post("/challenge/participate/goalAmount") {
                contentType(ContentType.Application.Json)
                setBody(goalAmountDto)
            }.let {
                it.status == HttpStatusCode.OK
            }
        } catch (e: ClientRequestException) {
            // 4XX response
            // TODO - 에러시 오류 로직 처리
            Log.e(TAG, "Error : ${e.response.body<BaseResponseDto>().message}")
            return false
        } catch (e: ServerResponseException) {
            // 5XX response
            // TODO - 에러시 오류 로직 처리
            Log.e(TAG, "Error : ${e.response.status.description}")
            return false
        } catch (e: Exception) {
            Log.e(TAG, "Error : ${e.message}")
            return false
        }
    }
}