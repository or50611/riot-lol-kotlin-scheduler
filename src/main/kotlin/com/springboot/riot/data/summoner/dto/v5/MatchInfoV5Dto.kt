package com.springboot.riot.data.summoner.dto.v5

data class MatchInfoV5Dto(

    val gameCreation: Long = 0L,
    val gameDuration: Long = 0L,
    val gameEndTimestamp: Long = 0L,
    val gameId: Long = 0L,
    val gameMode: String? = null,
    val gameName: String? = null,
    val gameStartTimestamp: Long = 0L,
    val gameType: String? = null,
    val gameVersion: String? = null,
    val mapId: Int = 0,
    val platformId: String? = null,
    val queueId: Int = 0,
    val tournamentCode: String? = null,

    val participants: List<MatchInfoParticipantsV5Dto>? = null,
    val teams: List<MatchInfoTeamV5Dto>? = null

)
