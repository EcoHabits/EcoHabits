package com.ecohabits.presentation.challenges

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.ecohabits.data.local.dao.WeatherChallengeDAO
import com.ecohabits.domain.model.Challenge
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ChallengesViewModel @Inject constructor() : ViewModel() {
}

