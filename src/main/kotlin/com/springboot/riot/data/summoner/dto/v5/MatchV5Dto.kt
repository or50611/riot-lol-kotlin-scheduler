package com.springboot.riot.data.summoner.dto.v5

import com.springboot.riot.data.summoner.dto.v5.MatchInfoV5Dto

data class MatchV5Dto(
    var matchId: String? = null,
    var puuid: String? = null,

    var info: MatchInfoV5Dto? = null
)
