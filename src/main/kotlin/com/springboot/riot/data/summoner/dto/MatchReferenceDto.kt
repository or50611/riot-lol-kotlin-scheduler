package com.springboot.riot.data.summoner.dto

data class MatchReferenceDto (
	var accountId: String? = null,
	var platformId: String? = null,
	var gameId: Long = 0L,
	var champion: Int = 0,
	var queue: Int = 0,
	var season: Int = 0,
	var timestamp: Long = 0L,
	var role: String? = null,
	var lane: String? = null,

)
