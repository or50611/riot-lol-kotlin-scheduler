package com.springboot.riot.data.items.dto

import com.google.gson.annotations.SerializedName

data class ItemMapsDto (
    var key: String? = null,
    @SerializedName(value = "11")
    val i_11: Boolean = false,
    @SerializedName(value = "12")
    val i_12: Boolean = false,
    @SerializedName(value = "21")
    val i_21: Boolean = false,
    @SerializedName(value = "22")
    val i_22: Boolean = false
)