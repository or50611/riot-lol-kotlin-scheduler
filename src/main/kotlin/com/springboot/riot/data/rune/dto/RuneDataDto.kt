package com.springboot.riot.data.rune.dto

data class RuneDataDto(
    val id: Int = 0,
    var parentId: Int = 0,
    val key: String? = null,
    var fileNm: String? = null,
    val icon: String? = null,
    val name: String? = null,
    val shortDesc: String? = null,
    val longDesc: String? = null,

    val slots: List<RuneSlotsDto>? = null

)
