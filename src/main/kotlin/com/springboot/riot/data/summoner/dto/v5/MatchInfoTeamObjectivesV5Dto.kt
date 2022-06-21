package com.springboot.riot.data.summoner.dto.v5

data class MatchInfoTeamObjectivesV5Dto (
    var matchId: String? = null,
    var gameId: Long = 0L,
    var teamId: Int = 0,
    var objective: String? = null,
    val first: Boolean = false,
    val kills: Int = 0
)