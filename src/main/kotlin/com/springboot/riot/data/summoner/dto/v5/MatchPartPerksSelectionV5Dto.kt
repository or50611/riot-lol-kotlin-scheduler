package com.springboot.riot.data.summoner.dto.v5

data class MatchPartPerksSelectionV5Dto(
    var matchId: String? = null,
    var gameId: Long = 0L,
    var participantId: Int = 0,
    var description: String? = null,
    var style: Int = 0,
    var perkOrder: Int = 0,
    val perk: Int = 0,
    val var1: Int = 0,
    val var2: Int = 0,
    val var3: Int = 0,
)
