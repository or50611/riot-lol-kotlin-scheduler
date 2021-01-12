package com.springboot.riot.user.dto

data class ParticipantStatsDto (
	
	var participantId: Int = 0,
	var win: Boolean = false,
	var kills: Int = 0,
	var deaths: Int = 0,
	var assists: Int = 0,
	var totalMinionsKilled: Int = 0,
	var neutralMinionsKilled: Int = 0,
	var champLevel: Int = 0,
	var goldEarned: Int = 0,
	var turretKills: Int = 0,
	var wardsPlaced: Int = 0,
	var wardsKilled: Int = 0,
	var visionWardsBoughtInGame: Int = 0
	
)