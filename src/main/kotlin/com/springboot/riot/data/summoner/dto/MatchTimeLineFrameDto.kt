package com.springboot.riot.data.summoner.dto

import com.fasterxml.jackson.annotation.JsonAnyGetter
import com.fasterxml.jackson.annotation.JsonAnySetter
import java.util.HashMap

class MatchTimeLineFrameDto {
    var gameId: Int = 0
    val timestamp: Int = 0
    val events: List<MatchTimeLineEventDto>? = null

    var participantFrames: MutableMap<String, MatchTimeLineParticipantDto> = HashMap()

    @JsonAnyGetter
    fun getAdditionalProperties(): Map<String, MatchTimeLineParticipantDto>? {
        return participantFrames
    }

    @JsonAnySetter
    fun setAdditionalProperty(name: String, value: MatchTimeLineParticipantDto) {
        participantFrames[name] = value
    }
}
