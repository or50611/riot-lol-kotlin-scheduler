package com.springboot.riot.data.champion.dto

import com.fasterxml.jackson.annotation.JsonAnySetter

import com.fasterxml.jackson.annotation.JsonAnyGetter

import java.util.HashMap


class ChampionDto {
    var type: String? = null
    var format: String? = null
    var version: String? = null

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