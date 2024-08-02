package com.example.duoswipe.data.model

object Constants {
    const val SIGN_IN_REQUEST = "signInRequest"
    const val SIGN_UP_REQUEST = "signUpRequest"

    object AuthErrors {
        val PROVIDER_ALREADY_LINKED="ERROR_EMAIL_ALREADY_LINKED"
        const val CREDENTIAL_ALREADY_IN_USE = "ERROR_CREDENTIAL_ALREADY_IN_USE"
        const val EMAIL_ALREADY_IN_USE = "ERROR_EMAIL_ALREADY_IN_USE"
    }
}