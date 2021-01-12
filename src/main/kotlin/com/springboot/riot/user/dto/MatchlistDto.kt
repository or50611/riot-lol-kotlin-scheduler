package com.springboot.riot.user.dto

data class MatchlistDto (
	var startIndex: Int? = 0,
	var totalGames: Int? = 0,
	var endIndex: Int? = 0,
	var matches: List<MatchReferenceDto>? = null

)
