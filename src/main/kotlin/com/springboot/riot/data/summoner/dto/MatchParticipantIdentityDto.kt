package com.springboot.riot.data.summoner.dto

data class MatchParticipantIdentityDto (
	val participantId: Int = 0,
	val player: MatchPlayerDto? = null
)
