package com.zzanz.swip_android.data.remote.api

import com.zzanz.swip_android.common.Resource
import com.zzanz.swip_android.data.remote.dto.BaseResponseDto
import com.zzanz.swip_android.data.remote.dto.FcmTokenDto
import com.zzanz.swip_android.data.remote.dto.NotificationTimeDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
import javax.inject.Inject

class NotificationServiceImpl @Inject constructor(
    private val client: HttpClient
) : NotificationService {
    override suspend fun postNotificationTime(notificationTime: NotificationTimeDto): Resource<Boolean> {
        return try {
            val response = client.post("notification/time") {
                contentType(ContentType.Application.Json)
                setBody(notificationTime)
            }
            when (response.status) {
                HttpStatusCode.OK -> {
                    Resource.Success(true)
                }

                HttpStatusCode.BadRequest -> {
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

    override suspend fun postFcmToken(fcmToken: FcmTokenDto): Resource<Boolean> {
        return try {
            val response = client.post("notification/register") {
                contentType(ContentType.Application.Json)
                setBody(fcmToken)
            }
            when (response.status) {
                HttpStatusCode.OK -> {
                    Resource.Success(true)
                }

                HttpStatusCode.BadRequest -> {
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
}