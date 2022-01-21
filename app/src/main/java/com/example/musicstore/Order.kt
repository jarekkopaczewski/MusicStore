package com.example.musicstore

import com.google.gson.annotations.SerializedName

data class Order(
    @SerializedName("ID_ZAMOWIENIA") val id_zamowienia: Int,
    @SerializedName("ID_KLIENTA") val id_klienta: Int,
    @SerializedName("STATUS") val status: String,
    @SerializedName("WARTOSC") val wartosc: Int
)