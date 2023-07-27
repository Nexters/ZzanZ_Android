package com.example.zzanz_android.data.mapper

import com.example.zzanz_android.domain.model.ChallengeModel
import com.example.zzanz_android.data.remote.dto.ChallengeDto

object ChallengeMapper: Mapper<ChallengeDto, ChallengeModel> {
    override fun toData(model: ChallengeModel): ChallengeDto {
        TODO("Not yet implemented")
    }

    override fun toModel(data: ChallengeDto): ChallengeModel {
        TODO("Not yet implemented")
    }
}