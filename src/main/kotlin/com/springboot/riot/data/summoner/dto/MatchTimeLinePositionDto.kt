package com.springboot.riot.data.summoner.dto

data class MatchTimeLinePositionDto (
    var gameId: Int = 0,
    //PARTICIPANT_FRAMES
    var frameId: Int = 0,
    var participantId: Int = 0,
    //EVENT
    var timestamp: Int = 0,

    val x: Int = 0,
    val y: Int = 0,
)