package com.springboot.riot.data.summoner_spells.dto

import com.fasterxml.jackson.annotation.JsonAnyGetter
import com.fasterxml.jackson.annotation.JsonAnySetter
import java.util.*

class SummonerSpellsDto {
    var type: String? = null
    val version: String? = null

    var data: MutableMap<String, SummonerSpellsDataDto> = HashMap()

    @JsonAnyGetter
    fun getDataProperties(): Map<String, SummonerSpellsDataDto>? {
        return data
    }



    @JsonAnySetter
    fun setDataProperty(name: String, value: SummonerSpellsDataDto) {
        data[name] = value
    }
}