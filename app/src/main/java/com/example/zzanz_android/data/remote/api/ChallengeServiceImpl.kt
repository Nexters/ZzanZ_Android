package com.example.zzanz_android.data.remote.api

import android.util.Log
import com.example.zzanz_android.common.Resource
import com.example.zzanz_android.data.remote.dto.BaseResponseDto
import com.example.zzanz_android.data.remote.dto.ChallengeDto
import com.example.zzanz_android.data.remote.dto.GoalAmountByCategoryDto
import com.example.zzanz_android.data.remote.dto.GoalAmountDto
import com.example.zzanz_android.data.remote.dto.SpendingListDto
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
import io.ktor.http.cio.Response
import io.ktor.http.contentType
import timber.log.Timber
import javax.inject.Inject

class ChallengeServiceImpl @Inject constructor(
    private val client: HttpClient
) : ChallengeService {
    private val TAG = this.javaClass.simpleName
    override suspend fun getChallengeParticipate(cursor: Int?, page: Int): List<ChallengeDto> {
        return client.get("challenge/participate") {
            parameter("cursor", cursor)
            parameter("page", page)
        }.body()
    }

    override suspend fun postGoalAmount(goalAmountDto: GoalAmountDto): Resource<Boolean> {
        return try {
            val response = client.post("challenge/participate") {
                contentType(ContentType.Application.Json)
                setBody(goalAmountDto)
            }
            when(response.status){
                HttpStatusCode.OK -> {
                    return Resource.Success(true)
                }
                HttpStatusCode.BadRequest -> {
                    // 400 : 이미 다음 챌린지에 참여중입니다.
                    throw Exception(response.body<BaseResponseDto>().message)
                }
                else -> {
                    throw Exception("Unknown Error")
                }
            }
        } catch (e: Exception) {
            Timber.e("$TAG [postGoalAmount] ${e.message}")
            return Resource.Error(e)
        }
    }

    override suspend fun postCategoryGoalAmount(goalAmountDtoList: List<GoalAmountByCategoryDto>): Resource<Boolean> {
        return try {
            client.post("challenge/plan/category") {
                contentType(ContentType.Application.Json)
                setBody(goalAmountDtoList)
            }.let {
                Resource.Success(it.status == HttpStatusCode.OK)
            }
        } catch (e: ClientRequestException) {
            // 4XX response
            // TODO - 에러시 오류 로직 처리
            Log.e(TAG, "Error : ${e.response.body<BaseResponseDto>().message}")
            return Resource.Error(e)
        } catch (e: ServerResponseException) {
            // 5XX response
            // TODO - 에러시 오류 로직 처리
            Log.e(TAG, "Error : ${e.response.status.description}")
            return Resource.Error(e)
        } catch (e: Exception) {
            Log.e(TAG, "Error : ${e.message}")
            return Resource.Error(e)
        }
    }

    override suspend fun putGoalAmount(goalAmountDto: GoalAmountDto): Resource<Boolean> {
        return try {
            client.post("challenge/participate/goalAmount") {
                contentType(ContentType.Application.Json)
                setBody(goalAmountDto)
            }.let {
                Resource.Success(it.status == HttpStatusCode.OK)
            }
        } catch (e: ClientRequestException) {
            // 4XX response
            // TODO - 에러시 오류 로직 처리
            Log.e(TAG, "Error : ${e.response.body<BaseResponseDto>().message}")
            return Resource.Error(e)
        } catch (e: ServerResponseException) {
            // 5XX response
            // TODO - 에러시 오류 로직 처리
            Log.e(TAG, "Error : ${e.response.status.description}")
            return Resource.Error(e)
        } catch (e: Exception) {
            Log.e(TAG, "Error : ${e.message}")
            return Resource.Error(e)
        }
    }

    override suspend fun getSpending(planId: Int, cursorId: Int?, size: Int): Resource<SpendingListDto> {
        try {
            val response = client.get("challenge/plan/${planId}/spending"){
                parameter("cursorId", cursorId)
                parameter("size", size)
            }
            when(response.status){
                HttpStatusCode.OK -> {
                    return Resource.Success(response.body())
                }
                HttpStatusCode.BadRequest -> {
                    // 400 : 카테고리 정보가 존재하지 않습니다.
                    throw Exception(response.status.description)
                }
                else -> {
                    throw Exception("Unknown Error")
                }
            }
        } catch (e: Exception){
            return Resource.Error(e)
        }
    }
}