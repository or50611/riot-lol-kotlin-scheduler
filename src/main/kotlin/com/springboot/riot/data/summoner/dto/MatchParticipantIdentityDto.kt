package com.springboot.riot.data.summoner.dto

data class MatchParticipantIdentityDto (
	var gameId: Int = 0,
	val participantId: Int = 0,
	val player: MatchPlayerDto? = null
)
