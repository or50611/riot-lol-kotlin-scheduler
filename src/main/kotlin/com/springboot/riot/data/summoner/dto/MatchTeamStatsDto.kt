package com.springboot.riot.data.summoner.dto

data class MatchTeamStatsDto (
		var gameId: Int = 0,
		val teamId: Int = 0,
		val win: String? = null,
		val firstBlood: Boolean = false,
		val firstTower: Boolean = false,
		val firstInhibitor: Boolean = false,
		val firstBaron: Boolean = false,
		val firstDragon: Boolean = false,
		val firstRiftHerald: Boolean = false,
		val towerKills: Int = 0,
		val inhibitorKills: Int = 0,
		val baronKills: Int = 0,
		val dragonKills: Int = 0,
		val vilemawKills: Int = 0,
		val riftHeraldKills: Int = 0,
		val dominionVictoryScore: Int = 0,

		val bans: List<MatchTeamBansDto>? = null
)
