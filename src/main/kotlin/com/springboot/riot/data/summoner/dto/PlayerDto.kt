package com.springboot.riot.data.summoner.dto

data class PlayerDto (
	var profileIcon: Int = 0,
	var accountId: String? = null,
	var matchHistoryUri: String? = null,
	var currentAccountId: String? = null,
	var currentPlatformId: String? = null,
	var summonerName: String? = null,
	var summonerId: String? = null,
	var platformId: String? = null
	
)