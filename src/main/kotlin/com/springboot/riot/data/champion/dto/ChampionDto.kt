package com.springboot.riot.data.champion.dto

import com.fasterxml.jackson.annotation.JsonAnySetter

import com.fasterxml.jackson.annotation.JsonAnyGetter

import java.util.HashMap


class ChampionDto {
    val type: String? = null
    val format: String? = null
    val version: String? = null

    var data: MutableMap<String, ChampionDataDto> = HashMap()

    @JsonAnyGetter
    fun getAdditionalProperties(): Map<String, ChampionDataDto>? {
        return data
    }

    @JsonAnySetter
    fun setAdditionalProperty(name: String, value: ChampionDataDto) {
        data[name] = value
    }
}