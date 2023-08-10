package com.example.zzanz_android.data.remote.api

import com.example.zzanz_android.common.Resource
import com.example.zzanz_android.data.remote.dto.BaseResponseDto
import com.example.zzanz_android.data.remote.dto.ChallengeDto
import com.example.zzanz_android.data.remote.dto.GoalAmountByCategoryDto
import com.example.zzanz_android.data.remote.dto.GoalAmountDto
import com.example.zzanz_android.data.remote.dto.SpendingListDto
import com.example.zzanz_android.data.remote.dto.request.SpendingDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
import timber.log.Timber
import javax.inject.Inject

class ChallengeServiceImpl @Inject constructor(
    private val client: HttpClient
) : ChallengeService {
    private val TAG = this.javaClass.simpleName
    override suspend fun getChallengeParticipate(cursor: Int?, page: Int): Resource<List<ChallengeDto>> {
        try {
            val response = client.get("challenge/participate") {
                parameter("cursor", cursor ?: "")
                parameter("size", page)
            }
            when(response.status){
                HttpStatusCode.OK -> {
                    return Resource.Success(response.body())
                }
                else -> {
                    throw Exception("Unknown Error")
                }
            }
        } catch (e: Exception){
            return Resource.Error(e)
        }
    }

    override suspend fun postGoalAmount(goalAmountDto: GoalAmountDto): Resource<Boolean> {
        return try {
            val response = client.post("challenge/participate") {
                contentType(ContentType.Application.Json)
                setBody(goalAmountDto)
            }
            when (response.status) {
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
            Timber.e("${e.message}")
            return Resource.Error(e)
        }
    }

    override suspend fun postCategoryGoalAmount(goalAmountDtoList: List<GoalAmountByCategoryDto>): Resource<Boolean> {
        return try {
            val response =  client.post("challenge/plan/category") {
                contentType(ContentType.Application.Json)
                setBody(goalAmountDtoList)
            }
            when (response.status) {
                HttpStatusCode.OK -> {
                    Resource.Success(true)
                }

                HttpStatusCode.BadRequest -> {
                    // 4xx - 아직 챌린지에 참여하지 않았습니다.
                    throw Exception(response.body<BaseResponseDto>().message)
                }

                else -> {
                    throw Exception("Unknown Error")
                }
            }
        } catch (e: Exception) {
            Resource.Error(e)
        }
    }

    override suspend fun putGoalAmount(goalAmountDto: GoalAmountDto): Resource<Boolean> {
        return try {
            val response = client.put("challenge/participate/goalAmount") {
                contentType(ContentType.Application.Json)
                setBody(goalAmountDto)
            }
            when (response.status) {
                HttpStatusCode.OK -> {
                    Resource.Success(true)
                }

                HttpStatusCode.BadRequest -> {
                    // 4xx - 아직 챌린지에 참여하지 않았습니다.
                    throw Exception(response.body<BaseResponseDto>().message)
                }

                else -> {
                    throw Exception("Unknown Error")
                }
            }
        } catch (e: Exception) {
            Resource.Error(e)
        }
    }

    override suspend fun getSpending(
        planId: Int,
        cursorId: Int?,
        size: Int
    ): Resource<SpendingListDto> {
        try {
            val response = client.get("challenge/plan/${planId}/spending") {
                parameter("cursorId", cursorId)
                parameter("size", size)
            }
            when (response.status) {
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
        } catch (e: Exception) {
            return Resource.Error(e)
        }
    }

    override suspend fun postSpending(planId: Int, spendingDto: SpendingDto): Resource<Boolean>{
        try {
            val response = client.post("challenge/plan/${planId}/spending"){
                setBody(spendingDto)
            }
            when(response.status){
                HttpStatusCode.OK -> {
                    return Resource.Success(true)
                }
                HttpStatusCode.BadRequest -> {
                    // 400 : 카테고리에 해당하는 챌린지 정보가 없습니다. planId = {planId}
                    // 400 : 소비내역 추가 및 변경이 가능한 기간이 아닙니다. spendingId = {spendingId}
                    // 400 : 카테고리 정보가 존재하지 않습니다. planId = {planId}
                    // 400 : 요청 파라미터 조건이 맞지 않습니다.
                    throw Exception(response.body<BaseResponseDto>().message)
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