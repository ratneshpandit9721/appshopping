package com.example.meeshoclone.domain.usecase

import android.net.Uri
import com.example.meeshoclone.common.ResultState
import com.example.meeshoclone.domain.models.UserDataParent
import com.example.meeshoclone.domain.repo.Repo

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserProfileImageUsecase @Inject constructor(private val repo: Repo){

    fun userProfileImage (uri: Uri) : Flow<ResultState<String>>{

        return repo.userProfileImage(uri)
    }



}