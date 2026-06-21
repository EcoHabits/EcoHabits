package com.ecohabits.data.remote.dto

import com.google.gson.annotations.SerializedName

data class LocationResponseDto(
    @SerializedName("address")
    val address: Address
)

data class Address(
    @SerializedName("city")
    val city: String? = null,
    @SerializedName("town")
    val town: String? = null,
    @SerializedName("village")
    val village: String? = null,
    @SerializedName("suburb")
    val suburb: String? = null
)
