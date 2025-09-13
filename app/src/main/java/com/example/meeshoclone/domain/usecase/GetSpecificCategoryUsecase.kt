package com.example.meeshoclone.domain.usecase

import com.example.meeshoclone.common.ResultState
import com.example.meeshoclone.domain.models.ProductDataModels
import com.example.meeshoclone.domain.repo.Repo

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class GetSpecificCategoryUsecase @Inject constructor(private val repo: Repo) {

    fun getSpecificCategoryItems(categoryName : String): Flow<ResultState<List<ProductDataModels>>>{

        return repo.getSpecificCategoryItems(categoryName)
    }
}



