package com.example.zzanz_android.data.remote.api

import android.annotation.SuppressLint
import com.example.zzanz_android.common.Resource
import com.example.zzanz_android.data.remote.dto.BaseResponseDto
import com.example.zzanz_android.data.remote.dto.NotificationDto
import com.google.firebase.messaging.FirebaseMessagingService
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
) : NotificationService{
    override suspend fun postNotificationConfig(notificationDto: NotificationDto): Resource<Boolean> {
        return try {
            val response = client.post("notification/config") {
                contentType(ContentType.Application.Json)
                setBody(notificationDto)
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