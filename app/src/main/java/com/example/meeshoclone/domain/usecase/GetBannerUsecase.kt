package com.example.meeshoclone.domain.usecase

import com.example.meeshoclone.common.ResultState
import com.example.meeshoclone.domain.models.BannerDataModels
import com.example.meeshoclone.domain.repo.Repo

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetBannerUsecase @Inject constructor(private val repo: Repo){

    fun getBanner () : Flow<ResultState<List<BannerDataModels>>>{

        return repo.getBanner()
    }



}