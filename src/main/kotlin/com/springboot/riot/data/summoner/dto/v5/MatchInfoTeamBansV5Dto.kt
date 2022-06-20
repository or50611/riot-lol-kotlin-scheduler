package com.springboot.riot.data.summoner.dto.v5

data class MatchInfoTeamBansV5Dto (

    var matchId: String? = null,
    var gameId: Long = 0L,
    val championId: Int = 0,
    val pickTurn: Int = 0
)