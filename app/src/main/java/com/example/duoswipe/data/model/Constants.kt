package com.example.duoswipe.data.model

object Constants {
    const val SIGN_IN_REQUEST = "signInRequest"
    const val SIGN_UP_REQUEST = "signUpRequest"

    const val SIGN_IN_SCREEN = "Sign in"
    const val FORGOT_PASSWORD_SCREEN = "Forgot password"
    const val SIGN_UP_SCREEN = "Sign up"
    const val VERIFY_EMAIL_SCREEN = "Verify email"
    const val PROFILE_SCREEN = "Profile"
    const val OVERVIEW_SCREEN="Overview"
    const val CARDLISTOVERVIEW_SCREEN="Card list overview"
    const val UPDATECARD_SCREEN="Update Card Screen"
    object AuthErrors {
        val PROVIDER_ALREADY_LINKED="ERROR_EMAIL_ALREADY_LINKED"
        const val CREDENTIAL_ALREADY_IN_USE = "ERROR_CREDENTIAL_ALREADY_IN_USE"
        const val EMAIL_ALREADY_IN_USE = "ERROR_EMAIL_ALREADY_IN_USE"
    }
}