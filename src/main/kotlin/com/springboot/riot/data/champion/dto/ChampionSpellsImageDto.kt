package com.springboot.riot.data.champion.dto

data class ChampionSpellsImageDto(
    var key:String? = null,
    var sort: String? = null,
    val full: String? = null,
    val sprite: String? = null,
    val group: String? = null,
    val x: Int = 0,
    val y: Int = 0,
    val w: Int = 0,
    val h: Int = 0
)
