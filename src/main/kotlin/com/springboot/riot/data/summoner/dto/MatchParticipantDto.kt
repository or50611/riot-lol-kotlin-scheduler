package com.springboot.riot.data.summoner.dto

data class MatchParticipantDto (
	var gameId: Int = 0,
	val participantId: Int = 0,
	val teamId: Int = 0,
	val championId: Int = 0,
	val spell1Id: Int = 0,
	val spell2Id: Int = 0,

	val stats: MatchParticipantStatsDto? = null
)