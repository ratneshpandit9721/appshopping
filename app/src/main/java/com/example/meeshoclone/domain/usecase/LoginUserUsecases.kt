package com.example.meeshoclone.domain.usecase

import com.example.meeshoclone.common.ResultState
import com.example.meeshoclone.domain.models.UserData
import com.example.meeshoclone.domain.repo.Repo

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LoginUserUsecases @Inject constructor(private val repo: Repo){

    fun loginUserWithEmailAndPassword (userData: UserData) : Flow<ResultState<String>>{

        return repo.loginUserWithEmailAndPassword(userData)
    }



}