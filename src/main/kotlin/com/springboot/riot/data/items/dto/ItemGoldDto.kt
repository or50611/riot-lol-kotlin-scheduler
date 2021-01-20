package com.springboot.riot.data.items.dto

data class ItemGoldDto (
    var key: String? = null,
    val base: Int = 0,
    val purchasable: Boolean = false,
    val total: Int = 0,
    val sell: Int = 0
)