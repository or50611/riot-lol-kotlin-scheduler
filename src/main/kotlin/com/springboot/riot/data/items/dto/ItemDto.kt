package com.springboot.riot.data.items.dto

import com.fasterxml.jackson.annotation.JsonAnyGetter
import com.fasterxml.jackson.annotation.JsonAnySetter
import java.util.*

class ItemDto {
    var type: String? = null
    var version: String? = null
    var format: String? = null

    var data: MutableMap<String, ItemDataDto> = HashMap()

    @JsonAnyGetter
    fun getDataProperties(): Map<String, ItemDataDto>? {
        return data
    }

    @JsonAnySetter
    fun setDataProperty(name: String, value: ItemDataDto) {
        data[name] = value
    }

}