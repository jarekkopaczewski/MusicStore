package com.example.musicstore

import com.google.gson.annotations.SerializedName

data class ProductSM(
    @SerializedName("KOD_KRESKOWY") val kod_kreskowy: String,
    @SerializedName("NAZWA_PRODUKTU") val nazwa: String,
    @SerializedName("DOSTEPNOSC") val dostepnosc: String,
    @SerializedName("ILOSC") val ilosc: Int
)