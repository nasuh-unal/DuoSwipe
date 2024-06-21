package com.example.duoswipe.ui.register

data class SignInResult(
    val data: UserData?,
    val errorMessage: String?
)
data class UserData(
    val userId: String,
    val username: String?,
    val profilePictureId: String?
)