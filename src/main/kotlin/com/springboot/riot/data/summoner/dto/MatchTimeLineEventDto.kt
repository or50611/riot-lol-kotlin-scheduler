package com.springboot.riot.data.summoner.dto

data class MatchTimeLineEventDto(
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
    var gameId: Int = 0,
    val parentTimestamp: Int = 0,
    val type: String? = null,
    val timestamp: Int = 0,

    //WARD,CHAMPION,BUILDING
    val killerId: Int = 0,
    //SKILL, ITEM
    val participantId: Int = 0,
    //CHAMPION,BUILDING,ELITE_MONSTER_
    val position: MatchTimeLinePositionDto? = null,
    //CHAMPION,BUILDING
    val assistingParticipantIds: List<Int>? = null,

    //SKILL
    val skillSlot: Int = 0,
    val levelUpType: String? = null,

    //ITEM
    val itemId: Int = 0,
    val afterId: Int = 0,
    val beforeId: Int = 0,

    //CHAMPION
    val victimId: Int = 0,

    //ELITE_MONSTER
    val monsterType: String? = null,
    val monsterSubType: String? = null,

    //WARD
    val wardType: String? = null,
    val creatorId: Int = 0,


    //BUILDING
    val teamId: Int = 0,
    val buildingType: String? = null,
    val laneType: String? = null,
    val towerType: String? = null,

)