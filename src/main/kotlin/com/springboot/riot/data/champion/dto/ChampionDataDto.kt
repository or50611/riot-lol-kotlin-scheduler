package com.springboot.riot.data.champion.dto

data class ChampionDataDto (
    val key: String? = null,
    val version: String? = null,
    val id: String? = null,
    val name: String? = null,
    val title: String? = null,
    val blurb: String? = null,
    val partype: String? = null,

    val info: ChampionInfoDto? = null,
    val image: ChampionImageDto? = null,
    val tags: List<String>? = null,
    val stats: ChampionStatsDto? = null
)