package com.springboot.riot.data.summoner.dto

import com.google.gson.annotations.SerializedName

data class MatchParticipantTimeLineDeltaDto (
    var gameId: Int = 0,
    var participantId: Int = 0,
    var minDelta: String? = null,
    @SerializedName(value = "0-10")
    val MIN_0010: Double = 0.0,
    @SerializedName(value = "10-20")
    val MIN_1020: Double = 0.0
)