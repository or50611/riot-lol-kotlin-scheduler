package com.springboot.riot.data.summoner.dto

data class MatchDto (

    var gameId: Long = 0L,
    var platformId: String? = null,
    var gameCreation: Long = 0L,
    var gameDuration: Long = 0L,
    var queueId: Int = 0,
    var mapId: Int = 0,
    var seasonId: Int = 0,
    var gameVersion: String? = null,
    var gameMode: String? = null,
    var gameType: String? = null,

    var teams: List<TeamStatsDto>? = null,
    var participants: List<ParticipantDto>? = null,
    var participantIdentities: List<ParticipantIdentityDto>? = null
			
)