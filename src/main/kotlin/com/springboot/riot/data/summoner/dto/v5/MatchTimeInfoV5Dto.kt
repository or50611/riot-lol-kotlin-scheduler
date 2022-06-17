package com.springboot.riot.data.summoner.dto.v5

data class MatchTimeInfoV5Dto(
    val frameInterval: Int = 0,
    val frames: List<MatchTimeFrameV5Dto>? = null,
    val gameId: Long = 0L,
    val participants: List<MatchTimeParticipantV5Dto>? = null
)
