package com.springboot.riot.data.summoner.dto.v5

data class MatchTimeFramePartDamageV5Dto (
    var matchId: String? = null,
    var gameId: Long = 0L,
    var parentTimestamp: Long = 0L,
    var frameId: Int = 0,

    val magicDamageDone: Int = 0,
    val magicDamageDoneToChampions: Int = 0,
    val magicDamageTaken: Int = 0,
    val physicalDamageDone: Int = 0,
    val physicalDamageDoneToChampions: Int = 0,
    val physicalDamageTaken: Int = 0,
    val totalDamageDone: Int = 0,
    val totalDamageDoneToChampions: Int = 0,
    val totalDamageTaken: Int = 0,
    val trueDamageDone: Int = 0,
    val trueDamageDoneToChampions: Int = 0,
    val trueDamageTaken: Int = 0,
)