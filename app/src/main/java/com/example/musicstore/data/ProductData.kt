package com.example.musicstore.data

import com.google.gson.annotations.SerializedName

open class ProductData(
    @SerializedName("NAZWA_PRODUKTU") val nazwa: String,
    @SerializedName("PRODUCENT") val producent: String,
    @SerializedName("KATEGORIA") val kategoria: String,
    @SerializedName("CENA") var cena: Int,
    @SerializedName("ILOSC") val ilosc: Int,
    @SerializedName("ID_PRACOWNIKA") val pracownik: Int,
    @SerializedName("KOD_KRESKOWY") val kod_kreskowy: String
)