package com.springboot.riot.data.summoner.dto

import com.fasterxml.jackson.annotation.JsonAnyGetter
import com.fasterxml.jackson.annotation.JsonAnySetter

class MatchParticipantTimeLineDeltaDto {
    var gameId: Int = 0
    var participantId: Int = 0
    var minDelta: String? = null

    var data: MutableMap<String, Any> = java.util.HashMap()

    val min0010: Double
        get() {
            return (data["0-10"] as? Double) ?: 0.0
        }
    val min1020: Double
        get() {
            return (data["10-20"] as? Double) ?: 0.0
        }

    @JsonAnyGetter
    fun getAdditionalProperties(): Map<String, Any>? {
        return data
    }

    @JsonAnySetter
    fun setAdditionalProperty(name: String, value: Any) {
        data[name] = value
    }
}