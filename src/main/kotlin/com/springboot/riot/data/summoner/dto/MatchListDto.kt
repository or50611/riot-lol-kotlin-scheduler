package com.springboot.riot.data.summoner.dto

data class MatchListDto (
	var startIndex: Int? = 0,
	var totalGames: Int? = 0,
	var endIndex: Int? = 0,
	var matches: List<MatchReferenceDto>? = null

)
