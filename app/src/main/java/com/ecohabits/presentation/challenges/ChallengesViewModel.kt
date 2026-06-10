package com.ecohabits.presentation.challenges

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.ecohabits.data.local.dao.WeatherChallengeDAO
import com.ecohabits.domain.model.Challenge
import com.ecohabits.domain.model.WeatherChallenge
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ChallengesViewModel @Inject constructor(/*private val weatherChallengeDAO: WeatherChallengeDAO*/) : ViewModel() {
/*
    val allChallenges : LiveData<List<WeatherChallenge>> = weatherChallengeDAO.loadAllChallenges()
*/
}

