package com.springboot.riot.data.summoner.dto

data class MatchPlayerDto (
	var gameId: Int = 0,
	val platformId: String? = null,
	val accountId: String? = null,
	val summonerId: String? = null,
	val summonerName: String? = null,
	val currentPlatformId: String? = null,
	val currentAccountId: String? = null,
	val matchHistoryUri: String? = null,
	val profileIcon: Int = 0
)