package com.example.zzanz_android.data.remote.api

import com.example.zzanz_android.data.remote.dto.ChallengeDto

interface ChallengeService {
    suspend fun getChallengeParticipate(cursor: Int?, page: Int): List<ChallengeDto>
}