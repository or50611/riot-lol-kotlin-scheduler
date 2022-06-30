package com.springboot.riot.data.summoner.dto.v5

import com.springboot.riot.data.summoner.dto.MatchTimeLinePositionDto

data class MatchTimeFrameEventV5Dto(
    val realTimestamp: Long = 0L,


    //SKILL_LEVEL_UP
    //ITEM_DESTROYED
    //ITEM_PURCHASED
    //ITEM_UNDO
    //ITEM_SOLD
    //CHAMPION_KILL
    //WARD_PLACED
    //WARD_KILL
    //BUILDING_KILL
    //ELITE_MONSTER

    //TURRET_PLATE_DESTROYED
    //CHAMPION_SPECIAL_KILL
    //OBJECTIVE_BOUNTY_PRESTART
    var matchId: String? = null,
    var gameId: Long = 0L,
    var parentTimestamp: Long = 0L,
    val type: String? = null,
    val timestamp: Long = 0L,

    //WARD,CHAMPION,BUILDING,TURRET
    val killerId: Int = 0,
    //SKILL, ITEM
    val participantId: Int = 0,
    //CHAMPION,BUILDING,ELITE_MONSTER_
    val position: MatchTimeLinePositionDto? = null,
    //CHAMPION,BUILDING
    val bounty: Int = 0,
    val assistingParticipantIds: List<Int>? = null,

    //SKILL
    val skillSlot: Int = 0,
    val levelUpType: String? = null,

    //ITEM
    val itemId: Int = 0,
    val afterId: Int = 0,
    val beforeId: Int = 0,
    val goldGain: Int = 0,

    //CHAMPION
    val victimId: Int = 0,
    val shutdownBounty: Int = 0,
    val killStreakLength: Int = 0,

    //victimDamageDealt,victimDamageReceived list 챔피언킬 상세데이터 필요없을것같아서 제외시킴

    //CHAMPION_SPECIAL_KILL
    val killType: String? = null,
    val multiKillLength: Int = 0,


    //ELITE_MONSTER
    val monsterType: String? = null,
    val monsterSubType: String? = null,

    //WARD
    val wardType: String? = null,
    val creatorId: Int = 0,


    //BUILDING ,TURRET
    val teamId: Int = 0,
    val buildingType: String? = null,
    val laneType: String? = null,
    val towerType: String? = null,

    //OBJECTIVE_BOUNTY_PRESTART
    val actualStartTime: Long = 0L,


    var x: Int = 0,
    var y: Int = 0,

    )
