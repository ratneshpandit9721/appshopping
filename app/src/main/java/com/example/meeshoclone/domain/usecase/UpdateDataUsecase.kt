package com.example.meeshoclone.domain.usecase

import com.example.meeshoclone.common.ResultState
import com.example.meeshoclone.domain.models.UserData
import com.example.meeshoclone.domain.models.UserDataParent
import com.example.meeshoclone.domain.repo.Repo

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class UpdateDataUsecase @Inject constructor(private val repo: Repo){

    fun updateUserData (userDataParent: UserDataParent) : Flow<ResultState<String>>{

        return repo.updateUserData(userDataParent)
    }



}