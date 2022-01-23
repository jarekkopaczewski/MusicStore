package com.example.musicstore

import com.google.gson.annotations.SerializedName

data class Address(
    @SerializedName("MIASTO") val miasto: String,
    @SerializedName("ULICA") val ulica: String,
    @SerializedName("NUMER_MIESZKANIA") val numer_domu: Int,
    @SerializedName("KOD_POCZTOWY") val kod_pocztowy: String
)