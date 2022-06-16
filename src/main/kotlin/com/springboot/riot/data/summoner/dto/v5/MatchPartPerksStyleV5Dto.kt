package com.springboot.riot.data.summoner.dto.v5

data class MatchPartPerksStyleV5Dto(
    val description: String? = null,
    val selections: List<MatchPartPerksSelectionV5Dto>? = null,
    val style: Int = 0
)
