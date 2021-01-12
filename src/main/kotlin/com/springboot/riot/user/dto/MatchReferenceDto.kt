package com.springboot.riot.user.dto

data class MatchReferenceDto (
	var gameId: Long = 0L,
	var role: String? = null,
	var season: Int? = 0,
	var platformId: String? = null,
	var champion: Int? = 0,
	var queue: Int? = 0,
	var lane: String? = null,
	var timestamp: Long? = 0L
	
)
