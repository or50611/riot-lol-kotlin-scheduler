package com.springboot.riot.data.summoner.dto

data class ParticipantDto (
	var participantId: Int = 0,
	var teamId: Int = 0,
	var championId: Int = 0,
	var spell1Id: Int = 0,
	var spell2Id: Int = 0,
	var stats: ParticipantStatsDto? = null

)
