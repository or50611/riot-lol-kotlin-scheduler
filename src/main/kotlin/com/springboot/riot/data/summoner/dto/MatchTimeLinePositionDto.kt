package com.springboot.riot.data.summoner.dto

data class MatchTimeLinePositionDto (
    var gameId: Long = 0L,
    var parentTimestamp: Long = 0L,
    //PARTICIPANT_FRAMES
    var frameId: Int = 0,
    var participantId: Int = 0,
    //EVENT
    var timestamp: Long = 0L,

    val x: Int = 0,
    val y: Int = 0,
)