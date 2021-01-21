package com.springboot.riot.data.champion.dto

data class ChampionPassiveDto(
    var key: String? = null,
    val name: String? = null,
    val description: String? = null,
    val image: ChampionPassiveImageDto? = null
)
