package com.springboot.riot.data.summoner.dto.v5

data class MatchPartPerksV5Dto (
    val statPerks: MatchPartPerksStatV5Dto? = null,
    val styles: List<MatchPartPerksStyleV5Dto>? = null
)