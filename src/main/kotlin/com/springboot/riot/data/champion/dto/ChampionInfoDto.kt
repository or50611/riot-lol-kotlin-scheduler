package com.springboot.riot.data.champion.dto

data class ChampionInfoDto(
    var key:String? = null,
    val attack: Int = 0,
    val defense: Int = 0,
    val magic: Int = 0,
    val difficulty: Int = 0
)