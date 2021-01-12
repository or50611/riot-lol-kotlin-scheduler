package com.springboot.riot.user.dto

data class UserMatchListInfoDto (
	
	var gameId: Long = 0L,
	var participantId: Int = 0,
	var teamId: Int = 0,
	var championId: Int = 0,
	var spell1Id: Int = 0,
	var spell2Id: Int = 0,
	var win: Boolean = false,
	var kills: Int = 0,
	var deaths: Int = 0,
	var assists: Int = 0,
	var queue: Int = 0,
	var activeScore: Double = 0.0

)
