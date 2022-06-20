package com.springboot.riot.data.summoner.dto.v5

import com.fasterxml.jackson.annotation.JsonAnySetter
import com.fasterxml.jackson.annotation.JsonAnyGetter
import com.springboot.riot.data.champion.dto.ChampionDataDto
import com.springboot.riot.data.summoner.dto.MatchTeamBansDto
import com.springboot.riot.data.summoner_spells.dto.SummonerSpellsDataDto
import java.util.HashMap

class MatchInfoTeamV5Dto {
    var matchId: String? = null
    var gameId: Long = 0L
    val teamId: Int = 0
    val win: Boolean = false

    val bans: List<MatchInfoTeamBansV5Dto>? = null
    var objectives: MutableMap<String, MatchInfoTeamObjectivesV5Dto> = HashMap()

    @JsonAnyGetter
    fun getDataProperties(): Map<String, MatchInfoTeamObjectivesV5Dto>? {
        return objectives
    }

    @JsonAnySetter
    fun setDataProperties(name: String, value: MatchInfoTeamObjectivesV5Dto) {
        objectives[name] = value
    }


}