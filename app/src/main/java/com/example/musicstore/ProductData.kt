package com.example.musicstore

import com.google.gson.annotations.SerializedName


data class ProductData(
    @SerializedName("NAZWA_PRODUKTU") val nazwa: String,
    @SerializedName("PRODUCENT") val producent: String,
    @SerializedName("KATEGORIA") val kategoria: String,
    @SerializedName("CENA") val cena: Int,
    @SerializedName("ILOSC") val ilosc: Int,
    @SerializedName("ID_PRACOWNIKA") val pracownik: Int
)