package com.ecohabits.data.remote.dto

import com.google.gson.annotations.SerializedName

data class LocationResponseDto(
    @SerializedName("address")
    val address: Address
)

data class Address(
    @SerializedName("city")
    val city: String
)
