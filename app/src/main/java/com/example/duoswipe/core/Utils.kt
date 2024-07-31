package com.example.duoswipe.core

import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import android.widget.Toast

class Utils {
    companion object {
        fun print(e: Exception) = Log.e(TAG, e.stackTraceToString())

        fun showMessage(
            context: Context,
            message: String?
        ) = Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }
}