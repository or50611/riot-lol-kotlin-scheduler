package com.springboot.riot.data.summoner.dto

data class MatchParticipantDto (
	var gameId: Long = 0L,
	val participantId: Int = 0,
	val teamId: Int = 0,
	val championId: Int = 0,
	val spell1Id: Int = 0,
	val spell2Id: Int = 0,
	val accountId: String? = null,

	val stats: MatchParticipantStatsDto? = null,
	val timeline: MatchParticipantTimeLineDto? = null,

	//이건 회사에서 작업한거임.
)