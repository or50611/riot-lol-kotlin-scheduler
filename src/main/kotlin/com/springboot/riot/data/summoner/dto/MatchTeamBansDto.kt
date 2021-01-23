package com.springboot.riot.data.summoner.dto

data class MatchTeamBansDto(
    var gameId: Int = 0,
    var teamId: Int = 0,
    val championId: String? = null,
    val pickTurn: String? = null,

)