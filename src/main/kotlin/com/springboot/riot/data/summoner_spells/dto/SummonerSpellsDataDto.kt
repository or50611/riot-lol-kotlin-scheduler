package com.springboot.riot.data.summoner_spells.dto

data class SummonerSpellsDataDto(
    val key: String? = null,
    val id: String? = null,
    val name: String? = null,
    val description: String? = null,
    val tooltip: String? = null,
    val maxrank: Int = 0,
    val cooldownBurn: String? = null,
    val costBurn: String? = null,
    val summonerLevel: Int = 0,
    val costType: String? = null,
    val maxammo: String? = null,
    val rangeBurn: String? = null,
    val resource: String? = null,

    val image: SummonerSpellsImageDto? = null

)
