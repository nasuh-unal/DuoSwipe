package com.example.duoswipe.ui.cardStack

import androidx.lifecycle.ViewModel
import com.example.duoswipe.data.repository.DatabaseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CardStackViewModel @Inject() constructor(
    private val databaseRepository: DatabaseRepository
) : ViewModel() {

}