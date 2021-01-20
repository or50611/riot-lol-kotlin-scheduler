package com.springboot.riot.data.items.dto

import com.fasterxml.jackson.annotation.JsonAnyGetter
import com.fasterxml.jackson.annotation.JsonAnySetter

class ItemDataDto {
    var key: String? = null
    val name: String? = null
    val description: String? = null
    val colloq: String? = null
    val plaintext: String? = null

    val into: List<String>? = null
    val tags: List<String>? = null

    val image: ItemImageDto? = null
    val gold: ItemGoldDto? = null
    val maps: ItemMapsDto? = null

    var stats: MutableMap<String, Double>? = HashMap()

    @JsonAnyGetter
    fun getStatsProperties(): Map<String, Double>? {
        return stats
    }

    @JsonAnySetter
    fun setStatsProperty(name: String, value: Double) {
        stats?.set(name, value)
    }
}