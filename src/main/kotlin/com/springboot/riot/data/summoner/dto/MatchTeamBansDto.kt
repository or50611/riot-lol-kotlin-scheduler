package com.springboot.riot.data.summoner.dto

data class MatchTeamBansDto(
    var gameId: Long = 0L,
    var teamId: Int = 0,
    val championId: String? = null,
    val pickTurn: String? = null,

)