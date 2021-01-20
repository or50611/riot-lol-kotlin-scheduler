package com.springboot.riot.data.items.dto

data class ItemImageDto (
    var key: String? = null,
    var full: String? = null,
    var sprite: String? = null,
    var group: String? = null,
    var x: Int = 0,
    var y: Int = 0,
    var w: Int = 0,
    var h: Int = 0
)