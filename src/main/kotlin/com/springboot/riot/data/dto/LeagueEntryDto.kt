package com.springboot.riot.data.dto

data class LeagueEntryDto(
    var accountId: String? = null,
    var leagueId: String? = null,
    var queueType: String? = null,
    var tier: String? = null,
    var rank: String? = null,
    var summonerId: String? = null,
    var summonerName: String? = null,
    var leaguePoints: Int = 0,
    var wins: Int = 0,
    var losses: Int = 0,
    var veteran: Boolean = false,
    var inactive: Boolean = false,
    var freshBlood: Boolean = false,
    var hotStreak: Boolean = false
)
