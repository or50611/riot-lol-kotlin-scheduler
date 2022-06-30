package com.springboot.riot.data.summoner.dto.v5

data class MatchTimeFramePartV5Dto (
    var matchId: String? = null,
    var gameId: Long = 0L,
    var parentTimestamp: Long = 0L,
    var frameId: Int = 0,

    val championStats: MatchTimeFramePartChampionV5Dto? = null,
    val currentGold: Int = 0,
    val damageStats: MatchTimeFramePartDamageV5Dto? = null,
    val goldPerSecond: Int = 0,
    val jungleMinionsKilled: Int = 0,
    val level: Int = 0,
    val minionsKilled: Int = 0,
    val participantId: Int = 0,
    val position: MatchTimeFramePartPositionV5Dto? = null,
    val timeEnemySpentControlled: Int = 0,
    val totalGold: Int = 0,
    val xp: Int = 0,

    var x: Int = 0,
    var y: Int = 0,
)