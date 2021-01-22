package com.springboot.riot.data.summoner.dto

data class TeamStatsDto (
	
	var towerKills: Int = 0,
	var riftHeraldKills: Int = 0,
	var firstBlood: Boolean = false,
	var inhibitorKills: Int = 0,
	var firstBaron: Boolean = false,
	var firstDragon: Boolean = false,
	var dominionVictoryScore: Int = 0,
	var dragonKills: Int = 0,
	var baronKills: Int = 0,
	var firstInhibitor: Boolean = false,
	var firstTower: Boolean = false,
	var vilemawKills: Int = 0,
	var firstRiftHerald: Boolean = false,
	var teamId: Int = 0
	
)
