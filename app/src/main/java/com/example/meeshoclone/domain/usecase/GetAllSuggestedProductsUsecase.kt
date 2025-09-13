package com.example.meeshoclone.domain.usecase

import com.example.meeshoclone.common.ResultState
import com.example.meeshoclone.domain.models.ProductDataModels
import com.example.meeshoclone.domain.repo.Repo

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllSuggestedProductsUsecase @Inject constructor(private val repo: Repo){

    fun getAllSuggestedProduct () : Flow<ResultState<List<ProductDataModels>>>{

        return repo.getAllSuggestedProduct()
    }



}