package com.springboot.riot.data.items.dto

data class ItemImageDto (
    var key: String? = null,
    val full: String? = null,
    val sprite: String? = null,
    val group: String? = null,
    val x: Int = 0,
    val y: Int = 0,
    val w: Int = 0,
    val h: Int = 0
)