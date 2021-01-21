package com.springboot.riot.data.champion.dto

data class ChampionSpellsDto(
    var key: String? = null,
    val id: String? = null,
    val name: String? = null,
    val description: String? = null,
    val tooltip: String? = null,
    val maxrank: Int = 0,
    val cooldownBurn: String? = null,
    val costBurn: String? = null,
    val costType: String? = null,
    val maxammo: String? = null,
    val rangeBurn: String? = null,
    val resource: String? = null,
    val image: ChampionSpellsImageDto? = null
)
