package com.springboot.riot.data.summoner.dto.v5

data class MatchTimeFrameEventV5Dto(
    val realTimestamp: Long = 0L,
    val timestamp: Long = 0L,
    val type: String? = null,
)
