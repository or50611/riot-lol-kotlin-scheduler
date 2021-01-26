package com.springboot.riot.data.summoner.dto

data class MatchTimeLineParticipantDto(
    var gameId: Long = 0L,
    var parentTimestamp: Long = 0L,
    var frameId: Int = 0,
    val participantId: Int = 0,
    val position: MatchTimeLinePositionDto? = null,
    val currentGold: Int = 0,
    val totalGold: Int = 0,
    val level: Int = 0,
    val xp: Int = 0,
    val minionsKilled: Int = 0,
    val jungleMinionsKilled: Int = 0,
    val dominionScore: Int = 0,
    val teamScore: Int = 0,
)