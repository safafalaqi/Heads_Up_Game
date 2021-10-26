package com.example.headsuppgame.model

import kotlinx.serialization.SerialName
import java.io.Serializable


data class CelebritiesItem(
    @SerialName("name")
    val name: String? =null,
    @SerialName("taboo1")
    val taboo1: String? =null,
    @SerialName("taboo2")
    val taboo2: String? =null,
    @SerialName("taboo3")
    val taboo3: String? =null,
    @SerialName("pk")
    val pk: Int? =null
): Serializable