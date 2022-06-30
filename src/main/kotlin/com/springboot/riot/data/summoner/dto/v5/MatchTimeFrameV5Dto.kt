package com.springboot.riot.data.summoner.dto.v5

import com.fasterxml.jackson.annotation.JsonAnyGetter
import com.fasterxml.jackson.annotation.JsonAnySetter
import java.util.HashMap

class MatchTimeFrameV5Dto {
    val events: List<MatchTimeFrameEventV5Dto>? = null
    val timestamp: Long = 0L

    var participantFrames: MutableMap<String, MatchTimeFramePartV5Dto> = HashMap()

    @JsonAnyGetter
    fun getDataProperties(): Map<String, MatchTimeFramePartV5Dto>? {
        return participantFrames
    }

    @JsonAnySetter
    fun setDataProperties(name: String, value: MatchTimeFramePartV5Dto) {
        participantFrames[name] = value
    }
}
